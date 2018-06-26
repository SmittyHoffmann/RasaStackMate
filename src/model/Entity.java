package model;


import java.util.ArrayList;

public class Entity {

    String entityName;
    ArrayList<String> entityValues;
    SimpleFilter<String> filter = new SimpleFilter<>();

    public Entity(String entityName, ArrayList<String> entityValues){
        this.entityName = entityName;
        this.entityValues = entityValues;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public ArrayList<String> getEntityValues() {
        return entityValues;
    }

    public void setEntityValues(ArrayList<String> entityValues) {
        this.entityValues = entityValues;
    }

    public void addEntityValue(String entityValue){
        this.entityValues.add(entityValue);
    }

    public void deleteEntityValue(String entityValue){
        filter.setFilter(entityValue);
        this.entityValues.removeIf(filter);
    }
}
