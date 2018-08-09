package main.application;



import com.gluonhq.ignite.guice.GuiceContext;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Arrays;

/**
 * Startklasse von RasaStackMate
 *
 * @author Christian Hoffmann
 * @version 1.0
 */
public class GUI extends Application {

    //Kontext für DI-Framework
    private GuiceContext guiceContext;

    //Pfad des ausgewählten Projektordners
    private static String workspace;
    //umbenennung der platzhalter in beispielsätzen
    //entitys exisiteren nicht mehr? highlighting in beispielsätzen? hinweis wird verwendet ??

    /**
     * Startet die Anwendung und initialisiert den Kontext für den Guice-Injector.
     * Wechseln auf die Start-Ansicht
     *
     * @param primaryStage Das prime Anwendungsfenster
     */
    @Override
    public void start(Stage primaryStage){

        guiceContext = new GuiceContext(this, ()-> Arrays.asList(new GuiConfig()));
        guiceContext.init();

        final WindowManager stageController = guiceContext.getInstance(WindowManager.class);
        stageController.switchScene(WindowManager.SCENES.START_SCENE);
    }

    /**
     * Main-Methode
     * ruft JavaFX-Methode launch auf, die die start-Methode aufruft
     * @param args
     */
    public static void main(String []args){
        launch(args);
    }


    /**
     * Gibt den aktuell gesetzten Workspace zurück
     * @return String-Repräsentation des Workspace-Pfades
     */
    public static String getWorkSpace(){
        return workspace;
    }

    /**
     * Setzt den aktuellen Pfad des Workspaces auf den übergebenen Pfad
     * @param workspaceName String-Repräsentation des zu setzenden Workspace-Pfades
     */
    public static void setWorkSpace(String workspaceName){
        workspace=workspaceName;
    }
}
