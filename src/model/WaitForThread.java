package model;

import javafx.event.Event;
import view.WindowManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WaitForThread implements Runnable {

    Process process;
    int exitCode = 1;
    public WaitForThread(Process process){
        this.process = process;
    }


    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            this.exitCode = this.process.waitFor();
            List<String> output = new ArrayList<>();
            String line;
            while((line = in.readLine())!=null){
                output.add(line);
                System.out.println(line);
            }
            if(exitCode == 0){
                Event finished = new TrainFinishedEvent();
                ((TrainFinishedEvent) finished).setOutput(output);
                Event.fireEvent(WindowManager.stage.getScene().getRoot(),finished);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getExitCode(){
        return this.exitCode;
    }
}
