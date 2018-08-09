package main.util;


import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import java.util.List;

/**
 * Dialogfenster wenn ein Prozess beendet wurde
 */
public class ProcessEndedDialog extends Dialog {
    /**
     * Ergebnis des Dialogs
     */
    private List<String> result;
    /**
     * Prozess fehlgeschlagen oder erfolgreich
     */
    private boolean success;

    /**
     * Erzeugt Dialog
     * @param result Ergebnis des Prozess
     * @param success true wenn erfolgreich , false wenn fehlgeschlagen
     */
    public ProcessEndedDialog(List<String> result,boolean success){
        super();
        this.result = result;
        this.success = success;
        setupDialog();
    }


    /**
     * Initialisiert Dialogoberfläche
     */
    private void setupDialog(){
        if(success){
            setTitle("Training erfolgreich");
        }else{
            setTitle("Traing fehlgeschlagen");
        }


        ButtonType confirmButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);

        this.getDialogPane().getButtonTypes().addAll(confirmButtonType,cancelButtonType);

        BorderPane pane = new BorderPane();

        TextArea area = new TextArea();
        for(String string : result){
            area.appendText(string+"\n");
        }
        area.setEditable(false);



        pane.setCenter(area);

        this.getDialogPane().setContent(pane);
    }
}



