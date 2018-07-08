package controller.util;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import model.rasaCore.domain.slot.FloatSlot;

public class EditFloatSlotDialog extends Dialog {

    private TextField minValueField;
    private TextField maxValueField;
    float minvalue;
    float maxValue;


    public EditFloatSlotDialog(FloatSlot slot){
        super();
        this.maxValue = slot.getMaxValue();
        this.minvalue = slot.getMinValue();
        setupDialog();
    }
    public EditFloatSlotDialog(){
        super();
        setupDialog();


    }

    public void setupDialog(){
        this.setTitle("FloatSlot bearbeiten");
        this.setHeaderText("Gib die Daten ein");

        ButtonType confirmButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);

        this.getDialogPane().getButtonTypes().addAll(confirmButtonType,cancelButtonType);

        VBox vbox = new VBox();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.setPadding(new Insets(20,150,10,10));

        this.minValueField = new TextField();
        this.minValueField.setText(String.valueOf(minvalue));
        this.maxValueField = new TextField();
        this.maxValueField.setText(String.valueOf(maxValue));

        Label minvalueLabel = new Label("Minimaler Wert:");
        Label maxValueLabel = new Label("Maximaler Wert");

        grid.add(minvalueLabel,0,0);
        grid.add(this.minValueField,1, 0);
        grid.add(maxValueLabel,0,1);
        grid.add(this.maxValueField,1,1);

        Node confirmButton = this.getDialogPane().lookupButton(confirmButtonType);

        this.minValueField.textProperty().addListener(((observable, oldValue, newValue) -> {
            boolean isFloat = true;
            try{
                this.minvalue = Float.parseFloat(newValue);
            }catch(NumberFormatException e){
                isFloat = false;
            }
            confirmButton.setDisable(newValue.trim().isEmpty() || maxValueField.getText().isEmpty() || !isFloat);
        }));
        this.maxValueField.textProperty().addListener(((observable, oldValue, newValue) -> {
            boolean isFloat = true;
            try{
                this.maxValue = Float.parseFloat(newValue);
            }catch(NumberFormatException e){
                isFloat = false;
            }
            confirmButton.setDisable(newValue.trim().isEmpty() || minValueField.getText().isEmpty() || !isFloat);
        }));

        this.getDialogPane().setContent(grid);

        Platform.runLater(() -> this.minValueField.requestFocus());

        this.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType){
                return new Pair<>(minvalue, maxValue);
            }
            return null;
        });
    }
}
