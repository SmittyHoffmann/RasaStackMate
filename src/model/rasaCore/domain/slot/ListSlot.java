package model.rasaCore.domain.slot;

public class ListSlot extends Slot {

    public ListSlot(String name){
        super(name);
    }

    @Override
    public SLOTTYPE getType() {
        return SLOTTYPE.LIST;
    }
}
