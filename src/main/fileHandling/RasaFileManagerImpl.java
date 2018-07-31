package main.fileHandling;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.application.GUI;
import org.json.simple.JSONObject;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RasaFileManagerImpl implements RasaFileManager {

    public enum FOLDERS {
        TRAIN_DATA_FOLDER("/data"),
        NLU_MODEL_FOLDER("/models/nlu/"),
        CORE_MODEL_FOLDER("/models/dialogue/"),
        SPACY_CONFIG_FILE("/config_spacy.json");


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

    public void checkDirectories(){
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

        File spacyConfig = new File(GUI.getWorkSpace()+FOLDERS.SPACY_CONFIG_FILE.getFolderName());
        if(!spacyConfig.exists()){
            JSONObject root = new JSONObject();
            root.put("pipeline","spacy_sklearn");
            root.put("language","de");

            try {
                FileWriter writer = new FileWriter(spacyConfig);
                writer.write(root.toJSONString());
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        File[] nluModels = new File(this.nluModelFolder.getPath()+"\\"+"default").listFiles();
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

    @Override
    public void writeDomainFile(String fileName, Map<String, List<String>> intents, Map<String, List<String>> entities, Map<String, Map<String, Map<String, Object>>> slots, List<String> templates, Map<String, List<String>> actions) {
        try {
            String filePath = GUI.getWorkSpace()+"\\"+fileName+".yml";
            FileWriter writer = new FileWriter(filePath);
            DumperOptions options = new DumperOptions();
            options.setPrettyFlow(true);
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

            Yaml yaml = new Yaml(options);

            yaml.dump(intents,writer);

            writer = new FileWriter(filePath,true);

            writer.write("\n");
            yaml.dump(entities,writer);
            writer.write("\n");

            yaml.dump(slots,writer);
            writer.write("\n");

            for(String line : templates){
                writer.write(line);
            }

            writer.write("\n");
            yaml.dump(actions,writer);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeStoryFile(List<String>resultList, String fileName){
        try {
            String filePath = trainDataFolder.getAbsolutePath() +"/" +  fileName+".md";
            FileWriter writer = new FileWriter(filePath);
            writer.write("<!-- Generated by RasaStackMate -->\n");
            writer.close();
            writer = new FileWriter(filePath,true);
            for(String string : resultList){
                writer.write(string);
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
