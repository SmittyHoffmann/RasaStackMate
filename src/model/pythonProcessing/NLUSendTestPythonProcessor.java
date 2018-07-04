package model.pythonProcessing;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.*;

public class NLUSendTestPythonProcessor extends Service<String> {

    private String sentence;
    private Process process;

    public NLUSendTestPythonProcessor(Process process, String sentence){
        this.process = process;
        this.sentence = sentence;
    }

    @Override
    protected Task<String> createTask() {
        return new Task<>(){

            @Override
            protected String call() throws Exception {
                String result;
                OutputStream writeTo = process.getOutputStream();
                InputStream readFrom = process.getInputStream();

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writeTo));
                BufferedReader reader = new BufferedReader(new InputStreamReader(readFrom));

                writer.write(sentence);
                writer.flush();
                writer.close();

                while(process.isAlive()){
                    while((result = reader.readLine()) != null){
                        return result;
                    }
                }return null;
            }
        };
    }
}
