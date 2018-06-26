package model;

import java.util.HashMap;

public class nluExample {


    String text;
    HashMap<String, Integer> entities;

    public nluExample(String text, HashMap<String,Integer> entities){
        this.text = text;
        this.entities = entities;
    }

    public String getText(){
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public HashMap<String, Integer> getEntities() {
        return entities;
    }

    public void setEntities(HashMap<String, Integer> entities) {
        this.entities = entities;
    }


    public void addEntity(String entity, int startpos){
        this.entities.put(entity,startpos);
    }

    public void deleteEntity(String entity){
        this.entities.remove(entity);
    }
}
