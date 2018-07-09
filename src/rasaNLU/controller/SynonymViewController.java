package rasaNLU.controller;

import main.util.NameChangeDialog;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import rasaNLU.model.entity.EntityManager;
import rasaNLU.model.synonym.SynonymManager;
import org.controlsfx.control.textfield.TextFields;

import javax.inject.Inject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SynonymViewController implements Initializable {


        @FXML
        Button editSynonymNameButton;
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
        EntityManager entityManager;

        @Inject
        FXMLLoader fxmlLoader;

        private String currentSynonymName = "";

        private ObservableList<String> suggestions = FXCollections.observableArrayList();

        private SuggestionProvider<String> provider;



        @Override
        public void initialize(URL location, ResourceBundle resources){

            suggestions = entityManager.getAllEntityValues();

            suggestions.addListener((ListChangeListener<String>) event -> {
                    provider.clearSuggestions();
                    provider.addPossibleSuggestions(suggestions);
            });
            this.provider = SuggestionProvider.create(suggestions);
            TextFields.bindAutoCompletion(synonymNameTextField, provider);



            synonymChoiceBox.setItems(synonymManagerImpl.getSynonymNames());
            synonymChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
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
            valueListView.setEditable(true);
            valueListView.setCellFactory(TextFieldListCell.forListView());


            editSynonymNameButton.setOnAction(event-> {
                Dialog<String> dialog = new NameChangeDialog(currentSynonymName);
                ((NameChangeDialog) dialog).addSuggestionProvider(this.provider);
                Optional<String> result = dialog.showAndWait();


                result.ifPresent(changedName -> {
                   if(!changedName.equals(currentSynonymName)){
                       synonymManagerImpl.changeSynonymName(currentSynonymName,changedName);
                       synonymChoiceBox.getSelectionModel().select(changedName);
                   }
                });
            });






        }



    }



