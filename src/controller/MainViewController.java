package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.IntentManager;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {


    @FXML
    BorderPane mainPane;

    private Parent content;
    private Parent menu;


    @Inject
    FXMLLoader fxmlLoader;

    @Inject
    IntentManager manager;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../view/nluView.fxml"));
        try {
            content = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../view/menu.fxml"));
        try {
            menu = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainPane.setCenter(content);
        mainPane.setLeft(menu);

    }
}
