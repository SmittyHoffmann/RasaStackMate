package rasaCore.model.story;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import main.application.GUI;
import main.fileHandling.RasaFileManagerImpl;
import rasaCore.model.domain.DomainManager;
import rasaCore.model.graph.*;
import rasaCore.model.template.TemplateManager;
import rasaCore.view.story.Layout;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StoryReaderService extends Service<String> {

    Graph graph;
    Layout layout;
    String fileName;
    DomainManager domainManager;
    TemplateManager templateManager;

    public StoryReaderService(Graph graph, Layout layout, String fileName, DomainManager domainManager, TemplateManager templateManager) {
        this.graph = graph;
        this.layout = layout;
        this.fileName = fileName;
        this.domainManager = domainManager;
        this.templateManager = templateManager;
    }

    @Override
    protected Task<String> createTask() {
        return new Task<>() {
            @Override
            protected String call() throws NullPointerException {


                System.out.println("hier noch");
                graph.reset();
                System.out.println("hier nicht mehr");
                graph.beginUpdate();

                fillGraphModel(graph.getModel(), fileName);

                layout.execute();

                graph.endUpdate();

                return "fertig";
            }
        };
    }


    private void fillGraphModel(GraphModel graphModel, String fileName) {
        int counter = 0;
        String filePath = GUI.getWorkSpace() + "/" + RasaFileManagerImpl.FOLDERS.TRAIN_DATA_FOLDER.getFolderName() + "/" + fileName;

        String intentNameRegex = "\\s*\\*\\s*(?<intentName>[A-Za-z_-]+)";

        Pattern intentNamePattern = Pattern.compile(intentNameRegex);

        String entitiesRegex = "\"(?<entityKey>[A-Za-z]+)\"\\s*:\\s*\"(?<entityValue>[A-Za-z]+)\"";
        Pattern entityKeyPattern = Pattern.compile(entitiesRegex);

        String actionRegex = "\\s*-\\s*(?<actionName>[a-zA-Z_-]+)";
        Pattern actionNamePattern = Pattern.compile(actionRegex);


        boolean inStory = false;
        Cell currentCell = null;
        try {
            currentCell = graphModel.getGraphParent();
            BufferedReader reader = new BufferedReader((new FileReader(filePath)));
            String line = reader.readLine();
            boolean cellExists;
            while (line != null) {
                cellExists = false;
                line = line.trim();
                if (line.startsWith("##")) {
                    inStory = true;


                } else if (line.startsWith("*") && inStory) {
                    ObservableMap<String, String> entities = FXCollections.observableHashMap();
                    Matcher intentNameMatcher = intentNamePattern.matcher(line);
                    intentNameMatcher.find();
                    String intentName = intentNameMatcher.group("intentName");

                    Matcher entitiesMatcher = entityKeyPattern.matcher(line);
                    while (entitiesMatcher.find()) {
                        String entityKey = entitiesMatcher.group("entityKey");
                        String entityValue = entitiesMatcher.group("entityValue");
                        if (!domainManager.getEntities().contains(entityKey)) {
                            domainManager.addEntity(entityKey);
                        }
                        entities.put(entityKey, entityValue);
                    }


                    if (intentName != null) {

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                domainManager.addIntent(intentName);
                            }
                        });


                        Cell bufferCell = new IntentElement("buffer", intentName);
                        ((IntentElement) bufferCell).setEntities(entities);

                        if (currentCell != null) {
                            for (Cell cell : currentCell.getCellChildren()) {
                                if (cell instanceof IntentElement) {
                                    if (((IntentElement) cell).equals(bufferCell)) {
                                        currentCell = cell;
                                        cellExists = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (!cellExists) {
                            graphModel.addCell(intentName, CellType.INTENT);
                            Cell createdCell = graphModel.getCell(graphModel.getCellCounter());
                            ((IntentElement) createdCell).setEntities(entities);
                            if (currentCell != graphModel.getGraphParent()) {
                                graphModel.addEdge(currentCell.getCellId(), createdCell.getCellId());
                            } else {
                                currentCell.addCellChild(createdCell);
                                createdCell.addCellParent(currentCell);
                            }
                            currentCell = createdCell;
                        }
                    } else {
                        throw new NullPointerException("Datei fehlerhaft");
                    }
                } else if (line.startsWith("-") && inStory) {
                    Matcher actionNameMatcher = actionNamePattern.matcher(line);
                    actionNameMatcher.find();
                    String actionName = actionNameMatcher.group("actionName");

                    if (actionName.startsWith("utter")) {
                        if (!templateManager.getTemplateNames().contains(actionName)) {
                            templateManager.addTemplate(actionName);
                        }
                    } else {
                        if (!domainManager.getCustomActions().contains(actionName)) {
                            domainManager.addCustomAction(actionName);
                        }
                    }

                    if (actionName != null) {

                        Cell bufferCell = new ActionElement("buffer", actionName);

                        if (currentCell != null) {
                            for (Cell cell : currentCell.getCellChildren()) {
                                if (cell instanceof ActionElement) {
                                    if (((ActionElement) cell).equals(bufferCell)) {
                                        currentCell = cell;
                                        cellExists = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (!cellExists) {
                            graphModel.addCell(actionName, CellType.ACTION);
                            Cell createdCell = graphModel.getCell(graphModel.getCellCounter());
                            if (currentCell != graphModel.getGraphParent()) {
                                graphModel.addEdge(currentCell.getCellId(), createdCell.getCellId());
                            } else {
                                currentCell.addCellChild(createdCell);
                                createdCell.addCellParent(currentCell);
                            }
                            currentCell = createdCell;
                        }

                    }
                } else if (line.isEmpty()) {
                    currentCell = graphModel.getGraphParent();
                    inStory = false;
                }
                System.out.println(counter++);
                line = reader.readLine();

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
