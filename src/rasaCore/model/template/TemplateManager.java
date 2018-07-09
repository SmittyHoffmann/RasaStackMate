package rasaCore.model.template;

import javafx.collections.ObservableList;

import java.util.HashMap;

public interface TemplateManager {

    void addUtterance(String templateName, String utterance);

    void addTemplate(String templateName);

    void deleteTemplate(String templateName);

    Template getTemplate(String templateName);

    ObservableList<String> getTemplateNames();

    HashMap<String,Template> getTemplates();

    void deleteUtterance(String templateName, String utterance);

    ObservableList<String> getUtterancesToTemplate(String templateName);

    void changeTemplateName(String currentTemplateName, String changedName);


}

