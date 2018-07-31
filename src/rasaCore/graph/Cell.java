package rasaCore.graph;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Cell extends Pane implements IGraphElement{

    String cellId;

    List<Cell> children = new ArrayList<>();
    List<Cell> parents = new ArrayList<>();

    Node view;

    public Cell(String cellId) {
        this.cellId = cellId;
    }

    public void addCellChild(Cell cell) {
        children.add(cell);
    }

    public List<Cell> getCellChildren() {
        return children;
    }

    public void addCellParent(Cell cell) {
        parents.add(cell);
    }

    public List<Cell> getCellParents() {
        return parents;
    }

    public void removeCellChild(Cell cell) {
        children.remove(cell);
    }

    public void removeCellParent(Cell parent){
        parents.remove(parent);
    }

    public void setView(Node view) {

        this.view = view;
        getChildren().add(view);
    }


    public Node getView() {
        return this.view;
    }

    public String getCellId() {
        return cellId;
    }

    public void select(){
        setBorder(new Border(new BorderStroke(Color.YELLOW,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,new BorderWidths(2))));
    }
    public void unselect(){
        setBorder(null);
    }

    public boolean equals(Cell element){
        if(element instanceof  Cell){
            if(element.getCellId().equals(getCellId())){
                return true;
            }
        }
        return false;
    }
}
