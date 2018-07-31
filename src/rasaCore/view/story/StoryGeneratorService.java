package rasaCore.view.story;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import rasaCore.graph.ActionElement;
import rasaCore.graph.Cell;
import rasaCore.graph.GraphModel;
import rasaCore.graph.IntentElement;
import rasaNLU.model.entity.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceConfigurationError;

public class StoryGeneratorService extends Service<List<String>> {

    GraphModel model;
    EntityManager manager;
    public StoryGeneratorService(GraphModel model, EntityManager manager){
        this.model = model;
        this.manager = manager;
    }


    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected List<String> call() throws Exception {
                List<String> resultList = new ArrayList<>();
                List<List<String>> stories = new ArrayList<>();
                List<String> currentStory = new ArrayList<>();

                Cell parent =  model.getGraphParent();
                createRecursively(parent,currentStory,stories);
                int counter = 0;

                for(List<String> story : stories ){
                    counter++;
                    resultList.add("## "+Integer.toString(counter)+"\n");
                    for(String storyElement : story){
                        resultList.add(storyElement+"\n");
                    }
                    resultList.add("\n");
                }
                return resultList;
            }
        };
    }


    public void createRecursively(Cell currentCell, List<String> currentStory, List<List<String>> stories){
        for(Cell cell : currentCell.getCellChildren()){
            List<String> bufferList = new ArrayList<>();
            bufferList.addAll(currentStory);

            if(cell instanceof ActionElement){
                String action = "\t- "+ ((ActionElement) cell).getActionName();
                bufferList.add(action);
            }else if(cell instanceof IntentElement){
                String intent = "* "+ ((IntentElement) cell).getIntentName();
                if(!(((IntentElement) cell).getEntities().isEmpty())){
                    List<String> entityStrings = new ArrayList<>();
                    for(String entity : ((IntentElement) cell).getEntities()){
                        String randomEntityValue = manager.getRandomEntityValue(entity);
                        String entityString = "\""+entity+"\" : \""+randomEntityValue+"\"";
                        entityStrings.add(entityString);
                    }
                    intent = intent+"{"+String.join(",",entityStrings)+"}";
                }
                bufferList.add(intent);
            }
            if(cell.getCellChildren().isEmpty()){
                stories.add(bufferList);
                System.out.println(bufferList);
            }else{
                createRecursively(cell,bufferList,stories);
            }

        }


    }
}
