package main.util;

import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.textfield.TextFields;

public class NameChangeDialog extends Dialog{
    private TextField nameField;
    public NameChangeDialog(String name){
        super();
        this.setTitle("Name bearbeiten");
        this.setHeaderText("Gib einen anderen Namen ein");

        ButtonType confirmButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);

        this.getDialogPane().getButtonTypes().addAll(confirmButtonType,cancelButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);

        grid.setPadding(new Insets(20,150,10,10));

        this.nameField = new TextField();
        this.nameField.setText(name);

        Label nameLabel = new Label("Name:");
        grid.add(nameLabel,0,0);
        grid.add(this.nameField,1, 0);

        Node confirmButton = this.getDialogPane().lookupButton(confirmButtonType);

        this.nameField.textProperty().addListener(((observable, oldValue, newValue) -> {
            confirmButton.setDisable(newValue.trim().isEmpty());
        }));

        this.getDialogPane().setContent(grid);

        Platform.runLater(() -> this.nameField.requestFocus());

        this.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType){
                return this.nameField.getText();
            }
            return null;
        });
    }

    public void addSuggestionProvider(SuggestionProvider<String> provider){
        TextFields.bindAutoCompletion(this.nameField, provider);
    }
}
