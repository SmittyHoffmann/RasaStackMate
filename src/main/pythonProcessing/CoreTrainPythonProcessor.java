package main.pythonProcessing;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import main.application.GUI;
import main.fileHandling.RasaFileManagerImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoreTrainPythonProcessor extends Service<List<String>> {

    String storyFileName;

    String coreModelName;
    String domainName;
    String nluModelName;

    public CoreTrainPythonProcessor(String storyFileName, String coreModelName, String domainName, String nluModelName) {
        this.storyFileName = storyFileName;
        this.coreModelName = coreModelName;
        this.domainName = domainName;
        this.nluModelName = nluModelName;

    }


    @Override
    protected Task<List<String>> createTask() {

        return new Task<List<String>>() {
            @Override
            protected List<String> call() throws Exception {
                String formattedCommand = String.format(CoreCommands.TRAIN_CORE_MODEL.getCommandString(),
                        GUI.getWorkSpace() + RasaFileManagerImpl.FOLDERS.TRAIN_DATA_FOLDER.getFolderName() + "\\" + storyFileName,
                        GUI.getWorkSpace() + "\\" + RasaFileManagerImpl.FOLDERS.CORE_MODEL_FOLDER.getFolderName() + "\\" + coreModelName,
                        GUI.getWorkSpace() +"\\"+  domainName,
                        GUI.getWorkSpace() + "\\" + RasaFileManagerImpl.FOLDERS.NLU_MODEL_FOLDER.getFolderName() + "\\" + nluModelName);

                System.out.println(formattedCommand);
                List<String> commandList = Arrays.asList(formattedCommand.split(" "));
                List<String> output = new ArrayList<>();

                ProcessBuilder builder = new ProcessBuilder(commandList);
                builder.directory(new File(GUI.getWorkSpace()));


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

              //  BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errorIn = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                int exitCode = process.waitFor();


                output.add(Integer.toString(exitCode));
                return output;
            }
        };
    }


}
