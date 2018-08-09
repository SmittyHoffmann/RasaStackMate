package main.util;

import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import rasaNLU.model.entity.EntityManager;
import org.controlsfx.control.textfield.TextFields;

import javax.inject.Inject;

/**
 * Hilfsklasse um Textfelder als Listenzellen zu erzeugen und ihnen AutoCompletion hinzuzufügen
 */
public class TextFieldListCellWithProvider extends ListCell<String> {
    /**
     * Textfeld der Listenzelle
     */
    private TextField textfield;
    /**
     * Provider der Vorschläge macht
     */
    private SuggestionProvider<String> provider;
    /**
     * Vorschläge die der Provider macht
     */
    private ObservableList<String> suggestions = FXCollections.observableArrayList();

    @Inject
    private
    EntityManager manager;


    /**
     * Erzeugt Listzelle
     * @param manager Entity-Manager Instanz um Namen zu erhalten
     */
    public TextFieldListCellWithProvider(EntityManager manager){
        super();
        this.manager = manager;

    }

    /**
     * wird aufgerufen wenn Editieren gestartet wird
     */
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

    /**
     * aktualisiert Listzelle mit eingegebenem Wert
     * @param item Inhalt der Listzelle
     * @param empty Inhalt leer oder gefüllt?
     */
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

    /**
     * Fügt dem Textfield einen Provider hinzu der Vorschläge macht
     */
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
