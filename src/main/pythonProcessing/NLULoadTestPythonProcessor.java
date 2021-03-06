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

/**
 * Service zum Laden eines NLU-Test-Python-Skripts
 */
public class NLULoadTestPythonProcessor extends Service<Process> {
    /**
     * Name des NLU-Modells
     */
    private String modelName;

    /**
     * Erzeugt Objekt und setzt NLU-Model-Name
     * @param modelName Name des zu testenden NLU-Modells
     */
    public NLULoadTestPythonProcessor(String modelName) {
        this.modelName = modelName;
    }

    /**
     * Erzeugt Task zum Starten eines Python-Skripts
     * @return erzeugter Task
     */
    @Override
    protected Task<Process> createTask() {
        return new Task<>() {
            /**
             * Erzeugt Python-Prozess und liest Output. Wenn "Bot geladen" gelesen wird, wird der Prozess zurückgegeben und der Task ist zuende
             * @return aktiver Python-Prozess
             */
            @Override
            protected Process call() throws Exception {
                List<String> output = new ArrayList<>();
                String test = getClass().getResource("nluInterpreter.py").getPath();
                test = test.substring(1);
                String formattedCommand = String.format(NluCommands.LOAD_AND_TEST_NLU.getCommandString(), test, GUI.getWorkSpace()+"\\"+ RasaFileManagerImpl.FOLDERS.NLU_MODEL_FOLDER.getFolderName() + "default\\" +modelName);
                System.out.println(formattedCommand);
                List<String> commandList = Arrays.asList(formattedCommand.split(" "));
                ProcessBuilder builder = new ProcessBuilder(commandList);
                builder.redirectErrorStream(true);
                Process process = builder.start();
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                        if (line.equals("Model geladen")) {
                            return process;
                        }
                    }
                return null;
            }
        };
    }
}
