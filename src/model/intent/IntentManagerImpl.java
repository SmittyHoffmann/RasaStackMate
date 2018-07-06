package model.intent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Iterator;


public class IntentManagerImpl implements IntentManager {

    HashMap<String, Intent> intents;
    ObservableList<String> intentNames;


    public IntentManagerImpl() {
        this.intents = new HashMap<>();
        this.intentNames = FXCollections.observableArrayList();
    }


    public void addIntentExample(String intentName, String example){
        intents.get(intentName).addExample(example);
    }


    public void addIntent(String intentName){
        if(!intents.containsKey(intentName)){
            this.intents.put(intentName,new Intent());
            this.intentNames.add(intentName);
        }
    }


    public void deleteIntent(String intentName) {
        this.intents.remove(intentName);
        this.intentNames.remove(intentName);
    }


    public Intent getIntent(String intentName) {
        return this.intents.get(intentName);
    }


    public ObservableList<String> getIntentNames() {
        return this.intentNames;
    }


    public HashMap<String,Intent> getIntents(){
        return this.intents;
    }

    public void deleteIntentExample(String intentName, String example){
        this.intents.get(intentName).deleteExample(example);
    }

    public ObservableList<String> getExamplesToIntent(String intentName){
        return this.intents.get(intentName).getExamples();
    }

    @Override
    public void changeIntentName(String currentIntentName, String changedName) {
        if(intents.containsKey(changedName)){
            for(String value : intents.get(currentIntentName).getExamples()){
                intents.get(changedName).addExample(value);
            }

        }else{
            intents.put(changedName,intents.get(currentIntentName));
            intentNames.add(changedName);

        }
        intents.remove(currentIntentName);
        intentNames.remove(currentIntentName);


    }

    @Override
    public void parseJSONIntent(JSONObject currentJSONObject) {
        JSONObject intent = (JSONObject) currentJSONObject.get("intent");
        String intentName = (String) intent.get("name");
        String text = (String) currentJSONObject.get("text");
        JSONArray entities = (JSONArray) currentJSONObject.get("entities");

        Iterator<JSONObject> iterator = entities.iterator();
        while(iterator.hasNext()){
            JSONObject entity = iterator.next();
            String entityName = (String) entity.get("entity");
            String entityValue = (String) entity.get("value");
            text = text.replace(entityValue, "@"+entityName);
        }
        this.addIntent(intentName);
        this.addIntentExample(intentName,text);

    }


}


