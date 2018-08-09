package rasaCore.model.domain;

import javafx.collections.ObservableList;

import java.util.List;

/**
 * Klasse die die Domaindaten verwaltet
 */
public interface DomainManager {
    /**
     * Gibt Liste von Intentnamen zurück
     * @return Liste von Intentnamen
     */
    ObservableList<String> getIntents();

    /**
     * setzt Liste von Intentnamen
     * @param intents Liste von Intentnamen die gesetzt werden soll
     */
    void setIntents(List<String> intents);

    /**
     * Fügt Intentname hinzu
     * @param intent Name des Intents
     */
    void addIntent(String intent);

    /**
     * Entfernt Intent von Liste
     * @param intent Name des zu entfernenden Intents
     */
    void deleteIntent(String intent);

    /**
     * Gibt Liste von Entitynamen zurück
     * @return Liste von Entitynamen
     */
    ObservableList<String> getEntities();

    /**
     * Fügt Entityname hinzu
     * @param entity Entityname der hinzugefügt werden soll
     */
    void addEntity(String entity);

    /**
     * Entfernt Entity
     * @param entity Entitiyname der entfernt werden soll
     */
    void deleteEntity(String entity);

    /**
     * Gibt Liste von CustomAction-Namen zurück
     * @return Liste mit Custom-Action-Namen
     */
    ObservableList<String> getCustomActions();

    /**
     * Fügt Custom-Action-Namen hinzu
     * @param customAction Name der CustomAction die hinzugefügt werden soll
     */
    void addCustomAction(String customAction);

    /**
     * Entfernt Custom-Action-Namen
     * @param customAction CustomAction-Name der entfernt werden soll
     */
    void deleteCustomAction(String customAction);

    /**
     * Setzt Liste von Entitynamen
     * @param entityNames Liste die gesetzte werden soll
     */
    void setEntities(List<String> entityNames);
}
