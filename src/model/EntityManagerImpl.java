package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.inject.Singleton;
import java.util.HashMap;

@Singleton
public class EntityManagerImpl implements EntityManager{

    



    HashMap<String, Entity> entities;
    ObservableList<String> entityNames;


    public EntityManagerImpl() {
        this.entities = new HashMap<>();
        this.entityNames = FXCollections.observableArrayList();
    }


    public void addEntityValue(String entityName, String value){
        entities.get(entityName).addValue(value);
    }


    public void addEntity(String entityName){
        if(!entities.containsKey(entityName)){
            this.entities.put(entityName,new Entity());
            this.entityNames.add(entityName);
        }
    }


    public void deleteEntity(String entityName) {
        this.entities.remove(entityName);
        this.entityNames.remove(entityName);
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


}



