package rasaCore.model.domain;

import rasaCore.model.slot.SlotManager;
import rasaCore.model.template.TemplateManager;

import java.util.List;
import java.util.Map;

/**
 * Interface für Klassen zum Generieren und Einlesen von Domain-Dateien
 */
public interface DomainGenerator {

    /**
     * Generiert die Entity-Daten einer DomainDatei
     * @param domainManager Manager der die Daten enthält
     * @return Schreibbare Entity-Daten
     */
    Map<String, List<String>> generateEntities(DomainManager domainManager);

    /**
     * Generiert die Intent-Daten einer DomainDatei
     * @param domainManager Manager der die Daten enthält
     * @return Schreibbare Intent-Daten
     */
    Map<String, List<String>> generateIntents(DomainManager domainManager);

    /**
     * Generiert die Action-Daten einer Domaindatei
     * @param domainManager Manager der die Custom-Action Daten enthält
     * @param templateManager Manager der die Template-Daten enthält
     * @return Schreibbare Action-Liste
     */
    Map<String, List<String>> generateActions(DomainManager domainManager, TemplateManager templateManager);

    /**
     * Generiert die Template-Daten einer Domaindatei
     * @param manager Manager der die Templatedaten enthält
     * @return schreibbare TemplateDaten
     */
    List<String> generateTemplates(TemplateManager manager);

    /**
     * Generiert die Slotdaten einer Domaindatei
     * @param slotManager Manager der die Slotdaten enthält
     * @return schreibbare Slotdaten
     */
    Map<String, Map<String, Map<String, Object>>> generateSlots(SlotManager slotManager);

    /**
     * Befüllt Manager mit Daten die in der übergebenen Datei zu finden sind
     * @param fileName einzulesende Datei
     * @param domainManager Wird mit Intent-,Entity- und CustomAction-Daten befüllt
     * @param slotManager Wird mit Slot-Daten befüllt
     * @param templateManager Wird mit Templatedaten befüllt
     */
    void fillManagers(String fileName, DomainManager domainManager, SlotManager slotManager, TemplateManager templateManager);
}

