package rasaCore.view.story;

import rasaCore.graph.Graph;
import rasaCore.graph.GraphModel;

public interface StoryGenerator {

    void fillGraphModel(GraphModel graphModel, String fileName);

    void generateStories(GraphModel graphModel);


}
