package main.pythonProcessing;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class NLUSendTestPythonProcessor extends Service<JSONObject> {

    private String sentence;
    private Process process;

    public NLUSendTestPythonProcessor(Process process, String sentence) {
        this.process = process;
        this.sentence = sentence;
    }

    @Override
    protected Task<JSONObject> createTask() {
        return new Task<>() {

            @Override
            protected JSONObject call() throws Exception {
                String result;

                OutputStream writeTo = process.getOutputStream();
                InputStream readFrom = process.getInputStream();

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writeTo));
                BufferedReader reader = new BufferedReader(new InputStreamReader(readFrom));
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                writer.write(sentence);
                writer.write("\n");
                writer.flush();






                while((result = reader.readLine()) != null) {

                    if(result.startsWith("{")){

                        JSONParser parser = new JSONParser();
                        System.out.println(result);
                        String changedResult = result.replaceAll("'","\"");
                        JSONObject resultObject = null;
                        try{
                           resultObject = (JSONObject)parser.parse(changedResult);
                        }catch(ParseException e){
                            e.printStackTrace();
                        }

                        System.out.println("worked also");
                        System.out.println("aktuell:" + resultObject.toString());
                        return resultObject;
                    }

                }

                System.out.println("wert:" + process.exitValue());
                String error;
                while ((error = errorReader.readLine()) != null) {
                    System.out.println(error);
                }
                return null;

            }
        };
    }
}
