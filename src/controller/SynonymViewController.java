package controller;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.SynonymManager;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class SynonymViewController implements Initializable {



        @FXML
        ChoiceBox<String> synonymChoiceBox;

        @FXML
        ListView<String> valueListView;

        @FXML
        Button addValueButton;

        @FXML Button deleteValueButton;

        @FXML Button addSynonymButton;

        @FXML Button deleteSynonymButton;

        @FXML
        TextField synonymNameTextField;

        @FXML TextField valueTextField;

        @Inject
        SynonymManager synonymManagerImpl;

        @Inject
        FXMLLoader fxmlLoader;

        private String currentSynonymName = "";

        @Override
        public void initialize(URL location, ResourceBundle resources){



            synonymChoiceBox.setItems(synonymManagerImpl.getSynonymNames());
            synonymChoiceBox.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) ->{
                if(newValue!= null) {
                    valueListView.setItems(synonymManagerImpl.getValuesToSynonym(newValue));

                }
                currentSynonymName = newValue;
            });


            addSynonymButton.setOnAction(event -> {
                if(!synonymNameTextField.getText().isEmpty()){
                    synonymManagerImpl.addSynonym(synonymNameTextField.getText());
                    synonymChoiceBox.getSelectionModel().select(synonymNameTextField.getText()
                    );
                    synonymNameTextField.clear();
                }
            });


            deleteSynonymButton.setOnAction(event -> {

                if(!currentSynonymName.isEmpty()){
                    synonymManagerImpl.deleteSynonym(currentSynonymName);
                    currentSynonymName = "";
                    valueListView.setItems(null);
                }
            });

            addValueButton.setOnAction(event -> {
                String exampleText = valueTextField.getText();
                if(!exampleText.isEmpty() && !currentSynonymName.isEmpty()){
                    synonymManagerImpl.addSynonymValue(currentSynonymName,exampleText);
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










        }



    }



