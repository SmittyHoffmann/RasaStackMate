package rasaCore.model.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Implementierung des DomainManager-Interface
 */
public class DomainManagerImpl implements DomainManager {

    private ObservableList<String> intents;
    private ObservableList<String> entities;
    private ObservableList<String> customActions;

    /**
     * Erzeugt Objekt und initalisiert Listen
     */
    public DomainManagerImpl() {
        this.intents = FXCollections.observableArrayList();
        this.entities = FXCollections.observableArrayList();
        this.customActions = FXCollections.observableArrayList();

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public ObservableList<String> getIntents() {
        return this.intents;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setIntents(List<String> intents) {
        this.intents.clear();
        this.intents.addAll(intents);

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void addIntent(String intent) {
        if (!this.intents.contains(intent)) {
            this.intents.add(intent);
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteIntent(String intent) {
        this.intents.remove(intent);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public ObservableList<String> getEntities() {
        return this.entities;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void addEntity(String entity) {
        if (!this.entities.contains(entity)) {
            this.entities.add(entity);
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEntity(String entity) {
        this.entities.remove(entity);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public ObservableList<String> getCustomActions() {
        return this.customActions;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void addCustomAction(String customAction) {
        if (!this.customActions.contains(customAction)) {
            this.customActions.add(customAction);
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCustomAction(String customAction) {
        this.customActions.remove(customAction);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setEntities(List<String> entityNames) {
        this.entities.clear();
        this.entities.addAll(entityNames);
    }
}
