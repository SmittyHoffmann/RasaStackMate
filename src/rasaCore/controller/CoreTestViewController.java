package rasaCore.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.fileHandling.RasaFileManager;
import main.pythonProcessing.CoreLoadTestPythonProcessor;
import main.pythonProcessing.CoreSendTestPythonProcessor;
import org.controlsfx.control.SegmentedButton;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller für die Testview
 */
public class CoreTestViewController implements Initializable {
    /**
     * Button zum Senden einer Nachricht
     */
    @FXML
    Button sendButton;
    /**
     * Container für die seitlichen Kontrollelemente
     */
    @FXML
    VBox sideBox;

    /**
     * Textfeld zum Eingabe der Nachricht
     */
    @FXML
    TextArea inputMessage;
    /**
     * ScrollPane für das Chatfenster
     */
    @FXML
    ScrollPane chatBoxContainer;

    /**
     * Container für die Nachrichten
     */
    private VBox chatBox;
    /**
     * Geteilter Button für die Modi-Auswahl
     */
    private SegmentedButton toggle;
    /**
     * Button für Normal-Modus
     */
    private ToggleButton normalTest;
    /**
     * Button für interaktiver Trainer modus
     */
    private ToggleButton trainerTest;
    /**
     * Container für GUI-Elemente des Normalen Tests
     */
    private GridPane normalGrid;
    /**
     * Container für GUI-Elemente des Interaktiven Trainings
     */
    private GridPane trainOnlineGrid;
    /**
     * Auswahlbox für Core-Models
     */
    private ChoiceBox<String> coreModelChoiceBox;
    /**
     * Auswahlbox für NLU-Models(normaler Test)
     */
    private ChoiceBox<String> nluModelChoiceBox;

    /**
     * AuswahlBox für NLU-Model(Interaktiver Trainer)
     */
    private ChoiceBox<String> trainNluModelChoiceBox;
    /**
     * AuswahlBox für Storydatei
     */
    private ChoiceBox<String> storyFileChoiceBox;
    /**
     * Auswahlbox für Domaindatei
     */
    private ChoiceBox<String> domainFileChoiceBox;

    /**
     * Button zum Starten eines Python-Skripts
     */
    private Button loadButton;


