package controller.rasaNLU.develop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import main.GUI;
import model.rasaNLU.nluJSONData.NLUTrainDataGenerator;
import model.fileHandling.RasaFileManager;
import model.fileHandling.RasaFileManagerImpl;
import model.rasaNLU.entity.EntityManager;
import model.rasaNLU.intent.IntentManager;
import model.rasaNLU.regex.RegexManager;
import model.rasaNLU.synonym.SynonymManager;
import org.json.simple.JSONObject;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class NLUViewController implements Initializable {

    @FXML Button generateButton;

    @FXML Button importButton;

    @FXML ChoiceBox <String> trainFileChoiceBox;


    @FXML
    Tab intentTab;

    @FXML
    Tab entityTab;

    @FXML
    Tab synonymTab;

    @FXML
    Tab regexTab;

    @Inject
    RasaFileManager fileManager;

    @Inject
    NLUTrainDataGenerator generator;

    @Inject
    FXMLLoader fxmlLoader;


    @Inject
    IntentManager intentManager;

    @Inject
    EntityManager entityManager;

    @Inject
    SynonymManager synonymManager;

    @Inject
    RegexManager regexManager;







    Parent intentView;
    Parent entityView;
    Parent synonymView;
    Parent regexView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../../../view/intentView.fxml"));
        try {
            intentView = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../../../view/entityView.fxml"));

        try {
            entityView = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../../../view/synonymView.fxml"));
        try {
            synonymView = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource("../../../view/regexView.fxml"));
        try {
            regexView = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        trainFileChoiceBox.setItems(fileManager.getNLUTrainFiles());

        generateButton.setOnAction(e-> {
            String fileName;
            TextInputDialog dialog = new TextInputDialog("Dateiname");
            dialog.setContentText("Bitte geben sie den gewünschten Dateinamen an");

            Optional<String> result = dialog.showAndWait();
            if(result.isPresent()){
                fileName = result.get();
                JSONObject root = generator.generateTrainingData(intentManager.getIntents(),entityManager.getEntities(),synonymManager.getSynonyms(),regexManager.getRegexList());
                fileManager.writeNLUTrainData(root,fileName);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Datei schreiben erfolgreich");
                alert.setHeaderText(null);
                alert.setContentText("Das Generieren der Trainingsdatei war erfolgreich!");
                alert.showAndWait();
            }
        });

        importButton.setOnAction(event ->{
            String fileName = trainFileChoiceBox.getSelectionModel().getSelectedItem();
            generator.fillNLUManagers(fileName,intentManager,entityManager,synonymManager,regexManager);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Import erfolgreich");
            alert.setHeaderText(null);
            alert.setContentText("Der Import war erfolgreich");

            alert.showAndWait();

        });

        intentTab.setContent(intentView);
        entityTab.setContent(entityView);
        synonymTab.setContent(synonymView);
        regexTab.setContent(regexView);
    }
}
