package model.entity;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Entity {

    ObservableList<String> values;



    public Entity(){
        values = FXCollections.observableArrayList();
    }

    public ObservableList<String> getValues(){
        return this.values;
    }

    public void addValue(String value){

        if(!this.values.contains(value)){
            this.values.add(value);
        }

    }

    public void deleteValue(String value){
        this.values.remove(value);
    }

    public void setValues(List<String> valueList){
        this.values = FXCollections.observableArrayList(valueList);
    }

}
