package rasaCore.model.domain;

import main.application.GUI;
import rasaCore.model.slot.*;
import rasaCore.model.template.Template;
import rasaCore.model.template.TemplateManager;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DomainGeneratorImpl implements DomainGenerator {
    SlotFactory factory;

    public DomainGeneratorImpl() {
        factory = new SlotFactory();
    }

    public Map<String, List<String>> generateEntities(DomainManager domainManager) {
        Map<String, List<String>> entities = new HashMap<>();
        entities.put("entities", domainManager.getEntities());
        return entities;
    }

    public Map<String, List<String>> generateIntents(DomainManager domainManager) {
        Map<String, List<String>> intents = new HashMap<>();
        intents.put("intents", domainManager.getIntents());
        return intents;
    }

    public Map<String, List<String>> generateActions(DomainManager domainManager, TemplateManager templateManager) {
        Map<String, List<String>> actions = new HashMap<>();
        List<String> actionList = new ArrayList<>();
        for(String customAction : domainManager.getCustomActions()){
            actionList.add("actions."+customAction);
        }
        actionList.addAll(templateManager.getTemplateNames());

        actions.put("actions", actionList);
        return actions;
    }

    public List<String> generateTemplates(TemplateManager manager) {

        List<String> result = new ArrayList<>();
        Map<String, Template> templates = manager.getTemplates();

        result.add("templates:\n");
        for (Map.Entry<String, Template> entry : templates.entrySet()) {
            result.add(" " + entry.getKey() + ":\n");
            for (String utterance : entry.getValue().getUtterances()) {
                result.add("  - \"" + utterance + "\"\n");
            }
        }

        return result;
    }

    public Map<String, Map<String, Map<String, Object>>> generateSlots(SlotManager slotManager) {
        Map<String, Map<String, Map<String, Object>>> rootSlot = new HashMap<>();
        Map<String, Map<String, Object>> slots = new HashMap<>();

        List<Slot> slotList = slotManager.getSlots();

        for (Slot slot : slotList) {
            String slotName = slot.getName();
            SLOTTYPE slotType = slot.getType();
            Map<String, Object> singleSlot = new HashMap<>();
            singleSlot.put("type", slotType.getType());
            if (slotType.equals(SLOTTYPE.CATEGORICAL)) {
                List<String> slotValues = new ArrayList<>(((CategoricalSlot) slot).getValues());
                singleSlot.put("values", slotValues);
            } else if (slotType.equals(SLOTTYPE.FLOAT)) {
                singleSlot.put("min_value", ((FloatSlot) slot).getMinValue());
                singleSlot.put("max_value", ((FloatSlot) slot).getMaxValue());
            }
            slots.put(slotName, singleSlot);
        }
        rootSlot.put("slots", slots);
        return rootSlot;
    }

    @Override
    public void fillManagers(String fileName, DomainManager domainManager, SlotManager slotManager, TemplateManager templateManager) {
        String filePath = GUI.getWorkSpace() + "\\" + fileName;
        Yaml yaml = new Yaml();
        try {
            InputStream input = new FileInputStream(new File(filePath));
            Map<String, Object> domainMap = yaml.load(input);
            for (Map.Entry<String, Object> entry : domainMap.entrySet()) {
                if (entry.getKey().equals("intents")) {
                    List<String> intents = (List<String>) entry.getValue();
                    if (intents != null) {
                        domainManager.setIntents(intents);
                    }

                } else if (entry.getKey().equals("entities")) {
                    List<String> entities = (List<String>) entry.getValue();
                    if (entities != null) {
                        domainManager.setEntities(entities);
                    }

                } else if (entry.getKey().equals("slots")) {
                    Map<String, Map<String, Object>> slots = (Map<String, Map<String, Object>>) entry.getValue();
                    if (slots != null) {
                        for (Map.Entry<String, Map<String, Object>> slot : slots.entrySet()) {
                            String slotName = slot.getKey();
                            String type = (String) slot.getValue().get("type");
                            if (type.equals("categorical")) {
                                List<String> values = (List<String>) slot.getValue().get("values");
                                CategoricalSlot categorySlot = (CategoricalSlot) factory.getSlot(slotName, SLOTTYPE.CATEGORICAL);
                                categorySlot.setValues(values);
                                slotManager.addSlot(categorySlot);
                            } else if (type.equals("float")) {
                                float minValue = Float.parseFloat(slot.getValue().get("min_value").toString());
                                float maxValue = Float.parseFloat(slot.getValue().get("max_value").toString());
                                FloatSlot floatSlot = (FloatSlot) factory.getSlot(slotName, SLOTTYPE.FLOAT);
                                floatSlot.setMinValue(minValue);
                                floatSlot.setMaxValue(maxValue);
                                slotManager.addSlot(floatSlot);
                            } else {
                                Slot commonSlot = factory.getSlot(slotName, SLOTTYPE.fromString(type));
                                slotManager.addSlot(commonSlot);
                            }
                        }
                    }
                } else if (entry.getKey().equals("templates")) {
                    Map<String, List<String>> templateMap = (Map<String, List<String>>) entry.getValue();
                    if (templateMap != null) {
                        for (Map.Entry<String, List<String>> template : templateMap.entrySet()) {
                            String templateName = template.getKey();
                            List<String> templates = template.getValue();
                            templateManager.addTemplate(templateName);
                            for (String utterance : templates) {
                                templateManager.addUtterance(templateName, utterance);
                            }
                        }
                    }
                    } else if (entry.getKey().equals("actions")) {
                        List<String> actions = (List<String>) entry.getValue();
                        if (actions != null) {
                            for (String action : actions) {
                                if (action.startsWith("actions.")) {
                                    String [] split = action.split("\\.");
                                    domainManager.addCustomAction(split[split.length-1]);
                                }
                            }
                        }
                    }

                }

            } catch(FileNotFoundException e){
                e.printStackTrace();
            }


        }
    }
