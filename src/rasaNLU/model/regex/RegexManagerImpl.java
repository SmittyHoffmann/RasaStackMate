package rasaNLU.model.regex;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

public class RegexManagerImpl implements RegexManager{
    ObservableList<Regex> regexList;

    public RegexManagerImpl(){
        this.regexList = FXCollections.observableArrayList();
    }

    public void addRegex(String name, String pattern){
        this.regexList.add(new Regex(name,pattern));
    }


    public void deleteRegex(Regex regex) {
        this.regexList.remove(regex);
    }


    public void setRegexName(Regex regex, String name) {
        List<Regex> buffer = this.regexList.stream().filter(r -> r.getName().equals(regex.getName()) && r.getPattern().equals(regex.getPattern())).collect(Collectors.toList());
        buffer.get(0).setName(name);
    }


    public void setRegexPattern(Regex regex, String pattern) {
        List<Regex> buffer = this.regexList.stream().filter(r -> r.getName().equals(regex.getName()) && r.getPattern().equals(regex.getPattern())).collect(Collectors.toList());
        buffer.get(0).setPattern(pattern);
    }

    public ObservableList<Regex> getRegexList(){
        return this.regexList;
    }



}

