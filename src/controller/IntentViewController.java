package controller;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.EntityManager;
import model.IntentManager;
import org.controlsfx.control.textfield.TextFields;

import javax.inject.Inject;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
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

    @FXML TextField exampleTextField;

    @Inject
    IntentManager intentManagerImpl;

    @Inject
    EntityManager entityManagerImpl;

    @Inject
    FXMLLoader fxmlLoader;

    private String currentIntentName = "";

    ObservableList<String> suggestions = FXCollections.observableArrayList();
    SuggestionProvider<String> provider;

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

        exampleTextField.textProperty().addListener((observable,oldValue,newValue) -> {
            String currentText = newValue;
            provider.clearSuggestions();
            if(!currentText.isEmpty()) {

                if(currentText.substring(currentText.length()-2,currentText.length()-1).equals(" @")){



                    for (String value : entityManagerImpl.getEntityPlaceHolders()) {

                        suggestions.add(currentText.substring(0,currentText.length()-3) + value);
                    }

                    provider.addPossibleSuggestions(suggestions);
                }
            }
        });
        provider = SuggestionProvider.create(suggestions);
        TextFields.bindAutoCompletion(exampleTextField, provider);


        deleteIntentButton.setOnAction(event -> {

            if(!currentIntentName.isEmpty()){
                intentManagerImpl.deleteIntent(currentIntentName);
                currentIntentName = "";
                exampleListView.setItems(null);
            }
        });

        addExampleButton.setOnAction(event -> {
            String exampleText = exampleTextField.getText();
            if(!exampleText.isEmpty() && !currentIntentName.isEmpty()){
                intentManagerImpl.addIntentExample(currentIntentName,exampleText);
                exampleTextField.clear();
            }

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
