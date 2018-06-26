package view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;

public class WindowManager {

    public static Stage stage;
    @Inject private FXMLLoader fxmlLoader;

    public enum SCENES{
        START_SCENE("/view/mainView.fxml");

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

        stage = new Stage();
        root.getStylesheets().add("/view/style.css");
        stage.setMinHeight(400);
        stage.setMinWidth(800);
        stage.setTitle("RasaStackMate");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();


    }

}
