package model.rasaCore.domain;

import javafx.collections.ObservableList;

import java.util.List;

public interface DomainManager {

    List<String> getIntents();
    void setIntents(List<String> intents);
    void addIntent(String intent);
    void deleteIntent(String intent);

    List<String> getEntities();
    void addEntity(String entity);
    void deleteEntity(String entity);

    List<String> getCustomActions();
    void addCustomAction(String customAction);
    void deleteCustomAction(String customAction);

    void setEntities(List<String> entityNames);
}
