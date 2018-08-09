package main.application;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import rasaNLU.model.intent.IntentManager;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controller für die View
 */
public class MainViewController implements Initializable {
    //Hauptfenster
    @FXML BorderPane mainPane;
    //menü-Container
    @FXML VBox menuBox;
    //Button zum Ausklappen des NLU-Menüs
    @FXML Button firstMenu;
    //Container für NLU-Menü
    @FXML VBox firstSubVBox;
    //Container für UnterMenü-Punkte des NLU-Menüs
    @FXML VBox firstSubMenuList;

    //Container für Untermenü-Punkte des Core-Menüs
    @FXML VBox secondSubMenuList;
    //Button zum Ausklappen des Core-Menüs
    @FXML Button secondMenu;
    // Container für 2. Core-Menü
    @FXML VBox secondSubVBox;

    // Button zum öffnen der Core-Test-View
    @FXML Button coreTest;
    //Button zum öffnen der Core-Train-View
    @FXML Button coreTrain;
    //Button zum Öffnen des Story-Editors
    @FXML Button storyEditor;
    //Button zum öffnen Der DomainView
    @FXML Button domainEdit;

    //Button zum Öffnen der NLU-Bearbeitungs-Ansicht
    @FXML Button nluEdit;
    //Button zum Öffnen NLU-Train-View
    @FXML Button nluTrain;
    //Button zum Öffnen der NLU-Test-View
    @FXML Button nluTest;


    //Helfer zum entfernen und Hinzufügen der Untermenüpunkte
    private Map<VBox,VBox> map = new HashMap<>();


    //Container für den Hauptteil der Ansicht
    private Parent content;


    //JavaFX-Klasse zum Laden von FXML-Dateien
    //Wird injected damit sich das DI-Framework einklinken kann
    @Inject
    private FXMLLoader fxmlLoader;


    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addMenusToMap();
        setComponentsSize();
        setContentToNLU();

        nluEdit.setOnAction(event -> {
            setContentToNLU();
        });

        nluTrain.setOnAction(event -> {
            setContentToTrainNLU();
        });

        nluTest.setOnAction(event-> {
            setContentToTestNLU();
        });

        domainEdit.setOnAction(event-> {
            setContentToDomain();
        });

        storyEditor.setOnAction(event-> {
            setContentToStoryEditor();
        });

        coreTrain.setOnAction(event -> {
            setContentToTrainCore();
        });

        coreTest.setOnAction(event -> {
            setContentToTestCore();
        });



        firstMenu.setOnAction(event -> {
            toolsSlider(firstSubVBox, firstSubMenuList);
            removeOtherMenus(firstSubVBox);
        });

        secondMenu.setOnAction(event-> {
            toolsSlider(secondSubVBox, secondSubMenuList);
            removeOtherMenus(secondSubVBox);
        });


    }

    /**
     * Setzt die Ansicht auf Core-Training
     */
    private void setContentToTrainCore() {
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../../rasaCore/view/coreTrainView.fxml"));
        try {
            content = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainPane.setCenter(content);
    }
    /**
     * Setzt die Ansicht auf Core-Testen
     */
    private void setContentToTestCore() {
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../../rasaCore/view/coreTestView.fxml"));
        try {
            content = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainPane.setCenter(content);
    }

    /**
     * Setzt die Ansicht auf den Story-Editor
     */
    private void setContentToStoryEditor() {
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../../rasaCore/view/storyView.fxml"));
        try {
            content = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainPane.setCenter(content);
    }
    /**
     * Setzt die Ansicht auf Domain-Bearbeiten
     */
    private void setContentToDomain() {
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../../rasaCore/view/domainView.fxml"));
        try {
            content = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainPane.setCenter(content);
    }

    /**
     * Setzt die Ansicht auf Nlu-Test
     */
    private void setContentToTestNLU() {
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../../rasaNLU/view/nluTestView.fxml"));
        try {
            content = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainPane.setCenter(content);
    }


    /**
     * Setzt die Ansicht auf NLU-Daten bearbeiten
     */
    public void setContentToNLU(){
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../../rasaNLU/view/nluView.fxml"));
        try {
            content = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainPane.setCenter(content);
    }


    /**
     * Setzt die Ansicht auf NLU-Testen
     */
    public void setContentToTrainNLU(){
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../../rasaNLU/view/nluTrainView.fxml"));
        try {
            content = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainPane.setCenter(content);
    }


    /**
     * Setzt die Größe des Menüs
     */
    private void setComponentsSize(){

        menuBox.setPrefWidth(200);
        menuBox.setPrefHeight(800);
    }
    /**
     * WEist die Menü-Buttons dem Menü hinzu, versteckt sie aber
     */
    private void addMenusToMap(){
        map.put(firstSubVBox,firstSubMenuList);
        map.put(secondSubVBox,secondSubMenuList);

        for(Map.Entry<VBox,VBox> entry : map.entrySet()){
            entry.getKey().getChildren().remove(entry.getValue());
        }
    }

    /**
     * Methode zum ein und Ausblenden der Menü-Punkte
     * @param menu Menü-Container
     * @param subMenu Container für Unter-Menü-Punkte
     */
    public void toolsSlider(VBox menu, VBox subMenu){
        if (menu.getChildren().contains(subMenu)){
            final FadeTransition transition = new FadeTransition((Duration.millis(500)),menu);
            transition.setFromValue(0.5);
            transition.setToValue(1);
            transition.setInterpolator(Interpolator.EASE_IN);
            menu.getChildren().remove(subMenu);
            transition.play();
        }else{
            final FadeTransition transition = new FadeTransition((Duration.millis(500)),menu);
            transition.setFromValue(0.5);
            transition.setToValue(1);
            transition.setInterpolator(Interpolator.EASE_IN);
            menu.getChildren().add(subMenu);
            transition.play();
        }
    }

    /**
     * Entfernt die übergebenen Menü-Punkte
     * @param menu Menü das nicht mehr angezeigt werden soll
     */
    public void removeOtherMenus(VBox menu){
        for(Map.Entry<VBox,VBox> entry : map.entrySet()){
            if(!entry.getKey().equals(menu)){
                entry.getKey().getChildren().remove(entry.getValue());
            }
        }
    }
}

