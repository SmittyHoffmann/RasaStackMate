package model.rasaCore.slot;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoricalSlot extends Slot{

    ObservableList<String> values;

    public CategoricalSlot(String name){
        super(name);
        this.values = FXCollections.observableArrayList();

    }

    @Override
    public SLOTTYPE getType() {
        return null;
    }

    public void addValue(String value){
        this.values.add(value);
    }
    public void deleteValue(String value){
        this.values.remove(value);
    }

    public ObservableList<String> getValues(){
        return this.values;
    }
}
