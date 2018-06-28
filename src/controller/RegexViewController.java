package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Pair;
import model.Regex;
import model.RegexManager;


import javax.inject.Inject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegexViewController implements Initializable {

    @FXML
    private TableView<Regex> regexTableView;

    @FXML
    private TableColumn<Regex,String> nameColumn;

    @FXML
    private TableColumn<Regex,String> patternColumn;

    @FXML
    private Button addRegexButton;

    @FXML
    private Button deleteRegexButton;

    @Inject
    RegexManager regexManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        regexTableView.setEditable(true);


        regexTableView.setItems(regexManager.getRegexList());
        regexTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        patternColumn.setCellValueFactory(cellData -> cellData.getValue().patternProperty());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        patternColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        addRegexButton.setOnAction(e ->{
            Dialog<Pair<String,String>> dialog = new AddRegexDialog();
            Optional<Pair<String,String>> result = dialog.showAndWait();

            result.ifPresent(resultData -> {
                regexTableView.getItems().add(new Regex(resultData.getKey(),resultData.getValue()));
            });

        });

        deleteRegexButton.setOnAction(e -> {
            if(regexTableView.getSelectionModel().getSelectedItem()!= null) {
                regexTableView.getItems().remove(regexTableView.getSelectionModel().getSelectedItem());
            }
        });

    }


}
