package main.fileHandling;

import javafx.collections.ObservableList;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Interface für Klassen zum Schreiben von Trainingsdateien und dem Zugriff auf die Projektordner/Dateien
 */
public interface RasaFileManager {

    /**
     * Überprüft ob die benötigte OrdnerStruktur vorliegt und erstellt diese wenn nötig
     */
    void checkDirectories();

    /**
     * Gibt die NLU-Trainingsdateinamen zurück
     * @return Liste von NLU-Trainingsdateinamen
     */
    ObservableList<String> getNLUTrainFiles();
    /**
     * Gibt die Story-Dateinamen zurück
     * @return Liste von Story-Dateinamen
     */
    ObservableList<String> getCoreStoryFiles();
    /**
     * Gibt die Domaindateinamen zurück
     * @return Liste von Domaindateinamen
     */
    ObservableList<String> getCoreDomainFiles();
    /**
     * Gibt die NLU-Modelnamen zurück
     * @return Liste von NLU-Modelnamen
     */
    ObservableList<String> getNLUModels();
    /**
     * Gibt die Core-Modelnamen zurück
     * @return Liste von Core-Modelnamen
     */
    ObservableList<String> getCoreModels();

    /**
     * Schreibt die JSON-Trainingsdaten für die NLU-Komponente in die angegebene Datei
     * @param root Root-JSON-Objekt das in die Datei geschrieben wird
     * @param fileName Dateiname der zu schreibenden Datei
     */
    void writeNLUTrainData(JSONObject root, String fileName);

    /**
     *  Schreibt die Story-Daten in die angegebene Datei
     * @param resultList Story-Trainingsdaten
     * @param fileName Dateiname der zu schreibenden Datei
     */
    void writeStoryFile(List<String>resultList, String fileName);

    /**
     *
     * @param fileName Dateiname der zu schreibenden Datei
     * @param intents Daten zu den Intents der Domaindatei
     * @param entities Daten zu den Entities der Domaindatei
     * @param slots Daten zu den Slots der Domaindatei
     * @param templates Daten zu den Templates der Domaindatei
     * @param actions Daten zu den Actions der Domaindatei
     */
    void writeDomainFile(String fileName, Map<String, List<String>> intents, Map<String, List<String>> entities, Map<String, Map<String, Map<String,Object>>> slots, List<String> templates, Map<String, List<String>> actions);
}
