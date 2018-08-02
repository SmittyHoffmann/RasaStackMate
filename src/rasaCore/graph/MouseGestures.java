package rasaCore.graph;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;


public class MouseGestures {
    final DragContext dragContext = new DragContext();

    Graph graph;
    private Line line;

    public MouseGestures(Graph graph) {
        this.graph = graph;
    }

    public void addHandlers(final Node node) {

        node.addEventFilter(MouseEvent.MOUSE_PRESSED,onMousePressedEventHandler);
        node.addEventFilter(MouseEvent.MOUSE_DRAGGED,onMouseDraggedEventHandler);
        node.addEventFilter(MouseDragEvent.DRAG_DETECTED,onDragDetectedHandler);
        node.addEventFilter(MouseDragEvent.MOUSE_DRAG_RELEASED,onMouseDragReleased);

        graph.getCellLayer().setOnMouseDragReleased(onMouseDragReleased);


    }


    EventHandler<MouseEvent> onDragDetectedHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (!event.isPrimaryButtonDown() && event.isSecondaryButtonDown()) {
                    if(graph.getCellLayer().getChildren().contains(line)){
                        graph.getCellLayer().getChildren().remove(line);
                    }
                    Node node = (Node) event.getSource();
                    line = new Line();
                    dragContext.x = node.getLayoutX() + node.getBoundsInParent().getWidth()/2;
                    dragContext.y = node.getLayoutY() + node.getBoundsInParent().getHeight() / 2;
                    line.setStartX(dragContext.x);
                    line.setStartY(dragContext.y);
                    line.setEndX(dragContext.x);
                    line.setEndY(dragContext.y);

                    graph.getCellLayer().getChildren().add(0,line);

                    node.startFullDrag();
                    event.consume();

            }
        }
    };


    EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            if (event.isPrimaryButtonDown() && !event.isSecondaryButtonDown()) {
                Node node = (Node) event.getSource();

                double scale = graph.getScale();

                dragContext.x = node.getBoundsInParent().getMinX() * scale - event.getScreenX();
                dragContext.y = node.getBoundsInParent().getMinY() * scale - event.getScreenY();

            }
        }
    };



    EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            Node node = (Node) event.getSource();
            if (event.isPrimaryButtonDown() && !event.isSecondaryButtonDown()) {


                double offsetX = event.getScreenX() + dragContext.x;
                double offsetY = event.getScreenY() + dragContext.y;

                // adjust the offset in case we are zoomed
                double scale = graph.getScale();

                offsetX /= scale;
                offsetY /= scale;

                node.relocate(offsetX, offsetY);
            } else if (!event.isPrimaryButtonDown() && event.isSecondaryButtonDown()) {
                if (line != null) {


                    line.setEndX(event.getX() + dragContext.x - node.getBoundsInParent().getWidth() / 2);
                    line.setEndY(event.getY() + dragContext.y - node.getBoundsInParent().getHeight() / 2);
                }
            }
            event.consume();
        }
    };


    EventHandler<MouseDragEvent> onMouseDragReleased = new EventHandler<MouseDragEvent>() {
        @Override
        public void handle(MouseDragEvent event) {

            System.out.println(event.getSource());
            System.out.println(event.getGestureSource());
            if (event.getSource() != event.getGestureSource()) {
                System.out.println(event.getSource().getClass());
                if (event.getSource() instanceof Cell) {
                    Cell source = (Cell) event.getGestureSource();
                    Cell target = (Cell) event.getSource();
                    boolean exists = graph.getModel().addEdge(source.getCellId(), target.getCellId());
                    if(!exists){
                        graph.endUpdate();
                    }
                }

            }
            graph.getCellLayer().getChildren().remove(line);
            line = null;
            event.consume();
        }
    };


    class DragContext {
        double x;
        double y;
    }
}

