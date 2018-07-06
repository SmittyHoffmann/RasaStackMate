package model.entity;

import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;

public interface EntityManager {

    void addEntityValue(String entityName, String value);
    void addEntity(String entityName);
    void deleteEntity(String entityName);
    Entity getEntity(String entityName);
    ObservableList<String> getEntityNames();
    HashMap<String,Entity> getEntities();
    void deleteIntentExample(String entityName, String value);
    ObservableList<String> getValuesToEntity(String entityName);
    ObservableList<String> getEntityPlaceHolders();
    ObservableList<String> getAllEntityValues();

    void changeEntityName(String currentEntityName, String changedName);

    void parseJSONEntities(JSONArray entities);
}

