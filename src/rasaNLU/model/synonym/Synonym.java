package rasaNLU.model.synonym;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Synonym {

    ObservableList<String> values;



    public Synonym(){
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

    public void setValues(List<String> exampleList){
        this.values = FXCollections.observableArrayList(exampleList);
    }
}
