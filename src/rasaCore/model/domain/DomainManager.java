package rasaCore.model.domain;

import javafx.collections.ObservableList;

import java.util.List;

public interface DomainManager {

    ObservableList<String> getIntents();
    void setIntents(List<String> intents);
    void addIntent(String intent);
    void deleteIntent(String intent);

    ObservableList<String> getEntities();
    void addEntity(String entity);
    void deleteEntity(String entity);

    ObservableList<String> getCustomActions();
    void addCustomAction(String customAction);
    void deleteCustomAction(String customAction);

    void setEntities(List<String> entityNames);
}
