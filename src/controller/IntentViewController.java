package controller;

import com.gluonhq.ignite.guice.GuiceContext;

import com.google.inject.Injector;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.IntentManager;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class IntentViewController implements Initializable {

    @FXML ChoiceBox <String> intentChoiceBox;

    @FXML
    ListView<String> exampleListView;

    @FXML
    Button addExampleButton;

    @FXML Button deleteExampleButton;

    @FXML Button addIntentButton;

    @FXML Button deleteIntentButton;

    @FXML
    TextField intentNameTextField;

    @FXML TextArea exampleTextArea;

    @Inject
    IntentManager intentManagerImpl;

    @Inject
    FXMLLoader fxmlLoader;

    private String currentIntentName;




    @Override
    public void initialize(URL location, ResourceBundle resources){



        intentChoiceBox.setItems(intentManagerImpl.getIntentNames());
        intentChoiceBox.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) ->{
                if(newValue!= null) {
                    exampleListView.setItems(intentManagerImpl.getExamplesToIntent(newValue));

                }
                currentIntentName = newValue;
                });


        addIntentButton.setOnAction(event -> {
            if(!intentNameTextField.getText().isEmpty()){
                intentManagerImpl.addIntent(intentNameTextField.getText());
                intentChoiceBox.getSelectionModel().select(intentNameTextField.getText()
                );
                intentNameTextField.clear();
            }
        });


        deleteIntentButton.setOnAction(event -> {

            if(!currentIntentName.isEmpty()){
                intentManagerImpl.deleteIntent(currentIntentName);
                currentIntentName = "";
            }
        });

        addExampleButton.setOnAction(event -> {
            String exampleText = exampleTextArea.getText();
            if(!exampleText.isEmpty() && !currentIntentName.isEmpty()){
                intentManagerImpl.addIntentExample(currentIntentName,exampleText);
            }
            exampleTextArea.clear();
        });

        deleteExampleButton.setOnAction(event -> {
            int selectedIndex = exampleListView.getSelectionModel().getSelectedIndex();
            if(selectedIndex != -1){

                int newIndex = (selectedIndex == exampleListView.getItems().size() -1 ? selectedIndex -1 : selectedIndex);
                exampleListView.getItems().remove(selectedIndex);
                exampleListView.getSelectionModel().select(newIndex);
            }

        });










    }



}
