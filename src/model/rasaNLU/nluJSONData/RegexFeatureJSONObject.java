package model.rasaNLU.nluJSONData;

import org.json.simple.JSONObject;

public class RegexFeatureJSONObject extends JSONObject {

    public RegexFeatureJSONObject(String name, String pattern){
        this.put("name", name);
        this.put("pattern",pattern);
    }
}
