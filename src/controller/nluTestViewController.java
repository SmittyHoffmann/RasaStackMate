package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.fileHandling.RasaFileManager;
import model.pythonProcessing.NLULoadTestPythonProcessor;
import model.pythonProcessing.NLUSendTestPythonProcessor;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class nluTestViewController implements Initializable {


    @FXML
    ChoiceBox<String> modelChoiceBox;

    @FXML
    TextField testTextfield;

    @FXML
    Button loadModelButton;

    @FXML
    Button testSentenceButton;

    @FXML
    Label resultLabel;

    @Inject
    RasaFileManager fileManager;

    Process currentProcess;
    NLULoadTestPythonProcessor loadProcessor;
    NLUSendTestPythonProcessor sendProcessor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.modelChoiceBox.setItems(fileManager.getNLUModels());

        this.loadModelButton.setOnAction(e -> {
            String modelName = this.modelChoiceBox.getSelectionModel().getSelectedItem();
            if (modelName != null) {
                if (this.currentProcess != null) {
                    this.currentProcess.destroy();
                    this.currentProcess = null;
                }
                this.loadProcessor = new NLULoadTestPythonProcessor(modelName);
                this.loadProcessor.start();
            }


            this.loadProcessor.setOnSucceeded(event -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Model geladen");
                alert.setHeaderText(null);
                alert.setContentText("Das Model ist geladen");
                alert.showAndWait();
                this.currentProcess = this.loadProcessor.getValue();
                this.loadProcessor.reset();
            });
        });

        this.testSentenceButton.setOnAction(e -> {
            if (this.currentProcess != null && !this.testTextfield.getText().isEmpty()) {
                this.sendProcessor = new NLUSendTestPythonProcessor(this.currentProcess, this.testTextfield.getText());
                this.sendProcessor.start();



            this.sendProcessor.setOnSucceeded(event -> {
                String result = this.sendProcessor.getValue();
                this.sendProcessor.reset();
                this.resultLabel.setText(result);
            });
            }
        });
    }
}
