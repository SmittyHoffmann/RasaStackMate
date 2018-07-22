package rasaCore.view.graph;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

//codebasis von stackoverflow
public class StoryElement extends Pane {

    String elementId;

    List<StoryElement> children = new ArrayList<>();
    List<StoryElement> parents = new ArrayList<>();

    Node view;

    public StoryElement(String id){
        this.elementId = id;
    }

    public void addElementChild(StoryElement element){
        this.children.add(element);
    }

    public List<StoryElement> getElementChildren(){
        return this.children;
    }

    public void addElementParent(StoryElement element){
        this.parents.add(element);
    }

    public List<StoryElement> getElementParents(){
        return this.parents;
    }

    public void deleteElementChild(StoryElement element){
        this.children.remove(element);
    }

    public void setView(Node view){
        this.view = view;
    }

    public Node getView(){
        return this.view;
    }

    public String getElementId(){
        return this.elementId;
    }

}
