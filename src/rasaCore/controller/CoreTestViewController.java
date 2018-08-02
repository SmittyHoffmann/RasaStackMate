package rasaCore.controller;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import main.fileHandling.RasaFileManager;
import main.pythonProcessing.CoreLoadTestPythonProcessor;
import main.pythonProcessing.CoreSendTestPythonProcessor;
import org.controlsfx.control.SegmentedButton;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class CoreTestViewController implements Initializable {
    @FXML
    Button sendButton;
    @FXML
    VBox sideBox;
    @FXML
    TextArea inputMessage;
    @FXML
    ScrollPane chatBoxContainer;
    @FXML
    ChoiceBox<String> nluModelChoiceBox;
    @FXML
    ChoiceBox<String> coreModelChoiceBox;

    private VBox chatBox;
    private SegmentedButton toggle;
    private ToggleButton normalTest;
    private ToggleButton trainerTest;
    private Button loadButton;


    @Inject
    RasaFileManager fileManager;
    CoreLoadTestPythonProcessor loadProcessor;
    CoreSendTestPythonProcessor sendProcessor;
    Process currentProcess;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatBox = new VBox();
        normalTest = new ToggleButton("Normal");
        trainerTest = new ToggleButton("Interaktives Training");
        toggle = new SegmentedButton(normalTest,trainerTest);
        loadButton = new Button("Laden");

        normalTest.prefWidthProperty().bind(toggle.widthProperty().divide(2));
        trainerTest.prefWidthProperty().bind(toggle.widthProperty().divide(2));
        toggle.setMaxWidth(Double.MAX_VALUE);

        toggle.getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);
        chatBoxContainer.setContent(chatBox);
        sideBox.getChildren().add(toggle);
        sideBox.getChildren().add(loadButton);

        nluModelChoiceBox.setItems(fileManager.getNLUModels());
        coreModelChoiceBox.setItems(fileManager.getCoreModels());


        this.loadButton.setOnAction(event -> {
            String coreModel = coreModelChoiceBox.getSelectionModel().getSelectedItem();
            String nluModel = nluModelChoiceBox.getSelectionModel().getSelectedItem();
            Toggle buffer = toggle.getToggleGroup().getSelectedToggle();
            if (coreModel != null && nluModel != null) {
                if (normalTest.isSelected()) {
                    CoreLoadTestPythonProcessor processor = new CoreLoadTestPythonProcessor(coreModel, nluModel);
                    processor.start();
                    loadButton.setDisable(true);

                    processor.setOnSucceeded(e -> {
                        this.currentProcess = processor.getValue();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Model geladen");
                        alert.setHeaderText(null);
                        alert.setContentText("Das Model ist geladen");
                        alert.showAndWait();
                        loadButton.setDisable(false);
                    });
                }


                } else if (trainerTest.isSelected()) {

                } else {

                }
        });


        sendButton.setOnAction(event -> {
            String sendText = inputMessage.getText();
            if(!sendText.isEmpty() && currentProcess!=null){
                Label message = new Label("Nutzer sagt: " + sendText);
                message.setStyle("-fx-background-color: #ADF8B8");

                chatBox.getChildren().add(message);
                message.setAlignment(Pos.CENTER_LEFT);

                this.sendProcessor = new CoreSendTestPythonProcessor(currentProcess,sendText);
                sendProcessor.start();

                sendProcessor.setOnSucceeded(event1 -> {
                    String answer = sendProcessor.getValue();

                    Label answerLabel = new Label("Bot sagt: "+answer);
                    answerLabel.setStyle("-fxbackground-color: #ADDBF8");
                    chatBox.getChildren().add(answerLabel);
                    answerLabel.setAlignment(Pos.CENTER_RIGHT);
                });
            }
        });




    }
}
