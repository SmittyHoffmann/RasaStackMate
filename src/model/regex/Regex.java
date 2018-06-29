package model.regex;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Regex {
    private final StringProperty name;
    private final StringProperty pattern;

    public Regex(String name,String pattern){
        this.name = new SimpleStringProperty(name);
        this.pattern = new SimpleStringProperty(pattern);

    }

    public String getName(){
        return this.name.get();
    }
    public void setName(String name){
        this.name.set(name);
    }

    public StringProperty nameProperty(){
        return name;
    }

    public String getPattern(){
        return this.pattern.get();
    }

    public void setPattern(String pattern){
        this.pattern.set(pattern);
    }

    public StringProperty patternProperty(){
        return this.pattern;
    }

}
