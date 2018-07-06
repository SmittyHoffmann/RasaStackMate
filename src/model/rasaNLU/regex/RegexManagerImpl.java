package model.rasaNLU.regex;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RegexManagerImpl implements RegexManager{
    ObservableList<Regex> regexList;

    public RegexManagerImpl(){
        this.regexList = FXCollections.observableArrayList();
    }

    public void addRegex(String name, String pattern){
        this.regexList.add(new Regex(name,pattern));
    }

    public ObservableList<Regex> getRegexList(){
        return this.regexList;
    }
}


