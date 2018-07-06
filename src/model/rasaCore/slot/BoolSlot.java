package model.rasaCore.slot;

public class BoolSlot extends Slot{

    public BoolSlot(String name)
    {super(name);
    }

    @Override
    public SLOTTYPE getType() {
        return SLOTTYPE.BOOL;
    }
}
