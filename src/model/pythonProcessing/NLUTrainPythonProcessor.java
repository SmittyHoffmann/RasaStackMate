package model.pythonProcessing;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import main.GUI;
import model.fileHandling.RasaFileManagerImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NLUTrainPythonProcessor extends Service<List<String>>{

    public NLUTrainPythonProcessor(String trainFileName,String modelName){
        this.trainFileName = trainFileName;
        this.modelName = modelName;
    }


    String trainFileName;
    String modelName;



    public void setTrainFileName(String fileName){
        this.trainFileName = fileName;
    }

    public void setModelName(String modelName){
        this.modelName = modelName;
    }

    @Override
    protected Task<List<String>> createTask() {
            return new Task<>() {

                @Override
                protected List<String> call() throws Exception {
                    String formattedCommand = String.format(NLUCOMMANDS.TRAIN_NLU.getCommandString(),
                            GUI.getWorkSpace() + "\\" + RasaFileManagerImpl.FOLDERS.NLU_MODEL_FOLDER.getFolderName(),
                            GUI.getWorkSpace() + "\\" + RasaFileManagerImpl.FOLDERS.TRAIN_DATA_FOLDER.getFolderName() + "\\" + trainFileName,
                            GUI.getWorkSpace() + RasaFileManagerImpl.FOLDERS.SPACY_CONFIG_FILE.getFolderName(), modelName);
                    System.out.println(formattedCommand);
                    List<String> commandList = Arrays.asList(formattedCommand.split(" "));


                    ProcessBuilder builder = new ProcessBuilder(commandList);
                    builder.redirectErrorStream(true);
                    Process process = builder.start();
                    BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    int exitCode = process.waitFor();
                    List<String> output = new ArrayList<>();
                    String line;
                    while ((line = in.readLine()) != null) {
                        output.add(line);
                        System.out.println(line);
                    }
                    return output;
                }

            };

        }


}

