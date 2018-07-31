package rasaCore.graph;


import java.util.List;
import java.util.Random;

public class RandomLayout extends Layout {

    Graph graph;

    Random rnd = new Random();

    public RandomLayout(Graph graph) {

        this.graph = graph;

    }

    public void execute() {

        List<Cell> cells = graph.getModel().getAllCells();
        int counter = 0;
        for (Cell cell : cells) {
            counter++;
            double x = rnd.nextDouble() * 500;
            double y = rnd.nextDouble() * 500;
            System.out.println("vor relocate");
            cell.relocate(x, y);
            System.out.println("nach relocate noch übrig: "+ Integer.toString(cells.size()-counter));
        }

    }
}