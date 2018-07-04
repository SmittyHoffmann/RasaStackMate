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

public class NLULoadTestPythonProcessor extends Service<Process> {
    String modelName;

    public NLULoadTestPythonProcessor(String modelName) {
        this.modelName = modelName;
    }

    @Override
    protected Task<Process> createTask() {
        return new Task<>() {
            @Override
            protected Process call() throws Exception {
                List<String> output = new ArrayList<>();
                String test = getClass().getResource("nluInterpreter.py").getPath();
                test = test.substring(1);
                String formattedCommand = String.format(NLUCOMMANDS.LOAD_AND_TEST_NLU.getCommandString(), test, GUI.getWorkSpace()+"\\"+ RasaFileManagerImpl.FOLDERS.NLU_MODEL_FOLDER.getFolderName() + "default\\" +modelName);
                System.out.println(formattedCommand);
                List<String> commandList = Arrays.asList(formattedCommand.split(" "));
                ProcessBuilder builder = new ProcessBuilder(commandList);
                builder.redirectErrorStream(true);
                Process process = builder.start();
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                //while (process.isAlive()) {
                    while ((line = in.readLine()) != null) {
                        if (line.equals("Model geladen")) {
                            return process;
                        }
                    }
          //      }
                return null;
            }
        };
    }
}
