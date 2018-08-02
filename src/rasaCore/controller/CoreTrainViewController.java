package rasaCore.controller;

import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.fileHandling.RasaFileManager;
import main.pythonProcessing.CoreTrainPythonProcessor;
import main.util.ProcessEndedDialog;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CoreTrainViewController implements Initializable {


    @FXML
    ChoiceBox<String> storyFileChoiceBox;
    @FXML
    TextField modelNameTextField;
    @FXML
    ChoiceBox<String> domainFileChoiceBox;
    @FXML
    ChoiceBox<String> nluModelChoiceBox;
    @FXML
    Button trainButton;
    @Inject
    RasaFileManager fileManager;


    CoreTrainPythonProcessor processor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        storyFileChoiceBox.setItems(fileManager.getCoreStoryFiles());
        domainFileChoiceBox.setItems(fileManager.getCoreDomainFiles());
        nluModelChoiceBox.setItems(fileManager.getNLUModels());



        trainButton.setOnAction(event -> {
            String coreModelName = this.modelNameTextField.getText();
            String storyFileName = storyFileChoiceBox.getSelectionModel().getSelectedItem();
            String domainFileName = domainFileChoiceBox.getSelectionModel().getSelectedItem();
            String nluModelName = nluModelChoiceBox.getSelectionModel().getSelectedItem();
            if(!coreModelName.isEmpty() && !storyFileName.isEmpty() && !domainFileName.isEmpty() && !nluModelName.isEmpty()){
                this.processor = new CoreTrainPythonProcessor(storyFileName,coreModelName,domainFileName,nluModelName);
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


            this.processor.setOnFailed(we -> {
                System.out.println(we.getSource().getException().getMessage());
            });


        });





    }
}
