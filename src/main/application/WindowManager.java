package main.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Klasse zum Wechseln der Szenen zwischen Start- und Haupt-Ansicht
 */
class WindowManager {

    //Anwendungsfenster
    static Stage stage;
    //Zum Laden der FXML-Dateien
    @Inject
    private FXMLLoader fxmlLoader;

    /**
     * Enum für die verschiedenen Szenen/View-Dateien der  Start- und Haupt-Ansicht
     */
    public enum SCENES {

        START_SCENE("startView.fxml"),
        MAIN_SCENE("mainView.fxml");

        /**
         * Name der FXML-Datei
         */
        private String sceneName;

        /**
         * Weist String Pfad Enum zu
         *
         * @param scenePath Pfad der FXML-Datei
         */
        SCENES(String scenePath) {
            this.sceneName = scenePath;
        }

        /**
         * gibt zugehörigen Pfad-String zurück
         */
        public String getSceneName() {
            return sceneName;
        }
    }

    /**
     * Wechselt zur der übergebenen Ansicht
     * @param scene Ansicht zu der gewechselt werden soll
     */
    void switchScene(SCENES scene) {
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource(scene.getSceneName()));

        Parent root = null;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Falscher Pfad" + e.getMessage());
        }
        if (null == root) {
            throw new IllegalStateException("Gab nen Fehler beim Controller Initialisieren");
        }
        fxmlLoader.getController();
        if (stage != null) {
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
