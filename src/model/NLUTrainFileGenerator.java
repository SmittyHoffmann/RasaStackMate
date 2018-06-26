package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class NLUTrainFileGenerator {

    IntentManagerImpl intentManagerImpl;
    EntityManager entityManager;

    public NLUTrainFileGenerator(IntentManagerImpl intentManagerImpl, EntityManager entityManager) {
        this.intentManagerImpl = intentManagerImpl;
        this.entityManager = entityManager;
    }

    public void generateNLUTrainFile(String fileName) throws IOException {
        JSONObject root = new JSONObject();
        JSONObject rasa_nlu_data = new JSONObject();
        root.put("rasa_nlu_data", rasa_nlu_data);
        HashMap<String, Intent> intents = intentManagerImpl.getIntents();
        rasa_nlu_data.put("common_examples", this.generateCommonExamples(intents));

        try(FileWriter file = new FileWriter(fileName)){
            file.write(root.toJSONString());
            System.out.println("That went well");
        }

    }

    private JSONArray generateCommonExamples(HashMap<String, Intent> intents) {
        JSONArray common_examples = new JSONArray();
        for (HashMap.Entry<String, Intent> entry : intents.entrySet()) {
            String intentName = entry.getKey();
            Intent bufferIntent = entry.getValue();
            //          for (nluExample example : bufferIntent.getExamples()) {
            //           if (example.getEntities().size() == 0) {
            //          JSONObject generatedExample = generateExampleWithoutEntities(intentName, example);
            //              common_examples.add(generatedExample);
            //     } else {
            //          this.generateExamplesWithEntities(intentName, example, common_examples);
            //       }
            //     }

            //   }


        }
        return common_examples;
    }

 //   private JSONObject generateExampleWithoutEntities(String intentName, nluExample example) {

 //       JSONObject bufferExample = new JSONObject();
   //     JSONArray emptyArray = new JSONArray();

  //      bufferExample.put("entities", emptyArray);
 //       bufferExample.put("intent", intentName);
  //      bufferExample.put("text", example.getText());

  //      return bufferExample;

   // }

    private void generateExamplesWithEntities(String
                                                      intentName, nluExample example, JSONArray common_Examples) {
        HashMap<String, JSONArray> textEntityMap = new HashMap<>();
        this.generateTextandEntities(example.getText(), new JSONArray(), example.getEntities(),textEntityMap);
        for (HashMap.Entry<String, JSONArray> entry : textEntityMap.entrySet()) {
            String finalText = entry.getKey();
            JSONArray finalEntityArray = entry.getValue();
            JSONObject bufferExample = new JSONObject();
            bufferExample.put("entities",finalEntityArray);
            bufferExample.put("intent",intentName);
            bufferExample.put("text",finalText);
            common_Examples.add(bufferExample);
        }

    }

    private void generateTextandEntities(String text, JSONArray entities,HashMap<String,Integer> entityPositionMap, HashMap<String,JSONArray> finalMap){
        boolean containsEntity = false;
        String entityName = "";
        String [] splittedText = text.split(" ");
        for(int i = 0; i < splittedText.length; i++){
            if(splittedText[i].startsWith("@")){
                containsEntity = true;
                entityName = splittedText[i].replace("@","");
                i = splittedText.length;
            }
        }
        if(containsEntity){
            for(String value : entityManager.getEntity(entityName).getEntityValues()){
                String bufferText = text.replace("@"+entityName, value);
                JSONObject entityObject = new JSONObject();
                entityObject.put("start", entityPositionMap.get(entityName));
                entityObject.put("end", entityPositionMap.get(entityName) + value.length());
                entityObject.put("value", value);
                entityObject.put("entity", entityName);
                JSONArray workingArray = (JSONArray) entities.clone();
                workingArray.add(entityObject);
                this.generateTextandEntities(bufferText,workingArray,entityPositionMap, finalMap);
            }
        }
        else{
            finalMap.put(text,entities);
        }

    }

    private void insertEntityValues(String text, JSONArray entities, HashMap<String, Integer> entityMap, HashMap<String,JSONArray> finalMap) {
            String[] splittedText = text.split(" ");
            JSONArray workingArray = new JSONArray();
            boolean containsEntity = false;

            for (String s : splittedText) {
                if (s.startsWith("@")) {
                    String entityName = s.replace("@", "");
                    Entity bufferEntity = entityManager.getEntity(entityName);
                    for (String value : bufferEntity.getEntityValues()) {

                        String bufferText = text.replace(s, value);
                        JSONObject entityObject = new JSONObject();
                        entityObject.put("start", entityMap.get(bufferEntity));
                        entityObject.put("end", entityMap.get(bufferEntity) + value.length());
                        entityObject.put("value", value);
                        entityObject.put("entity", entityName);
                        if(!entities.isEmpty()){
                            workingArray = (JSONArray) entities.clone();
                        }
                        workingArray.add(entityObject);

                        for(String t : bufferText.split(" ")){
                            if(t.startsWith("@")){
                                containsEntity = true;
                            }
                        }

                        if(containsEntity) {
                            this.insertEntityValues(bufferText, workingArray, entityMap,finalMap);
                        }
                        else{
                            finalMap.put(bufferText, workingArray);
                        }
                    }
                }
            }


    }
}