package controller.util;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import javax.script.Bindings;

public class AddCategoricalSlotDialog extends Dialog {
    private ListView<String> valueListView;
    private ObservableList<String> values;

    public AddCategoricalSlotDialog(){
        super();
        this.setTitle("CategoricalSlot bearbeiten");
        this.setHeaderText("Gib die Daten ein");

        ButtonType confirmButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);

        this.getDialogPane().getButtonTypes().addAll(confirmButtonType,cancelButtonType);
        values = FXCollections.observableArrayList();
        BorderPane borderPane = new BorderPane();
        valueListView = new ListView<>();
        valueListView.setItems(values);

        borderPane.setCenter(valueListView);

        VBox vbox = new VBox();
        TextField valueTextField = new TextField();
        Button addButton = new Button("Wert hinzufügen");
        Button deleteButton = new Button("Wert entfernen");
        Separator separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);

        vbox.getChildren().addAll(valueTextField,addButton,separator,deleteButton);

        borderPane.setRight(vbox);

        Node confirmButton = this.getDialogPane().lookupButton(confirmButtonType);

        addButton.setOnAction(event-> {
            String value = valueTextField.getText();
            if(!value.isEmpty()){
                values.add(value);
            }
        });

        deleteButton.setOnAction(event->{
            String value = valueListView.getSelectionModel().getSelectedItem();
            if(!value.isEmpty()){
                values.remove(value);
            }
        });

        values.addListener((ListChangeListener<String>) c -> confirmButton.setDisable((c.getList().size() == 0)));


        this.getDialogPane().setContent(borderPane);

        Platform.runLater(() -> valueTextField.requestFocus());

        this.setResultConverter(dialogButton -> {
            if(dialogButton == confirmButtonType){
                return values;
            }
            return null;
        });

    }

}
