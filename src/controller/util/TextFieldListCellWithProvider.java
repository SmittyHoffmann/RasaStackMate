package controller.util;

import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import model.entity.EntityManager;
import org.controlsfx.control.textfield.TextFields;

import javax.inject.Inject;

public class TextFieldListCellWithProvider extends ListCell<String> {
    private TextField textfield;
    private SuggestionProvider<String> provider;
    private ObservableList<String> suggestions = FXCollections.observableArrayList();

    @Inject
    EntityManager manager;

    public TextFieldListCellWithProvider(EntityManager manager){
        super();
        this.manager = manager;

    }

    @Override
    public void startEdit(){
        if(!isEditable() || !getListView().isEditable()){
            return;
        }
        super.startEdit();

        if(isEditing()){
            if(textfield == null){
                textfield = new TextField(getItem());
                this.addProvider();
                textfield.setOnAction(event -> {
                    commitEdit(textfield.getText());
                });
            }
        }

        textfield.setText(getItem());
        setText(null);

        setGraphic(textfield);
        textfield.selectAll();
    }

    @Override
    public void updateItem(String item, boolean empty){
        super.updateItem(item,empty);

        if(isEmpty()){
            setText(null);
            setGraphic(null);
        }
        else{
            if(!isEditing()){
                if(textfield != null){
                    setText(textfield.getText());
                }else{
                    setText(item);
                }
                setGraphic(null);
            }
        }
    }

    public void addProvider(){

        this.textfield.textProperty().addListener((observable,oldValue,newValue) -> {
            String currentText = newValue;

            if(!currentText.isEmpty()) {

                if(currentText.equals("@")){
                    provider.clearSuggestions();
                    provider.addPossibleSuggestions(manager.getEntityPlaceHolders());
                }

                if(currentText.length() >= 2 && currentText.substring(currentText.length()-2).equals(" @")){
                    provider.clearSuggestions();
                    suggestions.clear();

                    for (String value : manager.getEntityPlaceHolders()) {

                        suggestions.add(currentText.substring(0,currentText.length()-1) + value);
                    }

                    provider.addPossibleSuggestions(suggestions);
                }
            }
        });
        provider = SuggestionProvider.create(suggestions);
        TextFields.bindAutoCompletion(textfield, provider);
    }
}
