package controller.rasaNLU.develop;

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
import model.rasaNLU.intent.IntentManager;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {


    @FXML Button storyEditor;
    @FXML Button domainEdit;
    @FXML Button feedBack;
    @FXML VBox secondSubMenuList;
    @FXML Button secondMenu;
    @FXML VBox secondSubVBox;

    @FXML
    BorderPane mainPane;
    @FXML
    AnchorPane rootPane;

    @FXML
    VBox vBox;

    @FXML
    Button firstMenu;
    @FXML VBox firstSubVBox;
    @FXML VBox firstSubMenuList;



    Map<VBox,VBox> map = new HashMap<>();

    @FXML Button nluEdit;
    @FXML Button nluTrain;
    @FXML Button nluTest;

    private Parent content;
    private Parent menu;


    @Inject
    FXMLLoader fxmlLoader;

    @Inject
    IntentManager manager;

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

        feedBack.setOnAction(event-> {
            setContentToFeedBack();
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

    private void setContentToFeedBack() {
    }

    private void setContentToStoryEditor() {
    }

    private void setContentToDomain() {
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../view/domainView.fxml"));
        try {
            content = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainPane.setCenter(content);
    }

    private void setContentToTestNLU() {
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../view/nluTestView.fxml"));
        try {
            content = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainPane.setCenter(content);
    }



    public void setContentToNLU(){
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../view/nluView.fxml"));
        try {
            content = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainPane.setCenter(content);
    }



    public void setContentToTrainNLU(){
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../view/nluTrainView.fxml"));
        try {
            content = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainPane.setCenter(content);
    }



    private void setComponentsSize(){

        vBox.setPrefWidth(200);
        vBox.setPrefHeight(800);
    }

    private void addMenusToMap(){
        map.put(firstSubVBox,firstSubMenuList);
        map.put(secondSubVBox,secondSubMenuList);

        for(Map.Entry<VBox,VBox> entry : map.entrySet()){
            entry.getKey().getChildren().remove(entry.getValue());
        }
    }


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

    public void removeOtherMenus(VBox menu){
        for(Map.Entry<VBox,VBox> entry : map.entrySet()){
            if(!entry.getKey().equals(menu)){
                entry.getKey().getChildren().remove(entry.getValue());
            }
        }
    }
}

