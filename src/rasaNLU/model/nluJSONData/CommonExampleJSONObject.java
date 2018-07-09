package rasaNLU.model.nluJSONData;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CommonExampleJSONObject extends JSONObject {

    String text;
    String intentName;



    JSONArray entities;

    public CommonExampleJSONObject(){
        super();
        this.entities = new JSONArray();
        this.put("entities", entities);
    }
    public CommonExampleJSONObject(String text,String intentName, JSONArray entities){
        super();
        this.text = text;
        this.intentName = intentName;
        this.entities = entities;
        this.put("text",text);
        this.put("intent", intentName);
        this.put("entities",entities);
    }

    public String getText(){
        return this.text;
    }

    public String getIntentName(){
        return intentName;
    }
    public JSONArray getEntities(){
    return this.entities;
    }

    public void setText(String text) {
        this.text = text;
        this.put("text",text);
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
        this.put("intent",intentName);
    }

    public void setEntities(JSONArray entities) {
        this.entities = entities;
        this.put("entities",entities);
    }

    public void addEntity(EntityJSONObject entity){
        this.entities.add(entity);
    }

}
