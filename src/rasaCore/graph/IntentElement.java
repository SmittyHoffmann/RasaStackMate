package rasaCore.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import main.util.EditEntitiesDialog;
import rasaNLU.model.intent.Intent;

import java.util.Optional;

public class IntentElement extends Cell {



    String intentName;
    Button editEntities;
    ObservableList<String> entities;
    Text entityText;

    public IntentElement(String cellId, String intentName) {
        super(cellId);

        entities = FXCollections.observableArrayList();

        this.intentName = intentName;

        VBox vbox = new VBox();


        Text name = new Text(intentName);
        entityText = new Text("{ }");

        String defaultFont = Font.getDefault().getName();

        name.setFont(Font.font(defaultFont, FontWeight.BOLD, 12));
        entityText.setFont(Font.font(defaultFont, 10));

        VBox.setMargin(name, new Insets(2, 2, 2, 2));
        vbox.setSpacing(2);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #FFFFFF");
        vbox.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, new BorderWidths(3))));

        editEntities = new Button("Entities");
        editEntities.setFont(Font.font(defaultFont, 10));


        vbox.getChildren().add(name);


        vbox.getChildren().add(entityText);
        vbox.getChildren().add(editEntities);
        setView(vbox);
    }


    public void setEditButtonHandler(ObservableList<String> entityList) {


        editEntities.addEventFilter(ActionEvent.ACTION, (EventHandler<Event>) event -> {
            System.out.println("vor Dialog");
            Dialog<ObservableList<String>> dialog = new EditEntitiesDialog(entityList, getEntities());
            Optional<ObservableList<String>> result = dialog.showAndWait();
            result.ifPresent(resultData -> {
                setEntities(resultData);
            });

            System.out.println("nachDialog");
        });
    }


    public ObservableList<String> getEntities() {
        return this.entities;
    }

    public void setEntities(ObservableList<String> entities) {
        this.entities.clear();
        String entityString = "";
        for (String entity : entities) {
            this.entities.add(entity);
            if (entityString.isEmpty()) {
                entityString = entity;
            } else {
                entityString = entityString + ", " + entity;
            }
        }
        entityText.setText("{ " + entityString + " }");

    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }


    public String getIntentName() {
        return this.intentName;
    }

    @Override
    public boolean equals(Cell element) {
        if (element instanceof IntentElement) {
            IntentElement compareElement =(IntentElement) element;
            FXCollections.sort(getEntities());
            FXCollections.sort(compareElement.getEntities());
           if(compareElement.getEntities().equals(getEntities()) && compareElement.getIntentName().equals(getIntentName())){
               return true;
           }
        }
        return false;
    }
}