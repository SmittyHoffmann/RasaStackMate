package controller;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import view.WindowManager;


import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MenuController implements Initializable{

    @FXML
    AnchorPane rootPane;

    @FXML VBox vBox;

    @FXML Button firstMenu;
    @FXML VBox firstSubVBox;
    @FXML VBox firstSubMenuList;


    Map <VBox,VBox> map = new HashMap<>();



    public void initialize(URL location, ResourceBundle resources) {
        addMenusToMap();
        setComponentsSize();


        firstMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                toolsSlider(firstSubVBox, firstSubMenuList);
                removeOtherMenus(firstSubVBox);
            }
        });

    }

    private void setComponentsSize(){

        vBox.setPrefWidth(200);
    }

    private void addMenusToMap(){
        map.put(firstSubVBox,firstSubMenuList);


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
