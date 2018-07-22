package rasaCore.view.graph;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class ActionElement extends StoryElement{

    public ActionElement(String id,String actionName){
        super(id);

        VBox box = new VBox();
        box.setMinHeight(75);
        box.setMaxHeight(75);
        box.setMaxWidth(75);
        box.setMinWidth(75);

        Label nameLabel =  new Label(actionName);
        box.getChildren().add(nameLabel);

        box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10),new BorderWidths(3))));


    }
}
