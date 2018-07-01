package model.nluJSONData;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class RasaNLUJSONObject extends JSONObject {

    JSONArray commonExamples;
    JSONArray entitySynonyms;
    JSONArray regexFeatures;

    public RasaNLUJSONObject(){
        super();
        this.commonExamples = new JSONArray();
        this.entitySynonyms = new JSONArray();
        this.regexFeatures = new JSONArray();

        this.put("common_examples", commonExamples);
        this.put("entity_synonyms", entitySynonyms);
        this.put("regex_features",regexFeatures);
    }

    public void addCommonExample(CommonExampleJSONObject commonExample){
        this.commonExamples.add(commonExample);
    }

    public void addSynonym(SynonymJSONObject synonym){
        this.entitySynonyms.add(synonym);
    }



    public void addRegex(RegexFeatureJSONObject newRegex) {
        this.regexFeatures.add(newRegex);
    }
}
