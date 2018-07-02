package model;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

import java.util.List;

public class TrainFinishedEvent extends Event {

    public static final EventType<TrainFinishedEvent> TRAIN_SUCCESSFUL= new EventType(ANY,"TRAIN_SUCCESSFUL");

    private int exitCode;
    private List<String> output;

    public TrainFinishedEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public TrainFinishedEvent(Object arg0, EventTarget arg1, EventType<? extends Event> arg2){
        super(arg0,arg1,arg2);
    }

    public TrainFinishedEvent(){
        this(TRAIN_SUCCESSFUL);
    }


    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

    public List<String> getOutput() {
        return output;
    }

    public void setOutput(List<String> output) {
        this.output = output;
    }
}
