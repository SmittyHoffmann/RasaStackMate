package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import model.intent.IntentManager;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NLUViewController implements Initializable {

    @FXML
    Tab intentTab;

    @FXML
    Tab entityTab;

    @FXML
    Tab synonymTab;

    @FXML
    Tab regexTab;

    @Inject
    IntentManager manager;

    @Inject
    FXMLLoader fxmlLoader;

    Parent intentView;
    Parent entityView;
    Parent synonymView;
    Parent regexView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../view/intentView.fxml"));
        try {
            intentView = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../view/entityView.fxml"));

        try {
            entityView = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../view/synonymView.fxml"));
        try {
            synonymView = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../view/regexView.fxml"));
        try {
            regexView = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }



        intentTab.setContent(intentView);
        entityTab.setContent(entityView);
        synonymTab.setContent(synonymView);
        regexTab.setContent(regexView);
    }
}
