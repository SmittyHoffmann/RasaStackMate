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

/**
 * Service-Klasse zum Starten eines Python-Skripts für Test oder Interaktives Training
 */
public class CoreLoadTestPythonProcessor extends Service<Process> {

    private String domainFile;
    private String storyFile;

    /**
     * Name des Core-Models
     */
    private String coreModel;
    /**
     * Name des NLU-Models
     */
    private String nluModel;

    /**
     * Gewählter Modus
     */
    private boolean trainOnlineMode;


    /**
     * Konstruktor für das Laden des Test-Python-Skripts
     * @param coreModel Name des Core-Models
     * @param nluModel Name des NLU-Models
     */
    public CoreLoadTestPythonProcessor(String coreModel, String nluModel){
        this.coreModel = coreModel;
        this.nluModel = nluModel;
        this.trainOnlineMode = false;
    }

    /**
     * Konstruktor für das Laden des Python-Skripts für interaktives Training
     * @param storyFile Name der Story-Datei
     * @param domainFile Name der Domain-Datei
     * @param nluModel Name des NLU-Models
     */
    public CoreLoadTestPythonProcessor(String storyFile,String domainFile,String nluModel){
        this.storyFile = storyFile;
        this.domainFile = domainFile;
        this.nluModel = nluModel;
        this.trainOnlineMode = true;
    }

    /**
     *  Erzeugt Task der das Python-Skript ausführt
     * @return Task der das Python-Skript ausführt
     */
    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            /**
             * Je nach Modus wird ein anderer Kommandozeilenbefehl befüllt und ausgeführt.
             * Danach wird der Output des Prozesses gelesen bis "Bot geladen" gelesen wird
             * @return gestarteten Python-Prozess
             */
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
