package rasaCore.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import rasaCore.model.DomainManager;
import rasaNLU.model.entity.EntityManager;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class DomainEntityController implements Initializable {


    @FXML TextField entityTextField;
    @FXML
    Button addEntityButton;
    @FXML
    Button deleteEntityButton;
    @FXML
    Button takeOverButton;
    @FXML
    ListView <String>entityListView;


    @Inject
    EntityManager entityManager;

    @Inject
    DomainManager domainManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        entityListView.setItems((ObservableList<String>) domainManager.getEntities());

        entityListView.setEditable(true);
        entityListView.setCellFactory(TextFieldListCell.forListView());

        takeOverButton.setOnAction(event-> {
            domainManager.setEntities(entityManager.getEntityNames());
        });

        addEntityButton.setOnAction(event -> {
            String entityName = this.entityTextField.getText();
            if(!entityName.isEmpty()){
                this.domainManager.addEntity(entityName);
            }
        });

        deleteEntityButton.setOnAction(event -> {
            String selectedEntity = this.entityListView.getSelectionModel().getSelectedItem();
            if(selectedEntity != null){
                this.domainManager.deleteEntity(selectedEntity);
            }
        });
    }
}
