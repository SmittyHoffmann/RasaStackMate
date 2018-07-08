package controller.rasaCore.domain;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import model.fileHandling.RasaFileManager;
import model.rasaCore.domain.DomainGenerator;
import model.rasaCore.domain.DomainManager;
import model.rasaCore.domain.template.TemplateManager;
import model.rasaCore.domain.slot.SlotManager;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DomainViewController implements Initializable {


    @FXML
    Button generateButton;
    @FXML
    Button importButton;
    @FXML
    ChoiceBox<String> domainFileChoiceBox;
    @FXML
    Tab customActionTab;
    @FXML
    Tab templateTab;
    @FXML
    Tab slotTab;
    @FXML
    Tab entityTab;
    @FXML
    Tab intentTab;
    @FXML
    TabPane TabPane;

    @Inject
    RasaFileManager fileManager;

    @Inject
    DomainGenerator generator;

    @Inject
    DomainManager domainManager;

    @Inject
    SlotManager slotManager;

    @Inject
    TemplateManager templateManager;

    @Inject
    FXMLLoader fxmlLoader;


    Parent intentView;
    Parent entityView;
    Parent slotView;
    Parent templateView;
    Parent customActionView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        setIntentTabContent();
        setEntityTabContent();
        setSlotTabContent();
        setTemplateTabContent();
        setCustomActionTabContent();
        domainFileChoiceBox.setItems(fileManager.getCoreDomainFiles());

        generateButton.setOnAction(event->{
            String fileName;
            TextInputDialog dialog = new TextInputDialog("Dateiname");
            dialog.setContentText("Bitte geben sie den gewünschten Dateinamen an");

            Optional<String> result = dialog.showAndWait();
            if(result.isPresent()){
                fileName = result.get();
                Map<String, List<String>> intents = generator.generateIntents(domainManager);
                Map<String, List<String>> entities = generator.generateEntities(domainManager);
                Map<String, Map<String, Map<String,Object>>> slots = generator.generateSlots(slotManager);
                List<String> templates = generator.generateTemplates(templateManager);
                Map<String, List<String>> actions = generator.generateActions(domainManager, templateManager);


                fileManager.writeDomainFile(fileName,intents,entities,slots,templates,actions);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Datei schreiben erfolgreich");
                alert.setHeaderText(null);
                alert.setContentText("Das Generieren der Trainingsdatei war erfolgreich!");
                alert.showAndWait();
            }
        });

        importButton.setOnAction(event -> {
            String fileName = domainFileChoiceBox.getSelectionModel().getSelectedItem();

            generator.fillManagers(fileName,domainManager,slotManager,templateManager);
        });

    }






    private void setCustomActionTabContent() {
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../../../view/domainCustomActionView.fxml"));
        try {
            customActionView = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        customActionTab.setContent(customActionView);
    }

    private void setTemplateTabContent() {
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../../../view/domainTemplateView.fxml"));
        try {
            templateView = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        templateTab.setContent(templateView);
    }


    private void setSlotTabContent() {
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../../../view/domainSlotView.fxml"));
        try {
            slotView = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        slotTab.setContent(slotView);
    }

    private void setEntityTabContent() {
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../../../view/domainEntityView.fxml"));
        try {
            entityView = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        entityTab.setContent(entityView);
    }

    private void setIntentTabContent() {
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../../../view/domainIntentView.fxml"));
        try {
            intentView = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        intentTab.setContent(intentView);
    }
}

