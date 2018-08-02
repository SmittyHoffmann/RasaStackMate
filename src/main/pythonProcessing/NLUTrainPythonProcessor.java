package main.pythonProcessing;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import main.application.GUI;
import main.fileHandling.RasaFileManagerImpl;

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
                    String formattedCommand = String.format(NluCommands.TRAIN_NLU.getCommandString(),
                            GUI.getWorkSpace() + "\\" + RasaFileManagerImpl.FOLDERS.NLU_MODEL_FOLDER.getFolderName(),
                            GUI.getWorkSpace() + "\\" + RasaFileManagerImpl.FOLDERS.TRAIN_DATA_FOLDER.getFolderName() + "\\" + trainFileName,
                            GUI.getWorkSpace() + RasaFileManagerImpl.FOLDERS.SPACY_CONFIG_FILE.getFolderName(), modelName);
                    System.out.println(formattedCommand);
                    List<String> commandList = Arrays.asList(formattedCommand.split(" "));


                    ProcessBuilder builder = new ProcessBuilder(commandList);
                    List<String> output = new ArrayList<>();
                    Process process = builder.start();

                    final Thread ioThread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                final BufferedReader reader = new BufferedReader(
                                        new InputStreamReader(process.getInputStream()));
                                String line = null;
                                while ((line = reader.readLine()) != null) {
                                    System.out.println(line);
                                    output.add(line);
                                }
                                reader.close();
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    final Thread errorThread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                final BufferedReader errorReader = new BufferedReader(
                                        new InputStreamReader(process.getErrorStream()));
                                String error = null;
                                while ((error = errorReader.readLine()) != null) {
                                    System.out.println(error);
                                    output.add(error);
                                }
                                errorReader.close();
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    ioThread.start();
                    errorThread.start();



                    BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    int exitCode = process.waitFor();
                    output.add(Integer.toString(exitCode));
                    String line;

                    return output;
                }

            };

        }


}

