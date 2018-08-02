package main.pythonProcessing;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import main.application.GUI;
import main.fileHandling.RasaFileManager;
import main.fileHandling.RasaFileManagerImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoreLoadTestPythonProcessor extends Service<Process> {

    private String coreModel;
    private String nluModel;

    public CoreLoadTestPythonProcessor(String coreModel, String nluModel){
        this.coreModel = coreModel;
        this.nluModel = nluModel;
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Process call() throws Exception {
                String out = "";
                String fullPath = getClass().getResource("runBot.py").getPath();
                String correctPath = fullPath.replaceFirst("/","");


                String formattedCommand = String.format(CoreCommands.LOAD_AND_TEST_BOT.getCommandString(),
                        correctPath,
                        GUI.getWorkSpace()+RasaFileManagerImpl.FOLDERS.CORE_MODEL_FOLDER.getFolderName()+coreModel+"/",
                        GUI.getWorkSpace()+RasaFileManagerImpl.FOLDERS.NLU_MODEL_FOLDER.getFolderName()+"default/"+nluModel+"/");


                List<String> commandList = Arrays.asList(formattedCommand.split(" "));
                ProcessBuilder builder = new ProcessBuilder(commandList);
                builder = builder.directory(new File(GUI.getWorkSpace()));



                builder.redirectErrorStream(true);
                Process process = builder.start();



                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                    if (line.equals("Bot geladen")) {
                        return process;
                    }
                }
                return null;

            }
        };
    }
}
