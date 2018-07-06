package model.rasaCore.slot;

public enum SLOTTYPE {

    TEXT("text"),
    BOOL("bool"),
    CATEGORICAL("categorical"),
    FLOAT("float"),
    LIST("list"),
    UNFEATURIZED("unfeaturized");

    private String type;
    SLOTTYPE(String type){ this.type = type;}

    public String getType(){ return this.type;}
}


