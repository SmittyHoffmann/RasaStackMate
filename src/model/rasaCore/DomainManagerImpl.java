package model.rasaCore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DomainManagerImpl  implements DomainManager {

    ObservableList<String> intents;
    ObservableList<String> entities;
    ObservableList<String> customActions;

    public DomainManagerImpl(){
        this.intents = FXCollections.observableArrayList();
        this.entities = FXCollections.observableArrayList();
        this.customActions = FXCollections.observableArrayList();
    }
    @Override
    public ObservableList<String> getIntents() {
        return this.intents;
    }

    public void setIntents(ObservableList<String> intents){
        this.intents.clear();
        this.intents.addAll(intents);

    }

    @Override
    public void addIntent(String intent) {
        if(!this.intents.contains(intent)){
            this.intents.add(intent);
        }
    }

    @Override
    public void deleteIntent(String intent) {
        this.intents.remove(intent);
    }

    @Override
    public ObservableList<String> getEntities() {
        return this.entities;
    }

    @Override
    public void addEntity(String entity) {
        if(!this.entities.contains(entity)){
            this.entities.add(entity);
        }
    }

    @Override
    public void deleteEntity(String entity) {
        this.entities.remove(entity);
    }

    @Override
    public ObservableList<String> getCustomActions() {
        return this.customActions;
    }

    @Override
    public void addCustomAction(String customAction) {
        if(!this.customActions.contains(customAction)){
            this.customActions.add(customAction);
        }
    }

    @Override
    public void deleteCustomAction(String customAction) {
        this.customActions.remove(customAction);
    }
}
