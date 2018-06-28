package model;

import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public interface IntentManager {

    void addIntentExample(String intentName, String example);

    void addIntent(String intentName);

    void deleteIntent(String intentName);

    Intent getIntent(String intentName);

    ObservableList<String> getIntentNames();

    HashMap<String,Intent> getIntents();

    void deleteIntentExample(String intentName, String example);

    public ObservableList<String> getExamplesToIntent(String intentName);

    void changeIntentName(String currentIntentName, String changedName);
}
