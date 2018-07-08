package model.rasaCore.domain.slot;

import javafx.collections.ObservableList;

public interface SlotManager {
    ObservableList<Slot> getSlots();
    void addSlot(Slot slot);
    void removeSlot(Slot slot);
}
