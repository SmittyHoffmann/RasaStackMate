package rasaCore.view.story;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.application.GUI;
import main.fileHandling.RasaFileManagerImpl;
import rasaCore.graph.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StoryGeneratorImpl implements StoryGenerator {

    @Override
    public void fillGraphModel(GraphModel graphModel, String fileName) {
        int counter = 0;
        String filePath = GUI.getWorkSpace() + "/" + RasaFileManagerImpl.FOLDERS.TRAIN_DATA_FOLDER.getFolderName() + "/" + fileName;

        String intentNameRegex = "\\s*\\*\\s*(?<intentName>[A-Za-z_-]+)";

        Pattern intentNamePattern = Pattern.compile(intentNameRegex);

        String entitiesRegex = "\"(?<entityKey>[A-Za-z]+)\"\\s*:\\s*\"[A-Za-z]+\"";
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
                    ObservableList<String> entities = FXCollections.observableArrayList();
                    Matcher intentNameMatcher = intentNamePattern.matcher(line);
                    intentNameMatcher.find();
                    String intentName = intentNameMatcher.group("intentName");

                    Matcher entitiesMatcher = entityKeyPattern.matcher(line);
                    while (entitiesMatcher.find()) {
                        entities.add(entitiesMatcher.group("entityKey"));
                    }


                    if (intentName != null) {

                        Cell bufferCell = new IntentElement("buffer",intentName);
                        ((IntentElement) bufferCell).setEntities(entities);

                        if (currentCell != null) {
                            for (Cell cell : currentCell.getCellChildren()) {
                                if (cell instanceof IntentElement) {
                                    if (((IntentElement)cell).equals(bufferCell)) {
                                        currentCell = cell;
                                        cellExists = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if(!cellExists){
                            graphModel.addCell(intentName, CellType.INTENT);
                            Cell createdCell = graphModel.getCell(graphModel.getCellCounter());
                            ((IntentElement)createdCell).setEntities(entities);
                            if(currentCell != graphModel.getGraphParent()){
                                graphModel.addEdge(currentCell.getCellId(), createdCell.getCellId());
                            }else{
                                graphModel.getGraphParent().addCellChild(createdCell);
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

                    if (actionName != null) {

                        Cell bufferCell = new ActionElement("buffer",actionName);

                        if (currentCell != null) {
                            for (Cell cell : currentCell.getCellChildren()) {
                                if (cell instanceof ActionElement) {
                                    if (((ActionElement)cell).equals(bufferCell)) {
                                        currentCell = cell;
                                        cellExists = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if(!cellExists){
                            graphModel.addCell(actionName, CellType.ACTION);
                            Cell createdCell = graphModel.getCell(graphModel.getCellCounter());
                            if(currentCell!= graphModel.getGraphParent()){
                                graphModel.addEdge(currentCell.getCellId(), createdCell.getCellId());
                            }else{
                                graphModel.getGraphParent().addCellChild(createdCell);
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

    @Override
    public void generateStories(GraphModel graphModel) {

    }
}
