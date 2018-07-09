package main.fileHandling;

import javafx.collections.ObservableList;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

public interface RasaFileManager {

    ObservableList<String> getNLUTrainFiles();
    ObservableList<String> getCoreStoryFiles();
    ObservableList<String> getCoreDomainFiles();
    ObservableList<String> getNLUModels();
    ObservableList<String> getCoreModelFiles();
    void writeNLUTrainData(JSONObject root, String fileName);

    void writeDomainFile(String fileName, Map<String, List<String>> intents, Map<String, List<String>> entities, Map<String, Map<String, Map<String,Object>>> slots, List<String> templates, Map<String, List<String>> actions);
}
