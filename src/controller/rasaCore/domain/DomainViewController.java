package controller.rasaCore.domain;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.fileHandling.RasaFileManager;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

