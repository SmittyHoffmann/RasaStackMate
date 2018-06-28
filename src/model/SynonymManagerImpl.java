package model;

import javafx.collections.ObservableList;

import java.util.HashMap;

public class SynonymManagerImpl implements SynonymManager{


    HashMap<String, Synonym> synonyms;
    ObservableList<String> synonymNames;

    @Override
    public void addSynonymValue(String synonymName, String value) {
        this.synonyms.get(synonymName).addValue(value);
    }

    @Override
    public void addSynonym(String synoynmName) {
        if(this.synonyms.containsKey(synoynmName)){
            this.synonyms.put(synoynmName,new Synonym());
            this.synonymNames.add(synoynmName);
        }


    }

    @Override
    public void deleteSynonym(String synonymName) {
        this.synonymNames.remove(synonymName);
        this.synonyms.remove(synonymName);
    }

    @Override
    public Synonym getSynonym(String synonymName) {
        return this.synonyms.get(synonymName);
    }

    @Override
    public ObservableList<String> getSynonymNames() {
        return this.synonymNames;
    }

    @Override
    public HashMap<String, Synonym> getSynonyms() {
        return this.synonyms;
    }

    @Override
    public void deleteSynonymValue(String synonymName, String value) {
        this.synonyms.get(synonymName).deleteValue(value);
    }

    @Override
    public ObservableList<String> getValuesToSynonym(String synonymName) {
        return this.synonyms.get(synonymName).getValues();
    }
}
