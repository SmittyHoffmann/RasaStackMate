package model.rasaCore;

import javafx.collections.ObservableList;

public interface DomainManager {

    ObservableList<String> getIntents();
    void setIntents(ObservableList<String> intents);
    void addIntent(String intent);
    void deleteIntent(String intent);

    ObservableList<String> getEntities();
    void addEntity(String entity);
    void deleteEntity(String entity);

    ObservableList<String> getCustomActions();
    void addCustomAction(String customAction);
    void deleteCustomAction(String customAction);
}
