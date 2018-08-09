package rasaCore.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import rasaCore.model.domain.DomainManager;
import rasaNLU.model.intent.IntentManager;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller für Domain-Intent-View
 */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initComponents();

        setHandler();
    }

    /**
     * Fügt GUI-Elementen Event-Handler hinzu
     */
    private void setHandler() {

        takeOverButton.setOnAction(event-> {
            domainManager.setIntents(intentManager.getIntentNames());
        });

        addIntentButton.setOnAction(event->{
            String intentName = intentTextField.getText();
            if(!intentName.isEmpty()){
                domainManager.addIntent(intentName);
            }
        });

        deleteIntentButton.setOnAction(event-> {
            String selectedIntent = this.intentListView.getSelectionModel().getSelectedItem();
            if(selectedIntent!= null){
                domainManager.deleteEntity(selectedIntent);
            }
        });
    }

    /**
     * Initialisiert und Befüllt GUI-Elemente
     */
    private void initComponents() {
        intentListView.setItems((ObservableList<String>) domainManager.getIntents());

        intentListView.setEditable(true);
        intentListView.setCellFactory(TextFieldListCell.forListView());
    }
}
