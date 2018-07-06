package model.rasaNLU.entity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Iterator;


public class EntityManagerImpl implements EntityManager{

    



    HashMap<String, Entity> entities;
    ObservableList<String> entityNames;
    ObservableList<String> entityPlaceHolders;
    ObservableList<String> entityValues;



    public EntityManagerImpl() {
        this.entities = new HashMap<>();
        this.entityNames = FXCollections.observableArrayList();
        this.entityPlaceHolders = FXCollections.observableArrayList();
        this.entityValues = FXCollections.observableArrayList();
    }


    public void addEntityValue(String entityName, String value){
        entities.get(entityName).addValue(value);
        entityValues.add(value);
    }


    public void addEntity(String entityName){
        if(!entities.containsKey(entityName)){
            this.entities.put(entityName,new Entity());
            this.entityNames.add(entityName);
            this.addPlaceHolder(entityName);
        }
    }

    private void addPlaceHolder(String entityName) {
        this.entityPlaceHolders.add("@"+entityName);
    }


    public void deleteEntity(String entityName) {
        this.removeValuesOfEntity(entityName);
        this.entities.remove(entityName);
        this.entityNames.remove(entityName);
        this.removePlaceHolder(entityName);
    }

    private void removeValuesOfEntity(String entityName) {
        for(String value : entities.get(entityName).getValues()){
            this.entities.remove(value);
        }
    }

    private void removePlaceHolder(String entityName) {
        this.entityPlaceHolders.remove("@"+entityName);
    }


    public Entity getEntity(String entityName) {
        return this.entities.get(entityName);
    }


    public ObservableList<String> getEntityNames() {
        return this.entityNames;
    }


    public HashMap<String,Entity> getEntities(){
        return this.entities;
    }

    public void deleteIntentExample(String entityName, String value){
        this.entities.get(entityName).deleteValue(value);
    }

    public ObservableList<String> getValuesToEntity(String entityName){
        return this.entities.get(entityName).getValues();
    }

    @Override
    public ObservableList<String> getEntityPlaceHolders() {
        return this.entityPlaceHolders;
    }

    @Override
    public ObservableList<String> getAllEntityValues() {
        return this.entityValues;
    }

    @Override
    public void changeEntityName(String oldName, String newName) {
        if(entities.containsKey(newName)){
            for(String value : entities.get(oldName).getValues()){
                entities.get(newName).addValue(value);
            }
        }else{
            entities.put(newName,entities.get(oldName));
            entityNames.add(newName);

        }
        entities.remove(oldName);
        entityNames.remove(oldName);

    }

    @Override
    public void parseJSONEntities(JSONArray entities) {
        Iterator<JSONObject> iterator = entities.iterator();
        while(iterator.hasNext()){
            JSONObject entity = iterator.next();
            String entityName = (String) entity.get("entity");
            String entityValue = (String) entity.get("value");
            this.addEntity(entityName);
            this.addEntityValue(entityName,entityValue);

        }
    }


}



