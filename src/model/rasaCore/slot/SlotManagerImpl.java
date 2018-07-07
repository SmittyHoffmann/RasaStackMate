package model.rasaCore.slot;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SlotManagerImpl implements SlotManager{

    ObservableList<Slot> slots;

    public SlotManagerImpl(){
        slots = FXCollections.observableArrayList();
    }


    @Override
    public ObservableList<Slot> getSlots() {
        return this.slots;
    }

    @Override
    public void addSlot(Slot slot) {
        this.slots.add(slot);
    }

    @Override
    public void removeSlot(Slot slot) {
        this.slots.remove(slot);
    }
}
