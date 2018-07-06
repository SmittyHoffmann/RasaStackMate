package controller.rasaCore.domain;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import model.rasaCore.DomainManager;
import model.rasaNLU.intent.IntentManager;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class DomainIntentController implements Initializable {


    @FXML
    TextField intentTextField;
    @FXML
    ListView<String> intentListView;
    @FXML
    Button takeOverButton;
    @FXML
    Button deleteIntentButton;
    @FXML
    Button addIntentButton;

    @Inject
    DomainManager domainManager;

    @Inject
    IntentManager intentManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        intentListView.setItems(domainManager.getIntents());

        intentListView.setEditable(true);
        intentListView.setCellFactory(TextFieldListCell.forListView());

        takeOverButton.setOnAction(event-> {
            domainManager.setIntents(intentManager.getIntentNames());
        });

        addIntentButton.setOnAction(event->{
            String intentName = intentTextField.getText();
            if(!intentName.isEmpty()){
                domainManager.addEntity(intentName);
            }
        });

        deleteIntentButton.setOnAction(event-> {
            String selectedIntent = this.intentListView.getSelectionModel().getSelectedItem();
            if(selectedIntent!= null){
                domainManager.deleteEntity(selectedIntent);
            }
        });
    }
}
