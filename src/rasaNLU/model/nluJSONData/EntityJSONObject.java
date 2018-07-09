package rasaNLU.model.nluJSONData;

import org.json.simple.JSONObject;

public class EntityJSONObject extends JSONObject {



    public EntityJSONObject(int start,String value, String entity){
        this.put("start", start);
        this.put("end",start+value.length());
        this.put("value",value);
        this.put("entity",entity);
    }

    public void setStart(int start){
        this.put("start",start);
    }


    public void setValue(String value){
        this.put("value",value);
        this.put("end",((int)this.get("start"))+value.length());
    }

    public void setEntity(String entity){
        this.put("entity",entity);
    }





}
