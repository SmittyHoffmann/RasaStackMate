package model;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityManager {

    HashMap<String, Entity> entities;

    public EntityManager(HashMap<String, Entity> entities) {
        this.entities = entities;
    }

    public void addEntity(String entityName) {
        if(this.getEntity(entityName) == null){
            Entity newEntity = new Entity(entityName,new ArrayList<String>());
            this.entities.put(newEntity.getEntityName(), newEntity);
        }
    }

    public void deleteEntity(String entityName) {
        this.entities.remove(entityName);
    }

    public Entity getEntity(String entityName) {
        return this.entities.get(entityName);
    }


}
