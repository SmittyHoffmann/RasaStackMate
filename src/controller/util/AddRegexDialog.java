package controller.util;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class AddRegexDialog  extends Dialog {
    private TextField patternField;
    private TextField nameField;
    public AddRegexDialog(){
        super();
        this.setTitle("regex hinzufügen");
        this.setHeaderText("Gib die regex-Daten ein");

        ButtonType confirmButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);

        this.getDialogPane().getButtonTypes().addAll(confirmButtonType,cancelButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.setPadding(new Insets(20,150,10,10));

        this.nameField = new TextField();
        this.nameField.setPromptText("Name des regex");
        this.patternField = new TextField();
        this.patternField.setPromptText("Regulärer Ausdruck");

        Label nameLabel = new Label("Name:");
        Label patternLabel = new Label("Regulärer Ausdruck");

        grid.add(nameLabel,0,0);
        grid.add(this.nameField,1, 0);
        grid.add(patternLabel,0,1);
        grid.add(this.patternField,1,1);

        Node confirmButton = this.getDialogPane().lookupButton(confirmButtonType);


        this.nameField.textProperty().addListener(((observable, oldValue, newValue) -> {
            confirmButton.setDisable(newValue.trim().isEmpty() || patternField.getText().isEmpty());
        }));
        this.patternField.textProperty().addListener(((observable, oldValue, newValue) -> {
        confirmButton.setDisable(newValue.trim().isEmpty() || nameField.getText().isEmpty());
        }));

        this.getDialogPane().setContent(grid);

        Platform.runLater(() -> this.nameField.requestFocus());

        this.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType){
                return new Pair<String,String>(nameField.getText(),patternField.getText());
            }
            return null;
        });
    }{
}}
