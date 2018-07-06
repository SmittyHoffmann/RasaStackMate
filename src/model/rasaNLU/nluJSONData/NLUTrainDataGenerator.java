package model.rasaNLU.nluJSONData;

import javafx.collections.ObservableList;
import model.rasaNLU.entity.Entity;
import model.rasaNLU.entity.EntityManager;
import model.rasaNLU.intent.Intent;
import model.rasaNLU.intent.IntentManager;
import model.rasaNLU.regex.Regex;
import model.rasaNLU.regex.RegexManager;
import model.rasaNLU.synonym.Synonym;
import model.rasaNLU.synonym.SynonymManager;
import org.json.simple.JSONObject;

import java.util.HashMap;

public interface NLUTrainDataGenerator {

    JSONObject generateTrainingData(HashMap<String,Intent> intents, HashMap<String,Entity> entities, HashMap<String, Synonym> synonyms, ObservableList<Regex> regexList);
    void fillNLUManagers(String filePath, IntentManager intentManager, EntityManager entityManager, SynonymManager synonymManager, RegexManager regexManager);
}
