package model.nluJSONData;

import javafx.collections.ObservableList;
import model.entity.Entity;
import model.entity.EntityManager;
import model.intent.Intent;
import model.intent.IntentManager;
import model.regex.Regex;
import model.regex.RegexManager;
import model.synonym.Synonym;
import model.synonym.SynonymManager;
import org.json.simple.JSONObject;

import java.util.HashMap;

public interface NLUTrainDataGenerator {

    JSONObject generateTrainingData(HashMap<String,Intent> intents, HashMap<String,Entity> entities, HashMap<String, Synonym> synonyms, ObservableList<Regex> regexList);
    void fillNLUManagers(String filePath, IntentManager intentManager, EntityManager entityManager, SynonymManager synonymManager, RegexManager regexManager);
}
