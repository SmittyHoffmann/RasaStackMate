package rasaCore.graph;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Graph {

    private GraphModel model;

    private Group canvas;

    private ZoomableScrollPane scrollPane;

    private IGraphElement currentSelectedElement;

    MouseGestures mouseGestures;


    /**
     * the pane wrapper is necessary or else the scrollpane would always align
     * the top-most and left-most child to the top and left eg when you drag the
     * top child down, the entire scrollpane would move down
     */
    Pane cellLayer;

    public Graph() {

        this.model = new GraphModel();

        canvas = new Group();

        cellLayer = new Pane();


        canvas.getChildren().add(cellLayer);

        mouseGestures = new MouseGestures(this);

        scrollPane = new ZoomableScrollPane(canvas);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: white;");
        scrollPane.setOnMouseClicked(onSelection);

        scrollPane.setOnKeyPressed(event -> {
            System.out.println("test");
            System.out.println(event.getCode());
            if (event.getCode().equals(KeyCode.DELETE)) {
                if (currentSelectedElement instanceof Edge) {
                    beginUpdate();
                    getModel().removeEdge((Edge) currentSelectedElement);
                    endUpdate();
                    currentSelectedElement = null;
                } else if (currentSelectedElement instanceof Cell) {
                    beginUpdate();
                    getModel().removeCell((Cell) currentSelectedElement);
                    endUpdate();
                    currentSelectedElement = null;
                }
            }
        });

    }

    public ScrollPane getScrollPane() {
        return this.scrollPane;
    }

    public Pane getCellLayer() {
        return this.cellLayer;
    }

    public GraphModel getModel() {
        return model;
    }

    public void beginUpdate() {
    }

    public void endUpdate() {

        // add components to mull pane

        // remove components from mull pane

        getCellLayer().getChildren().removeAll(model.getRemovedCells());
        getCellLayer().getChildren().removeAll(model.getRemovedEdges());



        // enable dragging of cells
        for (Cell cell : model.getAddedCells()) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    getCellLayer().getChildren().add(cell);
                    cell.addEventFilter(MouseEvent.MOUSE_CLICKED, onSelection);
                    mouseGestures.addHandlers(cell);
                }
            });
        }


        for (Edge edge : model.getAddedEdges()) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    getCellLayer().getChildren().add(edge);
                    edge.addEventFilter(MouseEvent.MOUSE_CLICKED, onSelection);
                    edge.toBack();

                }
            });


        }

        getModel().merge();

        getModel().attachOrphansToGraphParent(model.getAllCells());
        getModel().disconnectFromGraphParent(model.getAllCells());



    }

    public void reset() {

        getModel().clear();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                cellLayer.getChildren().clear();
            }
        });

        System.out.println("bin drin");
    }

    public double getScale() {
        return this.scrollPane.getScaleValue();
    }


    EventHandler<MouseEvent> onSelection = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {


            Node clickedOn = (Node) event.getSource();


            if (clickedOn instanceof IGraphElement) {
                System.out.println("Vor select");
                if (currentSelectedElement != clickedOn) {
                    if (currentSelectedElement != null) {
                        currentSelectedElement.unselect();
                    }
                    currentSelectedElement = (IGraphElement) clickedOn;
                    currentSelectedElement.select();
                    System.out.println("nach Select");

                } else {
                    currentSelectedElement.unselect();
                    currentSelectedElement = null;
                }
                event.consume();
            } else {
                if (currentSelectedElement != null) {
                    currentSelectedElement.unselect();
                }

            }
        }
    };


}
