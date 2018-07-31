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
import rasaCore.model.template.TemplateManager;
import rasaCore.graph.*;
import rasaCore.view.story.StoryGenerator;
import rasaCore.view.story.StoryGeneratorService;
import rasaCore.view.story.StoryReaderService;
import rasaNLU.model.entity.EntityManager;

import javax.inject.Inject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

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

    @Inject
    StoryGenerator storyGenerator;

    @Inject
    DomainManager domainManager;

    @Inject
    EntityManager entityManager;

    @Inject
    TemplateManager templateManager;

    @Inject Graph graph;

    @Inject
    RasaFileManager fileManager;

    Layout layout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> actionList = FXCollections.observableArrayList();
        actionList.addAll(domainManager.getCustomActions());
        actionList.addAll(templateManager.getTemplateNames());
        actionListView.setItems(actionList);

        intentListView.setItems(domainManager.getIntents());

        storyFileChoiceBox.setItems(fileManager.getCoreStoryFiles());


        contentPane.setCenter(graph.getScrollPane());

        graph.getModel().setEntities(domainManager.getEntities());

        layout = new AbegoTreeLayout(graph);
        layout.execute();


        addActionButton.setOnAction(event -> {
            String action = actionListView.getSelectionModel().getSelectedItem();
            if(action != null){
                graph.beginUpdate();
                graph.getModel().addCell(action,CellType.ACTION);
                graph.endUpdate();
            }

        });

        addIntentButton.setOnAction(event -> {
            String intent = intentListView.getSelectionModel().getSelectedItem();
            if(intent != null){
                graph.beginUpdate();
                graph.getModel().addCell(intent,CellType.INTENT);
                graph.endUpdate();
            }

        });


        generateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String fileName;
                TextInputDialog dialog = new TextInputDialog("Dateiname");
                dialog.setContentText("Bitte geben sie den gewünschten Dateinamen an");

                Optional<String> result = dialog.showAndWait();
                if(result.isPresent()){
                    fileName = result.get();
                    StoryGeneratorService generatorService = new StoryGeneratorService(graph.getModel(),entityManager);


                    generatorService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            fileManager.writeStoryFile(generatorService.getValue(),fileName);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Generierung erfolgreich");
                            alert.setHeaderText(null);
                            alert.setContentText("Generierung der Datei war erfolgreich");
                            alert.showAndWait();
                        }
                    });

                    generatorService.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            generatorService.getException().printStackTrace(System.err);
                            System.out.println("Gab nen Fehler");
                        }
                    });

                    generatorService.start();

                }
            }
        });


        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                graph.reset();
            }
        });



        importButton.setOnAction(event -> {
            String fileName = storyFileChoiceBox.getSelectionModel().getSelectedItem();

            //graph.reset();
            //graph.beginUpdate();

            StoryReaderService service = new StoryReaderService(graph,layout, fileName);
            service.start();


            service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Import erfolgreich");
                    alert.setHeaderText(null);
                    alert.setContentText("Der Import war erfolgreich");
                    alert.showAndWait();
                }
            });

            //storyGenerator.fillGraphModel(graph.getModel(),fileName);

            //graph.endUpdate();



            //layout.execute();

        });
    }


}