    @Inject
    RasaFileManager fileManager;
    CoreLoadTestPythonProcessor loadProcessor;
    CoreSendTestPythonProcessor sendProcessor;
    Process currentProcess;
    Label currentAnswer;

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        createComponents();
        setHandlers();






    }

    /**
     * Legt zusätzliche GUI-Elemente an und befüllt die View mit Daten
     */
    private void createComponents() {


        chatBox = new VBox();
        normalTest = new ToggleButton("Normal");
        trainerTest = new ToggleButton("Interaktives Training");
        toggle = new SegmentedButton(normalTest, trainerTest);
        loadButton = new Button("Laden");

        normalGrid = new GridPane();
        trainOnlineGrid = new GridPane();


        Label coreModelLabel = new Label("Core-Model:");
        Label nluModelLabel = new Label("NLU-Model:");
        nluModelChoiceBox = new ChoiceBox<>();
        coreModelChoiceBox = new ChoiceBox<>();
        nluModelChoiceBox.setItems(fileManager.getNLUModels());
        coreModelChoiceBox.setItems(fileManager.getCoreModels());

        Label trainNluModelLabel = new Label("NLU-Model");
        trainNluModelChoiceBox = new ChoiceBox<>();
        trainNluModelChoiceBox.setItems(fileManager.getNLUModels());

        Label storyFileLabel = new Label("Story-Datei:");
        Label domainFileLabel = new Label("Domain-Datei:");
        storyFileChoiceBox = new ChoiceBox<>();
        storyFileChoiceBox.setItems(fileManager.getCoreStoryFiles());
        domainFileChoiceBox = new ChoiceBox<>();
        domainFileChoiceBox.setItems(fileManager.getCoreDomainFiles());


        normalTest.prefWidthProperty().bind(toggle.widthProperty().divide(2));
        trainerTest.prefWidthProperty().bind(toggle.widthProperty().divide(2));
        toggle.setMaxWidth(Double.MAX_VALUE);

        toggle.getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);


        normalGrid.add(nluModelChoiceBox, 1, 0);
        normalGrid.add(coreModelChoiceBox, 1, 1);
        normalGrid.add(coreModelLabel, 0, 1);
        normalGrid.add(nluModelLabel, 0, 0);

        trainOnlineGrid.add(storyFileLabel, 0, 0);
        trainOnlineGrid.add(domainFileLabel, 0, 1);
        trainOnlineGrid.add(trainNluModelLabel, 0, 2);
        trainOnlineGrid.add(storyFileChoiceBox, 1, 0);
        trainOnlineGrid.add(domainFileChoiceBox, 1, 1);
        trainOnlineGrid.add(trainNluModelChoiceBox, 1, 2);


        normalGrid.setHgap(5);
        normalGrid.setVgap(5);

        trainOnlineGrid.setHgap(5);
        trainOnlineGrid.setVgap(5);

        sideBox.getChildren().add(toggle);
        sideBox.getChildren().add(loadButton);

        chatBoxContainer.setContent(chatBox);
    }

    /**
     * Fügt EventHandler zu Buttons hinzu
     */
    private void setHandlers(){
        normalTest.setOnAction(event -> {
            if (sideBox.getChildren().contains(trainOnlineGrid)) {
                sideBox.getChildren().remove(trainOnlineGrid);
            }
            sideBox.getChildren().add(1, normalGrid);
        });

        trainerTest.setOnAction(event -> {
            if (sideBox.getChildren().contains(normalGrid)) {
                sideBox.getChildren().remove(normalGrid);
            }
            sideBox.getChildren().add(1, trainOnlineGrid);
        });





        this.loadButton.setOnAction(event -> {

            if (normalTest.isSelected()) {
                String coreModel = coreModelChoiceBox.getSelectionModel().getSelectedItem();
                String nluModel = nluModelChoiceBox.getSelectionModel().getSelectedItem();


                if (coreModel != null && nluModel != null) {
                    CoreLoadTestPythonProcessor processor = new CoreLoadTestPythonProcessor(coreModel, nluModel);
                    processor.start();
                    loadButton.setDisable(true);
                    chatBox.getChildren().clear();

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
                String storyFile = storyFileChoiceBox.getSelectionModel().getSelectedItem();
                String domainFile = domainFileChoiceBox.getSelectionModel().getSelectedItem();
                String nluModel = trainNluModelChoiceBox.getSelectionModel().getSelectedItem();
                if (storyFile != null && domainFile != null && nluModel != null) {
                    CoreLoadTestPythonProcessor processor = new CoreLoadTestPythonProcessor(storyFile, domainFile, nluModel);
                    processor.start();
                    loadButton.setDisable(true);
                    chatBox.getChildren().clear();
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
            }

        });


        sendButton.setOnAction(event -> {
            String sendText = inputMessage.getText();
            if (!sendText.isEmpty() && currentProcess != null) {
                inputMessage.clear();
                sendButton.setDisable(true);
                Label message = new Label("Nutzer sagt: " + sendText);
                message.setStyle("-fx-background-color: red");
                message.setWrapText(true);
                chatBox.getChildren().add(message);


                currentAnswer = new Label();
                currentAnswer.setMaxWidth(chatBoxContainer.getWidth());
                currentAnswer.setStyle("-fx-background-color: blue");
                currentAnswer.setWrapText(true);


                chatBox.getChildren().add(currentAnswer);

                if (this.sendProcessor != null) {
                    if (currentProcess != sendProcessor.getProcess()) {
                        this.sendProcessor = new CoreSendTestPythonProcessor(currentProcess, sendText);
                        this.sendProcessor.start();
                        this.sendProcessor.messageProperty().addListener(listener);
                    } else {
                        this.sendProcessor.setMessage(sendText);
                        this.sendProcessor.restart();
                    }

                } else {
                    this.sendProcessor = new CoreSendTestPythonProcessor(currentProcess, sendText);
                    this.sendProcessor.start();
                    this.sendProcessor.messageProperty().addListener(listener);

                }


                this.sendProcessor.setOnSucceeded(event1 -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Fehler");
                    alert.setHeaderText(null);
                    alert.setContentText(sendProcessor.getValue());
                    alert.showAndWait();
                });

                this.sendProcessor.setOnFailed(event12 -> {
                    System.out.println("something went wrong");
                });


            }
        });
    }

    /**
     * Listener zum Hinzufügen von Chatnachrichten zum Chatfenster
     */
    ChangeListener<String> listener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (sendButton.isDisabled()) {
                sendButton.setDisable(false);
            }
            if (currentAnswer.getText().equals("")) {
                currentAnswer.setText("Bot sagt: ");
            }
            Platform.runLater(() -> currentAnswer.setText(currentAnswer.getText() + "\n" + newValue));

        }
    };
}
