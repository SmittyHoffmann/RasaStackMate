package rasaNLU.model.synonym;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;

public class SynonymManagerImpl implements SynonymManager{


    HashMap<String, Synonym> synonyms;
    ObservableList<String> synonymNames;


    public SynonymManagerImpl(){
        this.synonyms = new HashMap<>();
        this.synonymNames = FXCollections.observableArrayList();
    }
    @Override
    public void addSynonymValue(String synonymName, String value) {
        this.synonyms.get(synonymName).addValue(value);
    }

    @Override
    public void addSynonym(String synoynmName) {
        if(!this.synonyms.containsKey(synoynmName)){
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

    @Override
    public void changeSynonymName(String oldName, String newName) {
        if(synonyms.containsKey(newName)){
            for(String value : synonyms.get(oldName).getValues()){
                synonyms.get(newName).addValue(value);
            }

        }else{
            synonyms.put(newName,synonyms.get(oldName));
            synonymNames.add(newName);

        }
        synonyms.remove(oldName);
        synonymNames.remove(oldName);
    }
}
