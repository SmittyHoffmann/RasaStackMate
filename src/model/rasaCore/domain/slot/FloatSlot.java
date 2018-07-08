package model.rasaCore.domain.slot;

public class FloatSlot extends Slot{
    float minValue;
    float maxValue;

    public FloatSlot(String name){
        super(name);
    }

    public FloatSlot(Slot slot, float minValue, float maxValue){
        super(slot.getName());
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public SLOTTYPE getType() {
        return SLOTTYPE.FLOAT;
    }

    public float getMinValue(){
        return this.minValue;
    }
    public void setMinValue(float minValue){
        this.minValue = minValue;
    }

    public void setMaxValue(float maxValue){
        this.maxValue = maxValue;
    }


    public float getMaxValue(){
        return this.maxValue;
    }
}
