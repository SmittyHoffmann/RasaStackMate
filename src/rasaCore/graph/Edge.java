package rasaCore.graph;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class Edge extends Group implements IGraphElement{

    protected Cell source;
    protected Cell target;

  //  Line line;
    Arrow line;

    public Edge(Cell source, Cell target) {

        this.source = source;
        this.target = target;

        source.addCellChild(target);
        target.addCellParent(source);

        //line = new Line();
        line = new Arrow();

        line.startXProperty().bind( source.layoutXProperty().add(source.getBoundsInParent().getWidth() / 2.0).add(2));
        line.startYProperty().bind( source.layoutYProperty().add(source.getBoundsInParent().getHeight() / 2.0));

        line.endXProperty().bind( target.layoutXProperty().add( target.getBoundsInParent().getWidth() / 2.0).add(2));
        //line.endYProperty().bind( target.layoutYProperty().add( target.getBoundsInParent().getHeight() / 2.0));
        line.endYProperty().bind( target.layoutYProperty());
        unselect();
        line.getLine().setStrokeWidth(2);




        getChildren().add( line);


    }

    public Cell getSource() {
        return source;
    }

    public Cell getTarget() {
        return target;
    }

    public void select(){
        line.setColor(Color.YELLOW);
    }
    public void unselect(){
        line.setColor(Color.WHITE);
    }

}
