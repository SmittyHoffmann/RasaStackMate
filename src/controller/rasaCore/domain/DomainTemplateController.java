package controller.rasaCore.domain;

import controller.util.NameChangeDialog;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.VBox;
import model.rasaCore.domain.template.TemplateManager;

import javax.inject.Inject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DomainTemplateController implements Initializable {


    @FXML
    ChoiceBox<String> templateChoiceBox;
    @FXML TextField templateNameTextField;
    @FXML Button addTemplateButton;
    @FXML Button deleteTemplateButton;
    @FXML Button editTemplateNameButton;
    @FXML VBox buttonBox;
    @FXML Button addExampleButton;
    @FXML TextField exampleTextField;
    @FXML Button deleteExampleButton;
    @FXML ListView<String> exampleListView;


    @Inject
    TemplateManager templateManager;

    String currentTemplateName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        templateChoiceBox.setItems(templateManager.getTemplateNames());

        templateChoiceBox.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) ->{
            if(newValue!= null) {
                exampleListView.setItems(templateManager.getUtterancesToTemplate(newValue));

            }
            currentTemplateName = newValue;
        });

        exampleListView.setEditable(true);
        exampleListView.setCellFactory(TextFieldListCell.forListView());

        addTemplateButton.setOnAction(event ->{
            String templateName = templateNameTextField.getText();
            if(!templateName.isEmpty()){
                this.templateManager.addTemplate(templateName);
                templateChoiceBox.getSelectionModel().select(templateName);
                templateNameTextField.clear();
            }
        });

        deleteTemplateButton.setOnAction(event -> {

            if(!currentTemplateName.isEmpty()){
                templateManager.deleteTemplate(currentTemplateName);
                currentTemplateName = "";
                exampleListView.setItems(null);
            }
        });

        addExampleButton.setOnAction(event -> {
            String utterance = exampleTextField.getText();
            if(!utterance.isEmpty() && !currentTemplateName.isEmpty()){
                templateManager.addUtterance(currentTemplateName,utterance);
            }
        });

        deleteExampleButton.setOnAction(event-> {
            int selectedIndex = exampleListView.getSelectionModel().getSelectedIndex();
            if(selectedIndex != -1){

                int newIndex = (selectedIndex == exampleListView.getItems().size() -1 ? selectedIndex -1 : selectedIndex);
                exampleListView.getItems().remove(selectedIndex);
                exampleListView.getSelectionModel().select(newIndex);
            }
        });

        editTemplateNameButton.setOnAction(event-> {
            if(!currentTemplateName.isEmpty()){
                Dialog<String> dialog = new NameChangeDialog(currentTemplateName);
                Optional<String> result = dialog.showAndWait();

                result.ifPresent(changedName ->{
                    if(!changedName.equals(currentTemplateName)){
                        templateManager.changeTemplateName(currentTemplateName,changedName);
                        templateChoiceBox.getSelectionModel().select(changedName);
                    }
                });
            }
        });


    }
}
