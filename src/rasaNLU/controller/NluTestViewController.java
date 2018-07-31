package rasaNLU.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import rasaNLU.model.entity.EntityManager;
import main.fileHandling.RasaFileManager;
import rasaNLU.model.intent.IntentManager;
import main.pythonProcessing.NLULoadTestPythonProcessor;
import main.pythonProcessing.NLUSendTestPythonProcessor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.inject.Inject;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class NluTestViewController implements Initializable {


    @FXML Label textLabel;
    @FXML Label intentLabel;
    @FXML TextFlow entityText;

    @FXML Button addButton;
    @FXML
    ChoiceBox<String> modelChoiceBox;

    @FXML
    TextField testTextfield;

    @FXML
    Button loadModelButton;

    @FXML
    Button testSentenceButton;

    JSONObject resultObject;

    @Inject
    RasaFileManager fileManager;

    @Inject
    IntentManager intentManager;

    @Inject
    EntityManager entityManager;

    Process currentProcess;
    JSONObject currentJSONObject;
    NLULoadTestPythonProcessor loadProcessor;
    NLUSendTestPythonProcessor sendProcessor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.modelChoiceBox.setItems(fileManager.getNLUModels());

        String defaultFont = Font.getDefault().getName();
        textLabel.setFont(Font.font(defaultFont,FontWeight.BOLD,12));
        textLabel.setTextFill(Color.WHITE);
        intentLabel.setFont(Font.font(defaultFont,FontWeight.BOLD,12));
        intentLabel.setTextFill(Color.WHITE);


        this.loadModelButton.setOnAction(e -> {
            String modelName = this.modelChoiceBox.getSelectionModel().getSelectedItem();
            if (modelName != null) {
                if (this.currentProcess != null) {
                    this.currentProcess.destroy();
                    this.currentProcess = null;
                }
                this.loadProcessor = new NLULoadTestPythonProcessor(modelName);
                this.loadProcessor.start();
            }


            this.loadProcessor.setOnSucceeded(event -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Model geladen");
                alert.setHeaderText(null);
                alert.setContentText("Das Model ist geladen");
                alert.showAndWait();
                this.currentProcess = this.loadProcessor.getValue();
                this.loadProcessor.reset();
            });
        });

        this.testSentenceButton.setOnAction(e -> {
            if (this.currentProcess != null && !this.testTextfield.getText().isEmpty()) {
                this.sendProcessor = new NLUSendTestPythonProcessor(this.currentProcess, this.testTextfield.getText());
                this.sendProcessor.start();

            this.sendProcessor.setOnSucceeded(event -> {
                entityText.getChildren().clear();
                this.currentJSONObject = this.sendProcessor.getValue();

                textLabel.setText((String) currentJSONObject.get("text"));
                JSONObject intent = (JSONObject) currentJSONObject.get("intent");
                JSONArray entities = (JSONArray) currentJSONObject.get("entities");

                String intentName = (String) intent.get("name");

                double intentConfidence = (double) intent.get("confidence");

                Iterator<JSONObject> iterator = entities.iterator();
                while(iterator.hasNext()){
                    JSONObject entity = iterator.next();
                    String entityValue = (String) entity.get("value");
                    String entityName = (String) entity.get("entity");
                    double entityConfidence = (double) entity.get("confidence");
                    Text bufferText = new Text(entityName +" : "+ entityValue);
                    if(entityConfidence > 0.75){
                        bufferText.setFill(Color.GREEN);
                    }else{
                        bufferText.setFill(Color.RED);
                    }
                    entityText.getChildren().add(bufferText);
                }

                intentLabel.setText(intentName);
                if(intentConfidence > 0.75 ){
                    intentLabel.setTextFill(Color.GREEN);
                }else{
                    intentLabel.setTextFill(Color.RED);
                }

                this.sendProcessor.reset();

            });
            }
        });

        addButton.setOnAction(e-> {
            this.intentManager.parseJSONIntent(currentJSONObject);
            this.entityManager.parseJSONEntities((JSONArray)(currentJSONObject.get("entities")));
        });

    }
}
