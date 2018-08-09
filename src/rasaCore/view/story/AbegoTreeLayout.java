package rasaCore.view.story;

import javafx.application.Platform;
import org.abego.treelayout.Configuration;
import org.abego.treelayout.Configuration.Location;
import org.abego.treelayout.NodeExtentProvider;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;
import rasaCore.model.graph.Cell;
import rasaCore.model.graph.Graph;

import java.awt.geom.Rectangle2D;
import java.util.Map;


public class AbegoTreeLayout extends Layout{

    private Configuration<Cell> configuration;
    Graph graph;
    public AbegoTreeLayout(Graph graph){

        this(graph ,60,30, Location.Top);

    }

    public AbegoTreeLayout(Graph graph,double gapBetweenLevels, double gapBetweenNodes, Location location) {
        this(new DefaultConfiguration<>(gapBetweenLevels, gapBetweenNodes, location),graph);
    }

    public AbegoTreeLayout(Configuration<Cell> configuration, Graph graph) {
        this.configuration = configuration;
        this.graph = graph;
    }


    @Override
    public void execute() {
        final DefaultTreeForTreeLayout<Cell> layout = new DefaultTreeForTreeLayout<>(graph.getModel().getGraphParent());
        addRecursively(layout,graph.getModel().getGraphParent());

        final NodeExtentProvider<Cell> nodeExtentProvider = new NodeExtentProvider<Cell>() {
            @Override
            public double getWidth(Cell element) {
                if(element == graph.getModel().getGraphParent()){
                    return 0;
                }
                return element.getView().getBoundsInLocal().getWidth();
            }

            @Override
            public double getHeight(Cell element) {
                if(element == graph.getModel().getGraphParent()){
                    return 0;
                }
                return element.getView().getBoundsInLocal().getHeight();
            }
        };

        final TreeLayout<Cell> treeLayout = new TreeLayout<>(layout,nodeExtentProvider,configuration);

        int counter = 0;

        for(Map.Entry<Cell, Rectangle2D.Double> entry : treeLayout.getNodeBounds().entrySet()){
            counter++;
            Cell cell = entry.getKey();
            Rectangle2D.Double bounds = entry.getValue();
            if(!cell.equals(graph.getModel().getGraphParent())){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        cell.relocate(bounds.getCenterX(),bounds.getCenterY());
                        cell.setManaged(true);
                    }
                });

            }
            System.out.println("Noch übrig:");
            System.out.println(treeLayout.getNodeBounds().size()-counter);
        }

    }




    public void addRecursively(DefaultTreeForTreeLayout<Cell> layout, Cell node) {
        node.getCellChildren().forEach(element -> {
            if(!layout.hasNode(element)) {
                layout.addChild(node, element);
                addRecursively(layout, element);
            }
        });
    }
}
