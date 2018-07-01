package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.GUI;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RasaFileManagerImpl implements RasaFileManager {

    public enum FOLDERS {
        TRAIN_DATA_FOLDER("/data"),
        NLU_MODEL_FOLDER("/models/nlu/default/"),
        CORE_MODEL_FOLDER("/models/dialogue/");


        private String folderName;

        FOLDERS(String path) {
            this.folderName = path;
        }

        public String getFolderName() {
            return this.folderName;
        }
    }

    File trainDataFolder;
    File nluModelFolder;
    File coreModelFolder;
    File workspaceFolder;

    public RasaFileManagerImpl() {
        this.workspaceFolder = new File(GUI.getWorkSpace());
        this.checkDirectories();
    }

    public void checkDirectories() {
        this.trainDataFolder = new File(GUI.getWorkSpace() + FOLDERS.TRAIN_DATA_FOLDER.getFolderName());
        if (!trainDataFolder.exists()) {
            trainDataFolder.mkdir();
        }
        this.nluModelFolder = new File(GUI.getWorkSpace() + FOLDERS.NLU_MODEL_FOLDER.getFolderName());
        if (!nluModelFolder.exists()) {
            nluModelFolder.mkdirs();
        }
        this.coreModelFolder = new File(GUI.getWorkSpace() + FOLDERS.CORE_MODEL_FOLDER.getFolderName());
        if (!coreModelFolder.exists()) {
            coreModelFolder.mkdirs();
        }

    }

    public ObservableList<String> getNLUTrainFiles() {

        File[] JSONfiles = trainDataFolder.listFiles(new FileExtensionFilter(".json"));

        ObservableList<String> jsonFileNames = FXCollections.observableArrayList();
        for (File f : JSONfiles) {
            jsonFileNames.add(f.getName());
        }

        return jsonFileNames;
    }

    @Override
    public ObservableList<String> getCoreStoryFiles() {

        File[] storyFiles = this.trainDataFolder.listFiles(new FileExtensionFilter(".md"));

        ObservableList<String> storyFileNames = FXCollections.observableArrayList();
        for (File f : storyFiles) {
            storyFileNames.add(f.getName());
        }

        return storyFileNames;
    }

    @Override
    public ObservableList<String> getCoreDomainFiles() {

        File[] domainFiles = this.workspaceFolder.listFiles(new FileExtensionFilter(".yml"));

        ObservableList<String> domainFileNames = FXCollections.observableArrayList();
        for (File f : domainFiles) {
            domainFileNames.add(f.getName());
        }

        return domainFileNames;

    }

    @Override
    public ObservableList<String> getNLUModels() {
        File[] nluModels = this.nluModelFolder.listFiles();
        ObservableList<String> nluModelNames = FXCollections.observableArrayList();

        for (File f : nluModels) {
            if (f.isDirectory()) {
                nluModelNames.add(f.getName());
            }
        }

        return nluModelNames;
    }

    @Override
    public ObservableList<String> getCoreModelFiles() {
        File[] coreModels = this.coreModelFolder.listFiles();
        ObservableList<String> coreModelNames = FXCollections.observableArrayList();

        for (File f : coreModels) {
            if (f.isDirectory()) {
                coreModelNames.add(f.getName());
            }
        }

        return coreModelNames;
    }

    @Override
    public void writeNLUTrainData(JSONObject root, String fileName) {
        try (FileWriter file = new FileWriter(trainDataFolder.getAbsolutePath() +"/" +  fileName+".json")) {
            file.write(root.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void readTrainData(){
        JSONParser parser = new JSONParser();

    }
}
