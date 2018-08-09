package rasaCore.model.story;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import rasaCore.model.graph.ActionElement;
import rasaCore.model.graph.Cell;
import rasaCore.model.graph.GraphModel;
import rasaCore.model.graph.IntentElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StoryGeneratorService extends Service<List<String>> {

    GraphModel model;

    public StoryGeneratorService(GraphModel model){
        this.model = model;

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
                    for(Map.Entry<String,String> entry : ((IntentElement) cell).getEntities().entrySet()){
                        String entityValue = entry.getValue();
                        String entityKey = entry.getKey();
                        String entityString = "\""+entityKey+"\" : \""+entityValue+"\"";
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
