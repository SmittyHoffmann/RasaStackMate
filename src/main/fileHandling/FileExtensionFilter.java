package main.fileHandling;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Filename-Filter Implementierung um Dateien nach der Dateienung zu filtern
 */
public class FileExtensionFilter implements FilenameFilter {
    //gewünschtes Datei-Ende
    private String extension;

    /**
     * Erzeugt einen Filter
     * @param extension Dateiende nach dem überprüft werden soll
     */
    FileExtensionFilter(String extension){
        this.extension = extension;
    }

    /**
     * Überprüft Dateiendung
     * @param dir
     * @param name
     * @return Wert ob Dateiname mit der extension endet
     */
    @Override
    public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith(this.extension);
    }
}
