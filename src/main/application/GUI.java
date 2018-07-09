package main.application;

import com.gluonhq.ignite.guice.GuiceContext;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Arrays;

public class GUI extends Application {
    private GuiceContext guiceContext;

    private static String workspace;
    //umbenennung der platzhalter in beispielsätzen
    //entitys exisiteren nicht mehr? highlighting in beispielsätzen? hinweis wird verwendet ??

    @Override
    public void start(Stage primaryStage) throws Exception {
        guiceContext = new GuiceContext(this, ()-> Arrays.asList(new GuiConfig()));
        guiceContext.init();

        final WindowManager stageController = guiceContext.getInstance(WindowManager.class);
        stageController.switchScene(WindowManager.SCENES.START_SCENE);
    }

    public void run(String []args){
        launch(args);
    }

    public static void main(String []args){
        launch(args);
    }

    public static String getWorkSpace(){
        return workspace;
    }

    public static void setWorkSpace(String workspaceName){
        workspace=workspaceName;
    }
}
