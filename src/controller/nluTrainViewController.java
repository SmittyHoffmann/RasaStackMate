package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.NLUPythonProcessor;
import model.RasaFileManager;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class nluTrainViewController implements Initializable {


    @FXML
    ChoiceBox<String> trainFileChoiceBox;
    @FXML TextField modelNameTextField;
    @FXML Button trainButton;

    @Inject
    RasaFileManager fileManager;

    @Inject
    NLUPythonProcessor processor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        trainFileChoiceBox.setItems(fileManager.getNLUTrainFiles());

        trainButton.setOnAction(event -> {
            String modelName = this.modelNameTextField.getText();
            String fileName = trainFileChoiceBox.getSelectionModel().getSelectedItem();
            if(!modelName.isEmpty() && !fileName.isEmpty()){
                this.processor.trainNLU(fileName,modelName);
            }
        });


    }
}
