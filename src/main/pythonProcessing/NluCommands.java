package main.pythonProcessing;

/**
 * Enumeration für Kommandozeilenbefehle zum Starten von NLU-Python-Skripts
 */
public enum NluCommands {
    /**
     * Kommandozeilenbefehl zum Starten des NLU-Trainings
     */
    TRAIN_NLU("python -m rasa_nlu.train -o %s -d %s -c %s --fixed_model_name %s"),
    /**
     * Kommandozeilenbefehl zum Laden eines NLU-Test-Pythonskript
     */
    LOAD_AND_TEST_NLU("python %s %s");

    /**
     * hinterlegter KommandozeilenBefehl
     */
    private String command;

    /**
     * Weist Enum-Wert Kommandozeilenbefehl zu
     * @param commandString zuzuweisender Kommandozeilenbefehl
     */
    NluCommands(String commandString) {
        this.command = commandString;
    }

    /**
     * gibt hinterlegten KommandozeilenBefehl zurück
     * @return hinterlegter Kommandozeilenbefehl
     */
    public String getCommandString() {
        return this.command;
    }
}
