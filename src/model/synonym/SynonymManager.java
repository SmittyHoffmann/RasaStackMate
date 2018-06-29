package model.synonym;

import javafx.collections.ObservableList;

import java.util.HashMap;

public interface SynonymManager {
    void addSynonymValue(String synonymName, String value);
    void addSynonym(String synoynmName);
    void deleteSynonym(String synonymName);
    Synonym getSynonym(String synonymName);
    ObservableList<String> getSynonymNames();
    HashMap<String,Synonym> getSynonyms();
    void deleteSynonymValue(String synonymName, String value);
    ObservableList<String> getValuesToSynonym(String synonymName);

    void changeSynonymName(String oldName, String newName);
}
