package rasaCore.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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


    private VBox chatBox;

    private SegmentedButton toggle;
    private ToggleButton normalTest;
    private ToggleButton trainerTest;

    private GridPane normalGrid;
    private GridPane trainOnlineGrid;

    private Label coreModelLabel;
    private ChoiceBox<String> coreModelChoiceBox;

    private Label nluModelLabel;
    private ChoiceBox<String> nluModelChoiceBox;

    private Label trainNluModelLabel;
    private ChoiceBox<String> trainNluModelChoiceBox;

    private ChoiceBox<String> storyFileChoiceBox;
    private ChoiceBox<String> domainFileChoiceBox;
    private Label storyFileLabel;
    private Label domainFileLabel;

    private Button loadButton;



    @Inject
    RasaFileManager fileManager;
    CoreLoadTestPythonProcessor loadProcessor;
    CoreSendTestPythonProcessor sendProcessor;
    Process currentProcess;
    Label currentAnswer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        createComponents();

        normalGrid.add(nluModelChoiceBox,1,0);
        normalGrid.add(coreModelChoiceBox,1,1);
        normalGrid.add(coreModelLabel,0,1);
        normalGrid.add(nluModelLabel,0,0);

        trainOnlineGrid.add(storyFileLabel,0,0);
        trainOnlineGrid.add(domainFileLabel,0,1);
        trainOnlineGrid.add(trainNluModelLabel,0,2);
        trainOnlineGrid.add(storyFileChoiceBox,1,0);
        trainOnlineGrid.add(domainFileChoiceBox,1,1);
        trainOnlineGrid.add(trainNluModelChoiceBox,1,2);


        normalGrid.setHgap(5);
        normalGrid.setVgap(5);

        trainOnlineGrid.setHgap(5);
        trainOnlineGrid.setVgap(5);

        sideBox.getChildren().add(toggle);
        sideBox.getChildren().add(loadButton);


        normalTest.setOnAction(event -> {
            if(sideBox.getChildren().contains(trainOnlineGrid)){
                sideBox.getChildren().remove(trainOnlineGrid);
            }
            sideBox.getChildren().add(1,normalGrid);
        });

        trainerTest.setOnAction(event -> {
            if(sideBox.getChildren().contains(normalGrid)){
                sideBox.getChildren().remove(normalGrid);
            }
            sideBox.getChildren().add(1,trainOnlineGrid);
        });


        chatBoxContainer.setContent(chatBox);


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


                }else if (trainerTest.isSelected()) {
                    String storyFile = storyFileChoiceBox.getSelectionModel().getSelectedItem();
                    String domainFile = domainFileChoiceBox.getSelectionModel().getSelectedItem();
                    String nluModel = trainNluModelChoiceBox.getSelectionModel().getSelectedItem();
                    if(storyFile!= null && domainFile!= null && nluModel!= null){
                        CoreLoadTestPythonProcessor processor = new CoreLoadTestPythonProcessor(storyFile, domainFile,nluModel);
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
            if(!sendText.isEmpty() && currentProcess!=null){
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

                if(this.sendProcessor != null){
                    this.sendProcessor.setMessage(sendText);
                    this.sendProcessor.restart();



                }else {
                    this.sendProcessor = new CoreSendTestPythonProcessor(currentProcess,sendText);
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

    public void createComponents(){
        chatBox = new VBox();
        normalTest = new ToggleButton("Normal");
        trainerTest = new ToggleButton("Interaktives Training");
        toggle = new SegmentedButton(normalTest,trainerTest);
        loadButton = new Button("Laden");

        normalGrid = new GridPane();
        trainOnlineGrid = new GridPane();


        coreModelLabel = new Label("Core-Model:");
        nluModelLabel = new Label("NLU-Model:");
        nluModelChoiceBox = new ChoiceBox<>();
        coreModelChoiceBox = new ChoiceBox<>();
        nluModelChoiceBox.setItems(fileManager.getNLUModels());
        coreModelChoiceBox.setItems(fileManager.getCoreModels());

        trainNluModelLabel = new Label("NLU-Model");
        trainNluModelChoiceBox = new ChoiceBox<>();
        trainNluModelChoiceBox.setItems(fileManager.getNLUModels());

        storyFileLabel = new Label("Story-Datei:");
        domainFileLabel = new Label("Domain-Datei:");
        storyFileChoiceBox = new ChoiceBox<>();
        storyFileChoiceBox.setItems(fileManager.getCoreStoryFiles());
        domainFileChoiceBox = new ChoiceBox<>();
        domainFileChoiceBox.setItems(fileManager.getCoreDomainFiles());


        normalTest.prefWidthProperty().bind(toggle.widthProperty().divide(2));
        trainerTest.prefWidthProperty().bind(toggle.widthProperty().divide(2));
        toggle.setMaxWidth(Double.MAX_VALUE);

        toggle.getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);
    }






    ChangeListener<String> listener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if(sendButton.isDisabled()){
                sendButton.setDisable(false);
            }
            if(currentAnswer.getText().equals("")){
                currentAnswer.setText("Bot sagt: ");
            }
            Platform.runLater(() -> currentAnswer.setText(currentAnswer.getText() +"\n"+ newValue));

        }
    };
}
