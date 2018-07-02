package model;

import main.GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class NLUPythonProcessorImpl implements NLUPythonProcessor{

    public enum NLUCOMMANDS{
        TRAIN_NLU("python -m rasa_nlu.train -o %s -d %s -c %s --fixed_model_name %s");

        private String command;

        NLUCOMMANDS(String commandString){ this.command = commandString;}

        public String getCommandString(){
            return this.command;
        }


    }

    public NLUPythonProcessorImpl(){

    }

    public void trainNLU(String trainFileName, String modelName){

        String formattedCommand = String.format(NLUCOMMANDS.TRAIN_NLU.getCommandString(),
                GUI.getWorkSpace()+"\\"+RasaFileManagerImpl.FOLDERS.NLU_MODEL_FOLDER.getFolderName(),
                RasaFileManagerImpl.FOLDERS.TRAIN_DATA_FOLDER.getFolderName()+"\\"+trainFileName,
                GUI.getWorkSpace()+RasaFileManagerImpl.FOLDERS.SPACY_CONFIG_FILE.getFolderName(), modelName);
        System.out.println(formattedCommand);
        List<String> commandList = Arrays.asList(formattedCommand.split(" "));


        ProcessBuilder builder = new ProcessBuilder(commandList);
        builder.redirectErrorStream(true);
        try {
            Process process = builder.start();
            Runnable thread = new WaitForThread(process);
            thread.run();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
