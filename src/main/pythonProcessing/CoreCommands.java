package main.pythonProcessing;

public enum CoreCommands {
    TRAIN_CORE_MODEL("python -m rasa_core.train -s %s -o %s -d %s -u %s"),
    LOAD_RUN_BOT("python %s -c %s -n %s"),
    LOAD_TRAIN_ONLINE_BOT("python %s -s %s -d %s -n %s");



    private String command;

    CoreCommands(String commandString) {
        this.command = commandString;
    }

    public String getCommandString() {
        return this.command;
    }
}
