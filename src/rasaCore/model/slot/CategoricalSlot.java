package rasaCore.model.slot;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CategoricalSlot extends Slot{

    ObservableList<String> values;

    public CategoricalSlot(String name){
        super(name);
        this.values = FXCollections.observableArrayList();

    }

    @Override
    public SLOTTYPE getType() {
        return SLOTTYPE.CATEGORICAL;
    }



    public ObservableList<String> getValues(){
        return this.values;
    }

    public void setValues(List<String> values) {
        this.values.clear();
        this.values.addAll(values);
    }
}
