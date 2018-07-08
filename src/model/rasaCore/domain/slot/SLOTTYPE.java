package model.rasaCore.domain.slot;

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

    public static SLOTTYPE fromString(String type) {
        for (SLOTTYPE s : SLOTTYPE.values()) {
            if (s.type.equalsIgnoreCase(type)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Kein Slottyp für den Typ "+ type+ " gefunden");
    }

}


