package main.pythonProcessing;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.*;

public class CoreSendTestPythonProcessor extends Service<String> {


    Process process;
    String message;
    public CoreSendTestPythonProcessor(Process process,String message){
        this.process = process;
        this.message = message;


    }



    @Override
    protected Task<String> createTask() {

        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                String result;

                OutputStream writeTo = process.getOutputStream();
                InputStream readFrom = process.getInputStream();

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writeTo));
                BufferedReader reader = new BufferedReader(new InputStreamReader(readFrom));
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                try {
                    writer.write(message);
                    writer.write("\n");
                    writer.flush();

                    while((result = reader.readLine()) != null) {
                        if(!result.isEmpty()){
                            return result;

                        }

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

    }
}
