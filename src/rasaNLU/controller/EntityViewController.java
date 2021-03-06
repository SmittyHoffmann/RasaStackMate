package rasaNLU.controller;

import main.util.NameChangeDialog;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import rasaNLU.model.entity.EntityManager;

import javax.inject.Inject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EntityViewController implements Initializable {



            @FXML
            ChoiceBox<String> entityChoiceBox;

            @FXML
            ListView<String> valueListView;

            @FXML
            Button addValueButton;

            @FXML Button deleteValueButton;

            @FXML Button addEntityButton;

            @FXML Button deleteEntityButton;

            @FXML
            TextField entityNameTextField;

            @FXML TextField valueTextField;

            @Inject
            EntityManager entityManager;

            @FXML
            Button editEntityNameButton;

            @Inject
            FXMLLoader fxmlLoader;

            private String currentEntityName = "";




            @Override
            public void initialize(URL location, ResourceBundle resources){



                entityChoiceBox.setItems(entityManager.getEntityNames());
                entityChoiceBox.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) ->{
                    if(newValue!= null) {
                        valueListView.setItems(entityManager.getValuesToEntity(newValue));

                    }
                    currentEntityName = newValue;
                });


                addEntityButton.setOnAction(event -> {
                    if(!entityNameTextField.getText().isEmpty()){
                        entityManager.addEntity(entityNameTextField.getText());
                        entityChoiceBox.getSelectionModel().select(entityNameTextField.getText()
                        );
                        entityNameTextField.clear();
                    }
                });


                deleteEntityButton.setOnAction(event -> {

                    if(!currentEntityName.isEmpty()){
                        entityManager.deleteEntity(currentEntityName);
                        currentEntityName = "";
                        valueListView.setItems(null);
                    }
                });

                addValueButton.setOnAction(event -> {
                    String exampleText = valueTextField.getText();
                    if(!exampleText.isEmpty() && !currentEntityName.isEmpty()){
                        entityManager.addEntityValue(currentEntityName,exampleText);
                        valueTextField.clear();
                    }

                });

                deleteValueButton.setOnAction(event -> {
                    int selectedIndex = valueListView.getSelectionModel().getSelectedIndex();
                    if(selectedIndex != -1){

                        int newIndex = (selectedIndex == valueListView.getItems().size() -1 ? selectedIndex -1 : selectedIndex);
                        valueListView.getItems().remove(selectedIndex);
                        valueListView.getSelectionModel().select(newIndex);
                    }

                });

                valueListView.setCellFactory(TextFieldListCell.forListView());
                valueListView.setEditable(true);


                editEntityNameButton.setOnAction(event -> {
                    Dialog<String> dialog = new NameChangeDialog(currentEntityName);
                    Optional<String> result = dialog.showAndWait();

                    result.ifPresent(changedName -> {
                        if(!changedName.equals(currentEntityName)){
                            entityManager.changeEntityName(currentEntityName,changedName);
                            entityChoiceBox.getSelectionModel().select(changedName);
                        }
                    });

                });




            }



        }



