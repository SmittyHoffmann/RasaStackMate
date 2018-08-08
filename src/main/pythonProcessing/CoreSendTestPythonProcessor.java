package main.pythonProcessing;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.*;

import static java.lang.Thread.sleep;

public class CoreSendTestPythonProcessor extends Service<String> {


    Process process;
    String message;
    BufferedWriter writer;
    BufferedReader errorReader;
    BufferedReader reader;
    SenderTask currentTask;

    public CoreSendTestPythonProcessor(Process process, String message) {

        this.process = process;

        OutputStream writeTo = this.process.getOutputStream();
        writer = new BufferedWriter(new OutputStreamWriter(writeTo));
        errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        this.message = message;

        Thread ioThread = new Thread(() -> {
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.contains("DeprecationWarning") && !line.contains("if diff:")
                            && !line.contains("Bot loaded. Type a message and press enter:") &&!line.contains("Processed")
                            && !line.contains("Epoch") && !line.contains("44/44") &&!line.equals("")){
                        System.out.println(line);
                        if (currentTask != null) {
                            sleep(50);
                            currentTask.sendUpdate(line);
                        }

                    }
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        });

        ioThread.start();


    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Process getProcess() {
        return process;
    }

    @Override
    protected Task<String> createTask() {
        currentTask = new SenderTask(this.writer,this.errorReader,this.process,this.message);

        return currentTask;


    }

    class SenderTask extends Task<String>{

    BufferedWriter writer;
    Process process;
    String message;
    BufferedReader errorReader;

    public SenderTask(BufferedWriter writer, BufferedReader errorReader,Process process,String message){
        this.writer = writer;
        this.message = message;
        this.process = process;
        this.errorReader = errorReader;

    }


        @Override
        protected String call() throws Exception{
            try {

                writer.write(message);
                writer.write("\n");
                writer.flush();


            } catch (IOException e) {
                e.printStackTrace();
            }


            int exitCode = process.waitFor();
            System.out.println("wert:" + process.exitValue());
            String error = "";
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                error = error + errorLine;
            }
            return error;
        }

        public void sendUpdate(String message){
            updateMessage(message);
        }
    }
}


