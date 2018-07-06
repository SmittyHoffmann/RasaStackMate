package model.rasaCore.slot;

public class TextSlot extends Slot {

    public TextSlot(String name){
        super(name);
    }

    @Override
    public SLOTTYPE getType() {
        return SLOTTYPE.TEXT;
    }
}
