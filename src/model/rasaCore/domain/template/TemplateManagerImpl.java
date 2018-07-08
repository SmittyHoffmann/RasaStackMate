package model.rasaCore.domain.template;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;

public class TemplateManagerImpl implements TemplateManager{

    HashMap<String,Template> templates;
    ObservableList<String> templateNames;

    public TemplateManagerImpl(){
        this.templates = new HashMap<>();
        this.templateNames = FXCollections.observableArrayList();
    }


    @Override
    public void addUtterance(String templateName, String utterance) {
        this.templates.get(templateName).addUtterance(utterance);
    }

    @Override
    public void addTemplate(String templateName) {
        if(!this.templates.containsKey(templateName)){
            this.templates.put(templateName,new Template());
            this.templateNames.add(templateName);
        }
    }

    @Override
    public void deleteTemplate(String templateName) {
        this.templateNames.remove(templateName);
        this.templates.remove(templateName);
    }

    @Override
    public Template getTemplate(String templateName) {
        return this.templates.get(templateName);
    }

    @Override
    public ObservableList<String> getTemplateNames() {
        return this.templateNames;
    }

    @Override
    public HashMap<String, Template> getTemplates() {
        return this.templates;
    }

    @Override
    public void deleteUtterance(String templateName, String utterance) {
        this.templates.get(templateName).deleteUtterance(utterance);
    }

    @Override
    public ObservableList<String> getUtterancesToTemplate(String templateName) {
        return this.templates.get(templateName).getUtterances();
    }

    @Override
    public void changeTemplateName(String currentTemplateName, String changedName) {
        if(this.templates.containsKey(changedName)){
            for(String value : this.templates.get(currentTemplateName).getUtterances()){
                this.templates.get(changedName).addUtterance(value);
            }

        }else{
            this.templates.put(changedName,templates.get(currentTemplateName));
            templateNames.add(changedName);

        }
        templates.remove(currentTemplateName);
        templateNames.remove(currentTemplateName);

    }
}
