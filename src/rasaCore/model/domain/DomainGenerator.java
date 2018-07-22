package rasaCore.model.domain;

import rasaCore.model.slot.SlotManager;
import rasaCore.model.template.TemplateManager;

import java.util.List;
import java.util.Map;

public interface DomainGenerator {
    Map<String, List<String>> generateEntities(DomainManager domainManager);

    Map<String, List<String>> generateIntents(DomainManager domainManager);

    Map<String, List<String>> generateActions(DomainManager domainManager, TemplateManager templateManager);

    List<String> generateTemplates(TemplateManager manager);

    Map<String, Map<String, Map<String, Object>>> generateSlots(SlotManager slotManager);

    void fillManagers(String fileName, DomainManager domainManager, SlotManager slotManager, TemplateManager templateManager);
}

