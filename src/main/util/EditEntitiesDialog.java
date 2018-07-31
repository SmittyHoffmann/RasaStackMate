package main.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class EditEntitiesDialog extends Dialog {
    private ListView<String> entityListView;
    private ObservableList<String> entities;
    private ListView<String> selectedEntitiesListView;
    private ObservableList<String> selectedEntities;

    public EditEntitiesDialog(ObservableList<String> entities, ObservableList<String> selectedEntities){
        super();
        this.entities = FXCollections.observableArrayList();
        this.entities.addAll(entities);
        this.selectedEntities = FXCollections.observableArrayList();
        this.selectedEntities.addAll(selectedEntities);

        this.entities.removeAll(this.selectedEntities);

        setupDialog();


    }

    private void setupDialog() {

        this.setTitle("Entities bearbeiten");


        ButtonType confirmButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);

        this.getDialogPane().getButtonTypes().addAll(confirmButtonType,cancelButtonType);

        entityListView = new ListView<>();
        selectedEntitiesListView = new ListView<>();
        entityListView.setItems(this.entities);
        selectedEntitiesListView.setItems(this.selectedEntities);

        BorderPane pane = new BorderPane();
        pane.setLeft(entityListView);
        pane.setRight(selectedEntitiesListView);

        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(4);
        Button right = new Button(">>");
        Button left = new Button("<<");

        buttonBox.getChildren().add(right);
        buttonBox.getChildren().add(left);

        pane.setCenter(buttonBox);

        right.setOnAction(event -> {
            String selected = entityListView.getSelectionModel().getSelectedItem();
            if(selected!=null){
                selectedEntities.add(selected);
                entities.remove(selected);
            }
        });

        left.setOnAction(event -> {
            String selected = selectedEntitiesListView.getSelectionModel().getSelectedItem();
            if(selected!=null){
                entities.add(selected);
                selectedEntities.remove(selected);
            }
        });


        Node confirmButton = this.getDialogPane().lookupButton(confirmButtonType);

        this.getDialogPane().setContent(pane);

        this.setResultConverter(dialogButton -> {
            if(dialogButton == confirmButtonType){
                return selectedEntities;
            }
            return null;
        });
    }
}
