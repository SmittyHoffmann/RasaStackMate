package rasaNLU.model.nluJSONData;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SynonymJSONObject extends JSONObject {

    JSONArray synonyms;
    public SynonymJSONObject(String value){
        this.synonyms = new JSONArray();
        this.put("value",value);
        this.put("synonyms",synonyms);
    }

    public void addSynonym(String synonym){
        this.synonyms.add(synonym);
    }
}
