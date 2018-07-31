package main.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;

public class WindowManager {

    public static Stage stage;
    @Inject private FXMLLoader fxmlLoader;

    public enum SCENES{
        START_SCENE("startView.fxml"),
        MAIN_SCENE("mainView.fxml");

        private String sceneName;

        SCENES(String scenePath){
            this.sceneName = scenePath;
        }

        public String getSceneName(){
            return sceneName;
        }
    }


    public void switchScene(SCENES scene){
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource(scene.getSceneName()));

        Parent root = null;

        try{
            root = fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Falscher Pfad" + e.getMessage());
        }
        if(null ==root){
            throw new IllegalStateException("Gab nen Fehler beim Controller Initialisieren");
        }
        fxmlLoader.getController();
        if(stage!=null){
            stage.close();
        }
        stage = new Stage();
        root.getStylesheets().add("main/application/style.css");
        stage.setMinHeight(800);
        stage.setMinWidth(1200);
        stage.setTitle("RasaStackMate");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();


    }

}
