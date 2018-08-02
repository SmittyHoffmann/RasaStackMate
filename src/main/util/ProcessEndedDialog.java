package main.util;


import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import java.util.List;

public class ProcessEndedDialog extends Dialog {
    List<String> result;
    boolean success;

    public ProcessEndedDialog(List<String> result,boolean success){
        super();
        this.result = result;
        this.success = success;
        setupDialog();
    }



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



