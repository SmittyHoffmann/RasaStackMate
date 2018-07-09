package rasaNLU.model.nluJSONData;

import javafx.collections.ObservableList;
import rasaNLU.model.entity.Entity;
import rasaNLU.model.entity.EntityManager;
import rasaNLU.model.intent.Intent;
import rasaNLU.model.intent.IntentManager;
import rasaNLU.model.regex.Regex;
import rasaNLU.model.regex.RegexManager;
import rasaNLU.model.synonym.Synonym;
import rasaNLU.model.synonym.SynonymManager;
import org.json.simple.JSONObject;

import java.util.HashMap;

public interface NLUTrainDataGenerator {

    JSONObject generateTrainingData(HashMap<String,Intent> intents, HashMap<String,Entity> entities, HashMap<String, Synonym> synonyms, ObservableList<Regex> regexList);
    void fillNLUManagers(String fileName, IntentManager intentManager, EntityManager entityManager, SynonymManager synonymManager, RegexManager regexManager);
}
