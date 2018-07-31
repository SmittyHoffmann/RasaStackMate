package main.application;

import com.google.inject.AbstractModule;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import main.fileHandling.RasaFileManager;
import main.fileHandling.RasaFileManagerImpl;
import rasaCore.graph.Graph;
import rasaCore.graph.GraphModel;
import rasaCore.model.domain.DomainGenerator;
import rasaCore.model.domain.DomainGeneratorImpl;
import rasaCore.model.domain.DomainManager;
import rasaCore.model.domain.DomainManagerImpl;
import rasaCore.model.slot.SlotFactory;
import rasaCore.model.slot.SlotManager;
import rasaCore.model.slot.SlotManagerImpl;
import rasaCore.model.template.TemplateManager;
import rasaCore.model.template.TemplateManagerImpl;
import rasaCore.view.story.StoryGenerator;
import rasaCore.view.story.StoryGeneratorImpl;
import rasaNLU.model.entity.EntityManager;
import rasaNLU.model.entity.EntityManagerImpl;
import rasaNLU.model.intent.IntentManager;
import rasaNLU.model.intent.IntentManagerImpl;
import rasaNLU.model.nluJSONData.NLUJSONGenerator;
import rasaNLU.model.nluJSONData.NLUTrainDataGenerator;
import rasaNLU.model.regex.RegexManager;
import rasaNLU.model.regex.RegexManagerImpl;
import rasaNLU.model.synonym.SynonymManager;
import rasaNLU.model.synonym.SynonymManagerImpl;

public class GuiConfig extends AbstractModule {




    @Override
    protected void configure() {
       
    }

    @Provides @Singleton
    IntentManager provideIntentManager(){
        IntentManager manager = new IntentManagerImpl();
        return manager;
    }

    @Provides @Singleton
    EntityManager provideEntityManager(){
        EntityManager manager = new EntityManagerImpl();
        return manager;
    }

    @Provides @Singleton
    SynonymManager provideSynonymManager(){
        SynonymManager manager = new SynonymManagerImpl();
        return manager;
    }
    @Provides @Singleton
    RegexManager provideRegexManager(){
        RegexManager manager = new RegexManagerImpl();
        return manager;
    }

    @Provides @Singleton
    RasaFileManager provideRasaFileManager(){
        RasaFileManager manager = new RasaFileManagerImpl();
        return manager;
    }

    @Provides
    NLUTrainDataGenerator provideNLUTrainDataGenerator(){
        NLUTrainDataGenerator generator = new NLUJSONGenerator();
        return generator;
    }


    @Provides @Singleton
    DomainManager provideDomainManager(){
        DomainManager manager = new DomainManagerImpl();
        return manager;
    }
    @Provides @Singleton
    SlotManager provideSlotManager(){
        SlotManager manager = new SlotManagerImpl();
            return manager;
        }

    @Provides
    SlotFactory provideSlotFactory(){
        SlotFactory factory = new SlotFactory();
        return factory;
    }

    @Provides @Singleton
    TemplateManager provideTemplateManager(){
        TemplateManager manager = new TemplateManagerImpl();
        return manager;
    }

    @Provides
    DomainGenerator provideDomainGenerator(){
        DomainGenerator generator = new DomainGeneratorImpl();
        return generator;
    }

    @Provides @Singleton
    Graph provideGraph(){
        Graph graph = new Graph();
        return graph;
    }

    @Provides
    StoryGenerator provideStoryGenerator(){
        StoryGenerator generator = new StoryGeneratorImpl();
        return generator;
    }

    }







