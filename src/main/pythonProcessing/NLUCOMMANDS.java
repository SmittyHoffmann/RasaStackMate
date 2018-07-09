package main.pythonProcessing;

public enum NLUCOMMANDS {
    TRAIN_NLU("python -m rasa_nlu.train -o %s -d %s -c %s --fixed_model_name %s"),
    LOAD_AND_TEST_NLU("python %s %s");
    private String command;

    NLUCOMMANDS(String commandString) {
        this.command = commandString;
    }

    public String getCommandString() {
        return this.command;
    }
}
