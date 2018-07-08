package model.rasaCore.domain.slot;

public class UnfeaturizedSlot extends Slot {

    public UnfeaturizedSlot(String name){
        super(name);
    }


    @Override
    public SLOTTYPE getType() {
        return SLOTTYPE.UNFEATURIZED;
    }
}
