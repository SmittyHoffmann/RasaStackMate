package model.fileHandling;

import javafx.collections.ObservableList;
import org.json.simple.JSONObject;

public interface RasaFileManager {

    ObservableList<String> getNLUTrainFiles();
    ObservableList<String> getCoreStoryFiles();
    ObservableList<String> getCoreDomainFiles();
    ObservableList<String> getNLUModels();
    ObservableList<String> getCoreModelFiles();
    void writeNLUTrainData(JSONObject root, String fileName);

}
