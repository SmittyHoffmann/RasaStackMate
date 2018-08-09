package rasaCore.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import rasaCore.model.domain.DomainManager;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller für die Domain-CustomAction-View
 */
public class DomainCustomActionController implements Initializable {


    @FXML TextField customActionTextField;
    @FXML
    Button addCustomActionButton;
    @FXML Button deleteCustomActionButton;
    @FXML ListView<String> actionListView;

    @Inject
    DomainManager manager;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initComponents();
        setHandlers();




    }

    /**
     * Initialisiert GUI-Elemente und befüllt sie
     */
    private void initComponents(){

        this.actionListView.setItems((ObservableList<String>) manager.getCustomActions());
        actionListView.setEditable(true);
        actionListView.setCellFactory(TextFieldListCell.forListView());
    }

    /**
     * Fügt GUI-Elementen Eventhandler hinzu
     */
    private void setHandlers(){
        addCustomActionButton.setOnAction(event->{
            String customAction = customActionTextField.getText();
            if(!customAction.isEmpty()){
                manager.addCustomAction(customAction);
            }
        });
        deleteCustomActionButton.setOnAction(event->{
            String selectedAction = actionListView.getSelectionModel().getSelectedItem();
            if(selectedAction != null){
                manager.deleteCustomAction(selectedAction);
            }
        });
    }


}
