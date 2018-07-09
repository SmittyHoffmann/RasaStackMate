package rasaCore.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import rasaCore.model.DomainManager;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class DomainCustomActionController implements Initializable {


    @FXML TextField customActionTextField;
    @FXML
    Button addCustomActionButton;
    @FXML Button deleteCustomActionButton;
    @FXML ListView<String> actionListView;

    @Inject
    DomainManager manager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.actionListView.setItems((ObservableList<String>) manager.getCustomActions());
        actionListView.setEditable(true);
        actionListView.setCellFactory(TextFieldListCell.forListView());

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
