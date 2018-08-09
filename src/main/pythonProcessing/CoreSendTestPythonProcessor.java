package main.pythonProcessing;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.*;

import static java.lang.Thread.sleep;

/**
 * Service zum Senden von Nachrichten an einen Python-Prozess
 */
public class CoreSendTestPythonProcessor extends Service<String> {

    /**
     * aktiver Pythonprozess
     */
    private Process process;
    /**
     * zu sendende Nachricht
     */
    private String message;
    /**
     * Writer-Instanz zum Schreiben in den Prozess-Input
     */
    private BufferedWriter writer;
    /**
     * Reader zum Leses des Prozess-Error-Outputs
     */
    private BufferedReader errorReader;
    /**
     * Reader zum Lesen des Prozess-Outputs
     */
    private BufferedReader reader;
    /**
     * aktuell aktiver Task
     */
    private SenderTask currentTask;

    /**
     * Erzeugt Instanz und initialisiert Reader und erzeugt einen Thread in dem der Prozess-Output gelesen wird
     * @param process aktuell aktiver Python-Prozess
     * @param message Nachrich die gesendet werden soll
     */
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
                            && !line.contains("Bot loaded. Type a message and press enter:") && !line.contains("Processed")
                            && !line.contains("Epoch") && !line.contains("44/44") && !line.equals("")) {
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

    /**
     * setzt die Nachricht die an den Pythonprozess geschrieben werden soll
     * @param message Nachricht die geschrieben werden soll
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * gibt den aktuellen Pythonprozess zurück
     * @return aktiver Python-Prozess
     */
    public Process getProcess() {
        return process;
    }

    /**
     * erzeugt einen Sendertask der Nachrichten an einen Prozess schreibt
     * @return erzeugten Task
     */
    @Override
    protected Task<String> createTask() {
        currentTask = new SenderTask(this.writer, this.errorReader, this.process, this.message);

        return currentTask;


    }

    /**
     * Task-Klasse zum Senden von Nachrichten an einen Prozess
     */
    class SenderTask extends Task<String> {
        /**
         * Writer der Nachricht an den Prozess-Input schreibt
         */
        BufferedWriter writer;
        /**
         * aktiver Prozess
         */
        Process process;
        /**
         * zu sendende Nachricht
         */
        String message;
        /**
         * Reader der den Error-Stream vom Prozess liest
         */
        BufferedReader errorReader;

        /**
         * Erzeugt neuen Task zum Senden einer Nachricht
         * @param writer Writer zum Schreiben der Nachricht an den Prozess
         * @param errorReader Reader zum lesen des Error-Streams des Prozessen
         * @param process aktiver Prozess an den geschrieben werden soll
         * @param message Nachricht die geschrieben werden soll
         */
        SenderTask(BufferedWriter writer, BufferedReader errorReader, Process process, String message) {
            this.writer = writer;
            this.message = message;
            this.process = process;
            this.errorReader = errorReader;

        }


        @Override
        protected String call() throws Exception {
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

        public void sendUpdate(String message) {
            updateMessage(message);
        }
    }
}


