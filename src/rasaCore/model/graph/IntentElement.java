package rasaCore.model.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
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

import java.util.Map;
import java.util.Optional;

public class IntentElement extends Cell {



    String intentName;
    Button editEntities;
    ObservableMap<String,String> entities;
    Text entityText;

    public IntentElement(String cellId, String intentName) {
        super(cellId);

        entities = FXCollections.observableHashMap();

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
            Dialog<ObservableMap<String,String>> dialog = new EditEntitiesDialog(entityList, getEntities());
            Optional<ObservableMap<String,String>> result = dialog.showAndWait();
            result.ifPresent(resultData -> {
                setEntities(resultData);
            });

            System.out.println("nachDialog");
        });
    }


    public ObservableMap<String,String> getEntities() {
        return this.entities;
    }

    public void setEntities(ObservableMap<String,String> entities) {
        this.entities.clear();
        String entityString = "";
        for(Map.Entry<String,String> entry : entities.entrySet()){
            this.entities.put(entry.getKey(),entry.getValue());
            if (entityString.isEmpty()) {
                entityString = entry.getKey() + " : " + entry.getValue();
            } else {
                entityString = "\n" +entityString + ", " + entry.getKey() + " : " + entry.getValue();
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
           if(compareElement.getEntities().equals(getEntities()) && compareElement.getIntentName().equals(getIntentName())){
               return true;
           }
        }
        return false;
    }
}
