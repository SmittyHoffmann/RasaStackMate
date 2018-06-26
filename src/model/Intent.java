package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Intent {


    ObservableList<String> examples;



    public Intent(){
        examples = FXCollections.observableArrayList();
    }

    public ObservableList<String> getExamples(){
        return this.examples;
    }

    public void addExample(String example){
        this.examples.add(example);
    }

    public void deleteExample(String example){
        this.examples.remove(example);
    }

    public void setExamples(List<String> exampleList){
        this.examples = FXCollections.observableArrayList(exampleList);
    }









}
