package model.rasaNLU.nluJSONData;


import javafx.collections.ObservableList;
import model.rasaNLU.entity.Entity;
import model.rasaNLU.entity.EntityManager;
import model.rasaNLU.intent.Intent;
import model.rasaNLU.intent.IntentManager;
import model.rasaNLU.regex.Regex;
import model.rasaNLU.regex.RegexManager;
import model.rasaNLU.synonym.Synonym;
import model.rasaNLU.synonym.SynonymManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Collectors;

public class NLUJSONGenerator implements NLUTrainDataGenerator{

    private JSONObject root;
    private RasaNLUJSONObject rasaObject;




    @Inject
    public NLUJSONGenerator(){
        this.root = new JSONObject();
        this.rasaObject = new RasaNLUJSONObject();
        this.root.put("rasa_nlu_data", rasaObject);
    }


    @Override
    public JSONObject generateTrainingData(HashMap<String,Intent> intents, HashMap<String,Entity> entities, HashMap<String, Synonym> synonyms, ObservableList<Regex> regexList) {

        generateCommonExamples(intents,entities);
        generateSynonyms(synonyms);
        generateRegex(regexList);



        return root;
    }

    private void generateRegex(ObservableList<Regex> regexList) {
        for(Regex regex : regexList){
            RegexFeatureJSONObject newRegex = new RegexFeatureJSONObject(regex.getName(),regex.getPattern());
            rasaObject.addRegex(newRegex);
        }
    }

    private void generateSynonyms(HashMap<String, Synonym> synonyms) {
        for(HashMap.Entry<String,Synonym> synonym : synonyms.entrySet()){
            SynonymJSONObject newSynonym = new SynonymJSONObject(synonym.getKey());
            for(String value : synonym.getValue().getValues()){
                newSynonym.addSynonym(value);
            }
            rasaObject.addSynonym(newSynonym);
        }
    }

    private void generateCommonExamples(HashMap<String,Intent> intents,HashMap<String,Entity> entities){
        String entityFound;

        for(HashMap.Entry<String,Intent> currentIntent : intents.entrySet()){
            for(String currentExample : currentIntent.getValue().getExamples()){
                entityFound = searchForEntity(currentExample,entities);
                if(!entityFound.isEmpty()){
                    replacePlaceHolders(entities,currentExample,entityFound,currentIntent.getKey(),null);
                }else{
                    CommonExampleJSONObject newExample = new CommonExampleJSONObject();
                    newExample.setText(currentExample);
                    newExample.setIntentName(currentIntent.getKey());
                    rasaObject.addCommonExample(newExample);
                }
            }
        }
    }


    private void replacePlaceHolders(HashMap<String,Entity> entities, String example, String foundEntity, String currentIntentName, JSONArray currentEntities){
        int foundIndex = example.indexOf("@"+foundEntity);
        String entityFound;
        ArrayList<CommonExampleJSONObject> bufferExamples = new ArrayList<>();
        String changedExample;


        for(String value : entities.get(foundEntity).getValues()){
            EntityJSONObject newEntity = new EntityJSONObject(foundIndex,value,foundEntity);
            changedExample = example.replace("@"+foundEntity, value);
            CommonExampleJSONObject newExample = new CommonExampleJSONObject();
            newExample.setIntentName(currentIntentName);
            newExample.setText(changedExample);
            if(!(currentEntities == null)){
                JSONArray clonedEntities = (JSONArray)currentEntities.clone();
                newExample.setEntities(clonedEntities);
            }
            newExample.addEntity(newEntity);
            bufferExamples.add(newExample);
        }

        for(CommonExampleJSONObject ex : bufferExamples){
            entityFound = searchForEntity(ex.getText(),entities);
            if(!entityFound.isEmpty()){
                replacePlaceHolders(entities,ex.getText(), entityFound, currentIntentName,ex.getEntities());
            }
            else{
                rasaObject.addCommonExample(ex);
            }
        }
    }


    private String searchForEntity(String example, HashMap<String,Entity> entities){
        for(String currentEntity : entities.keySet()){
            if(example.contains(("@"+currentEntity))){
                return currentEntity;
            }
        }
        return "";
    }

    public void fillNLUManagers(String filePath, IntentManager intentManager, EntityManager entityManager, SynonymManager synonymManager, RegexManager regexManager){
        JSONParser parser = new JSONParser();

        try{
            Object obj = parser.parse(new FileReader(filePath));

            JSONObject root = (JSONObject) obj;

            JSONObject rasaNLUData = (JSONObject)root.get("rasa_nlu_data");

            JSONArray commonExamples = (JSONArray) rasaNLUData.get("common_examples");
            JSONArray entitySynonyms = (JSONArray) rasaNLUData.get("entity_synonyms");
            JSONArray regexFeatures = (JSONArray) rasaNLUData.get("regex_features");

            if(commonExamples != null){
                fillIntentsandEntities(commonExamples,intentManager,entityManager);
            }
            if(entitySynonyms != null){
                fillSynonyms(entitySynonyms,synonymManager);
            }
            if(regexFeatures != null){
                fillRegex(regexFeatures,regexManager);
            }




        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void fillRegex(JSONArray regexFeatures, RegexManager regexManager) {
        Iterator<JSONObject> regexIterator = regexFeatures.iterator();
        while (regexIterator.hasNext()){
            JSONObject regex = regexIterator.next();
            String name = (String) regex.get("name");
            String pattern = (String) regex.get("pattern");
            regexManager.addRegex(name,pattern);
        }
    }


    void fillIntentsandEntities(JSONArray commonExamples, IntentManager intentManager, EntityManager entityManager){
        Iterator<JSONObject> exampleIterator = commonExamples.iterator();
        while(exampleIterator.hasNext()){
            JSONObject example = exampleIterator.next();
            String intentName = (String) example.get("intent");
            String exampleText = (String) example.get("text");
            JSONArray entities = (JSONArray) example.get("entities");

            if(!intentManager.getIntents().containsKey(intentName)) {
                intentManager.addIntent(intentName);
            }

            String [] splittedExampleText = null;
            Iterator<JSONObject> entityIterator = entities.iterator();
            while(entityIterator.hasNext()){
                JSONObject entity = entityIterator.next();
                String entityName = (String) entity.get("entity");
                String entityValue = (String) entity.get("value");
                splittedExampleText = exampleText.split(" ");
                for(int i = 0; i < splittedExampleText.length; i++){
                    if(splittedExampleText[i].equals(entityValue)){
                        splittedExampleText[i] = "@"+entityName;
                    }
                }
                exampleText = Arrays.stream(splittedExampleText).collect(Collectors.joining(" "));
                entityManager.addEntity(entityName);
                entityManager.addEntityValue(entityName,entityValue);
            }


            intentManager.addIntentExample(intentName, exampleText );
        }
    }


    public void fillSynonyms(JSONArray entitySynonyms, SynonymManager synonymManager){
        Iterator<JSONObject> synonymIterator = entitySynonyms.iterator();
        while(synonymIterator.hasNext()){
            JSONObject synonym = synonymIterator.next();
            String value = (String) synonym.get("value");
            JSONArray synonymArray = (JSONArray) synonym.get("synonyms");

            synonymManager.addSynonym(value);
            Iterator<String> synonymArrayIterator = synonymArray.iterator();
            while(synonymArrayIterator.hasNext()){
                synonymManager.addSynonymValue(value,synonymArrayIterator.next());
            }

        }
    }
}
