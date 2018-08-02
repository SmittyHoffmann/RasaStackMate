package rasaNLU.controller;

import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.pythonProcessing.NLUTrainPythonProcessor;
import main.fileHandling.RasaFileManager;
import main.util.ProcessEndedDialog;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
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
                List<String> result = processor.getValue();
                Dialog dialog;
                if(result.get(result.size()-1).equals("0")){
                    dialog = new ProcessEndedDialog(result,true);
                }else{
                    dialog = new ProcessEndedDialog(result,false);
                }

                this.processor.reset();
                dialog.showAndWait();
            });
        });


    }
}
