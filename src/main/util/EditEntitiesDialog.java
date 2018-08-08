package main.util;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class EditEntitiesDialog extends Dialog {
    private ListView<String> entityListView;
    private ObservableList<String> entities;
    private TableView<Map.Entry<String,String>> selectedEntitiesTableView;
    private ObservableList<Map.Entry<String,String>> selectedEntities;
    private Label selectedEntityLabel;
    private TextField entityValueTextField;


    public EditEntitiesDialog(ObservableList<String> entities, ObservableMap<String,String> selectedEntities){
        super();
        this.entities = FXCollections.observableArrayList();
        this.entities.addAll(entities);
        this.selectedEntities = FXCollections.observableArrayList();

        this.selectedEntities.addAll(selectedEntities.entrySet());




       this.entities.removeAll(selectedEntities.keySet());

        setupDialog();


    }

    private void setupDialog() {

        this.setTitle("Entities bearbeiten");


        ButtonType confirmButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);

        this.getDialogPane().getButtonTypes().addAll(confirmButtonType,cancelButtonType);

        entityListView = new ListView<>();

        setupTableView();


        entityListView.setItems(this.entities);



        BorderPane pane = new BorderPane();

        VBox possibleEntityBox = new VBox(5);
        possibleEntityBox.setAlignment(Pos.CENTER);
        Button chooseEntity = new Button("Wähle Entity");

        chooseEntity.setOnAction(event -> {
            String entityName = entityListView.getSelectionModel().getSelectedItem();
            if(entityName!= null){
                selectedEntityLabel.setText(entityName);
            }
        });

        possibleEntityBox.getChildren().addAll(entityListView,chooseEntity);
        pane.setLeft(possibleEntityBox);


        VBox selectedEntityBox = new VBox(5);
        selectedEntityBox.setAlignment(Pos.CENTER);
        Button deleteEntity = new Button("Löschen");
        deleteEntity.setOnAction(event -> {
            Map.Entry<String,String> selectedEntry = selectedEntitiesTableView.getSelectionModel().getSelectedItem();
            if (selectedEntry != null) {
                selectedEntitiesTableView.getItems().remove(selectedEntry);
                entityListView.getItems().add(selectedEntry.getKey());
            }
        });

        selectedEntityBox.getChildren().addAll(selectedEntitiesTableView,deleteEntity);

        pane.setRight(selectedEntityBox);

        VBox middleBox = new VBox();
        middleBox.setAlignment(Pos.CENTER);
        middleBox.setSpacing(4);

        GridPane middleGrid = new GridPane();
        middleGrid.setVgap(5);
        middleGrid.setHgap(5);


        Label nameLabel = new Label("Ausgewählte Entity");
        Label valueLabel = new Label("Entity-Wert");
        selectedEntityLabel = new Label();
        entityValueTextField = new TextField("Entity-Wert eingeben");

        Button addEntity = new Button("Hinzufügen");
        addEntity.setOnAction(event -> {
            String entityValue = entityValueTextField.getText();
            String entityName = selectedEntityLabel.getText();
            if(!entityName.equals("") && !entityValue.equals("")) {
                Map.Entry<String, String> newEntry = new AbstractMap.SimpleEntry<>(entityName,entityValue);
                selectedEntitiesTableView.getItems().add(newEntry);
                entityListView.getItems().remove(entityName);
                selectedEntityLabel.setText("");
                entityValueTextField.setText("");
            }
        });

        middleGrid.add(nameLabel,0,0);
        middleGrid.add(valueLabel,0,1);
        middleGrid.add(selectedEntityLabel,1,0);
        middleGrid.add(entityValueTextField,1,1);

        middleBox.getChildren().addAll(middleGrid,addEntity);


        pane.setCenter(middleBox);


        Node confirmButton = this.getDialogPane().lookupButton(confirmButtonType);

        this.getDialogPane().setContent(pane);

        this.setResultConverter(dialogButton -> {
            if(dialogButton == confirmButtonType){
                Map<String,String> returnMap = FXCollections.observableHashMap();
                for(Map.Entry<String,String> entry : selectedEntitiesTableView.getItems()){
                    returnMap.put(entry.getKey(),entry.getValue());
                }
                return returnMap;
            }
            return null;
        });
    }

    private void setupTableView() {
        selectedEntitiesTableView = new TableView(selectedEntities);
        TableColumn<Map.Entry<String,String>,String> entityNameColumn = new TableColumn<>("Entity-Name");
        TableColumn<Map.Entry<String,String>,String> entityValueColumn = new TableColumn<>("Entity-Wert");
        entityNameColumn.setCellValueFactory(cd -> Bindings.createStringBinding(() -> cd.getValue().getKey()));
        entityValueColumn.setCellValueFactory(cd -> Bindings.createStringBinding(() ->cd.getValue().getValue()));
        selectedEntitiesTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        selectedEntitiesTableView.getColumns().setAll(entityNameColumn,entityValueColumn);
    }
}
