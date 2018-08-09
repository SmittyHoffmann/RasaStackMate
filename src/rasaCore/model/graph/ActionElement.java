package rasaCore.model.graph;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class ActionElement extends Cell{

    private String actionName;
    public ActionElement(String id, String actionName){

        super(id);
        this.actionName = actionName;
        setupUI();
    }

    private void setupUI() {
        VBox box = new VBox();


        Text name =  new Text(actionName);
        String defaultFont = Font.getDefault().getName();

        name.setFont(Font.font(defaultFont,FontWeight.BOLD,12));



        VBox.setMargin(name,new Insets(2));
        box.setStyle("-fx-background-color: #FFFFFF");
        box.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID,CornerRadii.EMPTY,new BorderWidths(2))));
        box.getChildren().add(name);



        setView(box);
    }

    public String getActionName(){
        return this.actionName;
    }

    @Override
    public boolean equals(Cell element) {
        if(element instanceof ActionElement){
            if(((ActionElement) element).getActionName().equals(getActionName())){
                return true;
            }
        }
        return false;
    }
}
