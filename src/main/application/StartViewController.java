package main.application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class StartViewController implements Initializable {
    @FXML
    private Text directoryText;

    @FXML
    private Button directoryButton;

    @FXML private Button startButton;

    @Inject
    WindowManager windowManager;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        String defaultFont = Font.getDefault().getName();

        directoryText.setFont(Font.font(defaultFont,FontWeight.BOLD,12));
        directoryText.setFill(Color.WHITE);

        directoryButton.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File choosedDirectory = directoryChooser.showDialog(WindowManager.stage);
            if(choosedDirectory == null){
                directoryText.setText("Kein Ordner ausgewählt");
            }
            else{
                String path = choosedDirectory.getAbsolutePath();
                directoryText.setText(path);
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
