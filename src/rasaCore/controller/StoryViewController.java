package rasaCore.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;


import main.fileHandling.RasaFileManager;
import rasaCore.model.domain.DomainManager;
import rasaCore.model.graph.CellType;
import rasaCore.model.graph.Graph;
import rasaCore.model.template.TemplateManager;
import rasaCore.model.story.StoryGeneratorService;
import rasaCore.model.story.StoryReaderService;
import rasaCore.view.story.AbegoTreeLayout;
import rasaCore.view.story.Layout;
import javax.inject.Inject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Controller für den Story-Editor
 */
public class StoryViewController implements Initializable {

    @FXML Button resetButton;
    @FXML
    Button addIntentButton;
    @FXML
    ListView<String> intentListView;
    @FXML
    Button addActionButton;
    @FXML
    ListView<String> actionListView;
    @FXML
    Button generateButton;
    @FXML
    Button importButton;
    @FXML
    ChoiceBox<String> storyFileChoiceBox;
    @FXML
    BorderPane contentPane;

    private ObservableList<String> actionList;


    @Inject
    DomainManager domainManager;

    @Inject
    TemplateManager templateManager;

    @Inject
    Graph graph;

    @Inject
    RasaFileManager fileManager;

    Layout layout;

    /**
     *{@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {


            setupComponents();
            setHandler();




    }

    /**
     * Bereitet GUI-Elemente vor und füllt Daten
     */
    private void setupComponents(){
        actionList = FXCollections.observableArrayList();

        fillActionList();
        actionListView.setItems(actionList);

        intentListView.setItems(domainManager.getIntents());

        storyFileChoiceBox.setItems(fileManager.getCoreStoryFiles());


        contentPane.setCenter(graph.getScrollPane());

        graph.getModel().setEntities(domainManager.getEntities());

        layout = new AbegoTreeLayout(graph);
        layout.execute();

    }

    /**
     * Befüllt die ActionList
     */
    private void fillActionList() {
        actionList.addAll(domainManager.getCustomActions());
        actionList.addAll(templateManager.getTemplateNames());
        actionList.add("action_restart");
    }

    private void setHandler() {
        addActionButton.setOnAction(event -> {
            String action = actionListView.getSelectionModel().getSelectedItem();
            if (action != null) {
                graph.beginUpdate();
                graph.getModel().addCell(action, CellType.ACTION);
                graph.getModel().getCell(graph.getModel().getCellCounter()).setManaged(true);
                graph.endUpdate();
            }

        });

        addIntentButton.setOnAction(event -> {
            String intent = intentListView.getSelectionModel().getSelectedItem();
            if (intent != null) {
                graph.beginUpdate();
                graph.getModel().addCell(intent, CellType.INTENT);
                graph.getModel().getCell(graph.getModel().getCellCounter()).setManaged(true);
                graph.endUpdate();
            }

        });


        generateButton.setOnAction(event -> {
            String fileName;
            TextInputDialog dialog = new TextInputDialog("Dateiname");
            dialog.setContentText("Bitte geben sie den gewünschten Dateinamen an");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                fileName = result.get();
                StoryGeneratorService generatorService = new StoryGeneratorService(graph.getModel());


                generatorService.setOnSucceeded(event13 -> {
                    fileManager.writeStoryFile(generatorService.getValue(), fileName);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Generierung erfolgreich");
                    alert.setHeaderText(null);
                    alert.setContentText("Generierung der Datei war erfolgreich");
                    alert.showAndWait();
                });

                generatorService.setOnFailed(event12 -> {
                    generatorService.getException().printStackTrace(System.err);
                    System.out.println("Gab nen Fehler");
                });

                generatorService.start();

            }
        });


        resetButton.setOnAction(event -> graph.reset());


        importButton.setOnAction(event -> {
            String fileName = storyFileChoiceBox.getSelectionModel().getSelectedItem();

            //graph.reset();
            //graph.beginUpdate();

            StoryReaderService service = new StoryReaderService(graph, layout, fileName, domainManager, templateManager);
            service.start();


            service.setOnSucceeded(event1 -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Import erfolgreich");
                alert.setHeaderText(null);
                alert.setContentText("Der Import war erfolgreich");
                alert.showAndWait();
                actionList.clear();
                fillActionList();

            });

        });


    }
}
