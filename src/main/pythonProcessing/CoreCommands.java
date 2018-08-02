package main.pythonProcessing;

public enum CoreCommands {
    TRAIN_CORE_MODEL("python -m rasa_core.train -s %s -o %s -d %s -u %s"),
    LOAD_AND_TEST_BOT("python %s -c %s -n %s");



    private String command;

    CoreCommands(String commandString) {
        this.command = commandString;
    }

    public String getCommandString() {
        return this.command;
    }
}
