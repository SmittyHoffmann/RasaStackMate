package main.pythonProcessing;

/**
 * Kommandozeilenbefehle zum Starten der Python-Skripte
 */
public enum CoreCommands {

    /**
     * Kommandozeilenbefehl zum Trainieren
     */
    TRAIN_CORE_MODEL("python -m rasa_core.train -s %s -o %s -d %s -u %s"),
    /**
     * Kommandozeilenbefehl zum normalen Testen
     */
    LOAD_RUN_BOT("python %s -c %s -n %s"),
    /**
     * Kommandozeilenbefehl für Interaktives Training
     */
    LOAD_TRAIN_ONLINE_BOT("python %s -s %s -d %s -n %s");


    /**
     * hinterlegter Kommandozeilenbefehl
     */
    private String command;

    /**
     * Weist Kommandozeilenbefehl dem Enum-Wert zu
     * @param commandString Kommandozeilenbefehl
     */
    CoreCommands(String commandString) {
        this.command = commandString;
    }

    /**
     * Gibt zzgewiesenen Kommandozeilenbefehl zurück
     * @return zugewiesener Kommandozeilenbefehl
     */
    public String getCommandString() {
        return this.command;
    }
}
