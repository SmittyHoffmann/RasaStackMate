package rasaCore.graph;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GraphModel {

    Cell graphParent;

    int cellCounter = 0;

    List<Cell> allCells;
    List<Cell> addedCells;
    List<Cell> removedCells;

    List<Edge> allEdges;
    List<Edge> addedEdges;
    List<Edge> removedEdges;

    Map<String,Cell> cellMap; // <id,cell>

    ObservableList<String> entities;

    public GraphModel() {
        // clear model, create lists
        clear();
    }

    public void clear() {

        allCells = new ArrayList<>();
        addedCells = new ArrayList<>();
        removedCells = new ArrayList<>();

        allEdges = new ArrayList<>();
        addedEdges = new ArrayList<>();
        removedEdges = new ArrayList<>();

        cellMap = new HashMap<>(); // <id,cell>

        graphParent = new Cell( "_ROOT_");

    }

    public Cell getCell(String id){
        return cellMap.get(id);
    }

    public Cell getGraphParent(){
        return this.graphParent;
    }

    public void clearAddedLists() {
        addedCells.clear();
        addedEdges.clear();
    }

    public List<Cell> getAddedCells() {
        return addedCells;
    }

    public List<Cell> getRemovedCells() {
        return removedCells;
    }

    public List<Cell> getAllCells() {
        return allCells;
    }

    public List<Edge> getAddedEdges() {
        return addedEdges;
    }

    public List<Edge> getRemovedEdges() {
        return removedEdges;
    }

    public List<Edge> getAllEdges() {
        return allEdges;
    }

    public void addCell(String name, CellType type) {

        this.cellCounter++;
        switch (type) {

            case ACTION:
                ActionElement actionElement = new ActionElement(Integer.toString(cellCounter),name);
                addCell(actionElement);
                break;
            case INTENT:
                IntentElement intentElement = new IntentElement(Integer.toString(cellCounter), name);
                intentElement.setEditButtonHandler(this.entities);
                addCell(intentElement);
                break;

            default:
                throw new UnsupportedOperationException("Unsupported type: " + type);
        }
    }

    public void addCell( Cell cell) {

        addedCells.add(cell);
        cell.setManaged(false);

        cellMap.put( cell.getCellId(), cell);

    }



    public boolean addEdge( String sourceId, String targetId) {

        Cell sourceCell = cellMap.get( sourceId);
        Cell targetCell = cellMap.get( targetId);
        boolean exists = false;
        for(Edge currentEdge : allEdges){
            if(currentEdge.getSource() == sourceCell && currentEdge.getTarget() == targetCell){
                exists = true;
            }
        }
        if(!exists){
            Edge edge = new Edge(sourceCell, targetCell);

            addedEdges.add( edge);
        }

        return exists;
    }

    /**
     * Attach all cells which don't have a parent to graphParent
     * @param cellList
     */
    public void attachOrphansToGraphParent( List<Cell> cellList) {

        for( Cell cell: cellList) {
            if( cell.getCellParents().size() == 0) {
                graphParent.addCellChild( cell);
                cell.addCellParent(graphParent);
            }
        }

    }

    /**
     * Remove the graphParent reference if it is set
     * @param cellList
     */
    public void disconnectFromGraphParent( List<Cell> cellList) {

        for( Cell cell: cellList) {
            if(cell.getCellParents().size() > 1 && cell.getCellParents().contains(graphParent)){
                graphParent.removeCellChild(cell);
                cell.removeCellParent(graphParent);
            }

        }
    }
    public void removeEdge(Edge edge){
        Cell source = edge.getSource();
        Cell target = edge.getTarget();

        target.getCellParents().remove(source);
        source.getCellChildren().remove(target);

        removedEdges.add(edge);
    }

    public void merge() {

        // cells
        allCells.addAll( addedCells);
        allCells.removeAll( removedCells);

        addedCells.clear();
        removedCells.clear();

        // edges
        allEdges.addAll( addedEdges);
        allEdges.removeAll( removedEdges);

        addedEdges.clear();
        removedEdges.clear();

    }

    public String getCellCounter(){
        return Integer.toString(cellCounter);
    }

    public void removeCell(Cell deleteCell) {

        for(Edge edge : allEdges){
            if(edge.getSource() == deleteCell){
                edge.getTarget().removeCellParent(deleteCell);
                removedEdges.add(edge);
            }
            else if(edge.getTarget() == deleteCell){
                edge.getSource().removeCellChild(deleteCell);
                removedEdges.add(edge);
            }
        }
        cellMap.remove(deleteCell.getCellId());
        removedCells.add(deleteCell);

    }


    public void setEntities(ObservableList<String> entities){
        this.entities = entities;
    }

    public ObservableList<String> getEntities(){
        return this.entities;
    }
}