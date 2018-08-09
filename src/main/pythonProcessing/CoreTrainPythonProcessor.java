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

/**
 * Service-Klasse zum Starten des Core-Trainings
 */
public class CoreTrainPythonProcessor extends Service<List<String>> {
    /**
     * Name der Story-Datei
     */
    private String storyFileName;
    /**
     * Name des gewünschten Models
     */
    private String coreModelName;
    /**
     * Name der Domain-Datei
     */
    private String domainName;
    /**
     * Name des NLU-Models
     */
    private String nluModelName;

    /**
     * Erzeugt neues Objekt zum Starten des Core-Trainings
     * @param storyFileName Name der Story-Datei
     * @param coreModelName Gewünschter Name des neuen Core-Models
     * @param domainName Name der Domain-Datei
     * @param nluModelName Name des NLU-Models
     */
    public CoreTrainPythonProcessor(String storyFileName, String coreModelName, String domainName, String nluModelName) {
        this.storyFileName = storyFileName;
        this.coreModelName = coreModelName;
        this.domainName = domainName;
        this.nluModelName = nluModelName;

    }

    /**
     * Erzeugt Task zum Trainieren eines Core-Models
     * @return erzeugter Task
     */
    @Override
    protected Task<List<String>> createTask() {

        return new Task<>() {
            /**
             * Startet das Training und wartet bis es fertig ist. Danach wird der Output zurückgegeben
             * @return Output des Trainings
             */
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
                final Thread ioThread = new Thread(() -> {
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
                });
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


                int exitCode = process.waitFor();


                output.add(Integer.toString(exitCode));
                return output;
            }
        };
    }


}
