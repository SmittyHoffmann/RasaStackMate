package controller.rasaNLU.develop;


import controller.util.NameChangeDialog;
import controller.util.TextFieldListCellWithProvider;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.rasaNLU.entity.EntityManager;
import model.rasaNLU.intent.IntentManager;
import org.controlsfx.control.textfield.TextFields;

import javax.inject.Inject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class IntentViewController implements Initializable {

    @FXML ChoiceBox <String> intentChoiceBox;

    @FXML
    ListView<String> exampleListView;

    @FXML
    Button addExampleButton;

    @FXML Button editIntentNameButton;
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

        exampleListView.setEditable(true);
        exampleListView.setCellFactory(param -> new TextFieldListCellWithProvider(this.entityManagerImpl));

        addIntentButton.setOnAction(event -> {
                    if (!intentNameTextField.getText().isEmpty()) {
                        intentManagerImpl.addIntent(intentNameTextField.getText());
                        intentChoiceBox.getSelectionModel().select(intentNameTextField.getText()
                        );
                        intentNameTextField.clear();
                    }
                });

        exampleTextField.textProperty().addListener((observable,oldValue,newValue) -> {
            String currentText = newValue;

            if(!currentText.isEmpty()) {

                if(currentText.equals("@")){
                    provider.clearSuggestions();
                    provider.addPossibleSuggestions(entityManagerImpl.getEntityPlaceHolders());
                }

                if(currentText.length() >= 2 && currentText.substring(currentText.length()-2).equals(" @")){
                    provider.clearSuggestions();
                    suggestions.clear();

                    for (String value : entityManagerImpl.getEntityPlaceHolders()) {

                        suggestions.add(currentText.substring(0,currentText.length()-1) + value);
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



        editIntentNameButton.setOnAction(event-> {
            if(!currentIntentName.isEmpty()){
                Dialog<String> dialog = new NameChangeDialog(currentIntentName);
                Optional<String> result = dialog.showAndWait();

                result.ifPresent(changedName ->{
                    if(!changedName.equals(currentIntentName)){
                        intentManagerImpl.changeIntentName(currentIntentName,changedName);
                        intentChoiceBox.getSelectionModel().select(changedName);
                    }
                });
            }
        });






    }



}
