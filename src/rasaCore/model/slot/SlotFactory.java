package rasaCore.model.slot;

public class SlotFactory {

    public Slot getSlot(String name,SLOTTYPE slottype){
        if(slottype == SLOTTYPE.TEXT){
            return new TextSlot(name);
        }else if(slottype == SLOTTYPE.BOOL){
            return new BoolSlot(name);
        }else if(slottype == SLOTTYPE.LIST){
            return new ListSlot(name);
        }else if(slottype == SLOTTYPE.FLOAT){
            return new FloatSlot(name);
        }else if(slottype == SLOTTYPE.CATEGORICAL){
            return new CategoricalSlot(name);
        }else if(slottype == SLOTTYPE.UNFEATURIZED){
            return new UnfeaturizedSlot(name);
        }
        return null;

    }
}
