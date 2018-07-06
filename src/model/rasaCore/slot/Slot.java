package model.rasaCore.slot;

public abstract class Slot {

    String name;

    public Slot(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public abstract SLOTTYPE getType();

}
