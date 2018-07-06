package controller;

import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.pythonProcessing.NLUCOMMANDS;
import model.pythonProcessing.NLUTrainPythonProcessor;
import model.fileHandling.RasaFileManager;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class NluTrainViewController implements Initializable {


    @FXML
    ChoiceBox<String> trainFileChoiceBox;
    @FXML TextField modelNameTextField;
    @FXML Button trainButton;

    @Inject
    RasaFileManager fileManager;

    NLUTrainPythonProcessor processor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        trainFileChoiceBox.setItems(fileManager.getNLUTrainFiles());

        trainButton.setOnAction(event -> {
            String modelName = this.modelNameTextField.getText();
            String fileName = trainFileChoiceBox.getSelectionModel().getSelectedItem();
            if(!modelName.isEmpty() && !fileName.isEmpty()){
                this.processor = new NLUTrainPythonProcessor(fileName,modelName);
                this.processor.start();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Training gestartet!");
                alert.setHeaderText(null);
                alert.setContentText("Das Training wurde gestartet");
                alert.showAndWait();
            }

            this.processor.setOnSucceeded((WorkerStateEvent we) -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Training erfolgreich!");
                alert.setHeaderText(null);
                alert.setContentText("Das Training war erfolgreich!");
                alert.showAndWait();
                this.processor.reset();
            });
        });


    }
}
