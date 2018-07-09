package main.application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class StartViewController implements Initializable {
    @FXML
    private Label directoryLabel;

    @FXML
    private Button directoryButton;

    @FXML private Button startButton;

    @Inject
    WindowManager windowManager;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        directoryButton.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File choosedDirectory = directoryChooser.showDialog(WindowManager.stage);
            if(choosedDirectory == null){
                directoryLabel.setText("Kein Ordner ausgewählt");
            }
            else{
                String path = choosedDirectory.getAbsolutePath();
                directoryLabel.setText(path);
                GUI.setWorkSpace(path);
                startButton.setDisable(false);
            }
        });


        startButton.setDisable(true);
        startButton.setOnAction(event -> {
            windowManager.switchScene(WindowManager.SCENES.MAIN_SCENE);
        });
    }
}
