package model.rasaCore.domain.template;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Template {

    ObservableList<String> utterances;

    public Template(){
        this.utterances = FXCollections.observableArrayList();
    }


    public ObservableList<String> getUtterances(){
        return this.utterances;
    }

    public void addUtterance(String utterance){
        if(!this.utterances.contains(utterance)){
            this.utterances.add(utterance);
        }

    }

    public void deleteUtterance(String utterance){
        this.utterances.remove(utterance);
    }

    public void setUtterances(List<String> utteranceList){
        this.utterances = FXCollections.observableArrayList(utteranceList);
    }
}
