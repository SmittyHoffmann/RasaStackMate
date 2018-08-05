package main.pythonProcessing;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import main.application.GUI;
import main.fileHandling.RasaFileManager;
import main.fileHandling.RasaFileManagerImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class CoreLoadTestPythonProcessor extends Service<Process> {

    private String domainFile;
    private String storyFile;
    private String coreModel;
    private String nluModel;
    private boolean trainOnlineMode;

    public CoreLoadTestPythonProcessor(String coreModel, String nluModel){
        this.coreModel = coreModel;
        this.nluModel = nluModel;
        this.trainOnlineMode = false;
    }

    public CoreLoadTestPythonProcessor(String storyFile,String domainFile,String nluModel){
        this.storyFile = storyFile;
        this.domainFile = domainFile;
        this.nluModel = nluModel;
        this.trainOnlineMode = true;
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Process call() throws Exception {
                String out = "";
                String formattedCommand;
                if(!trainOnlineMode){

                formattedCommand = String.format(CoreCommands.LOAD_RUN_BOT.getCommandString(),
                        GUI.getWorkSpace()+"/runBot.py",
                        GUI.getWorkSpace()+RasaFileManagerImpl.FOLDERS.CORE_MODEL_FOLDER.getFolderName()+coreModel+"/",
                        GUI.getWorkSpace()+RasaFileManagerImpl.FOLDERS.NLU_MODEL_FOLDER.getFolderName()+"default/"+nluModel+"/");
                }else{
                    formattedCommand = String.format(CoreCommands.LOAD_TRAIN_ONLINE_BOT.getCommandString(),
                            GUI.getWorkSpace()+"/trainOnline.py",
                            GUI.getWorkSpace()+RasaFileManagerImpl.FOLDERS.TRAIN_DATA_FOLDER.getFolderName()+"\\"+storyFile,
                            GUI.getWorkSpace() +"\\"+  domainFile,
                            GUI.getWorkSpace()+RasaFileManagerImpl.FOLDERS.NLU_MODEL_FOLDER.getFolderName()+"default/"+nluModel+"/");
                }

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
