package main;

import com.google.inject.AbstractModule;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import model.fileHandling.RasaFileManager;
import model.fileHandling.RasaFileManagerImpl;
import model.rasaCore.DomainManager;
import model.rasaCore.DomainManagerImpl;
import model.rasaCore.slot.SlotManager;
import model.rasaCore.slot.SlotManagerImpl;
import model.rasaNLU.entity.EntityManager;
import model.rasaNLU.entity.EntityManagerImpl;
import model.rasaNLU.intent.IntentManager;
import model.rasaNLU.intent.IntentManagerImpl;
import model.rasaNLU.nluJSONData.NLUJSONGenerator;
import model.rasaNLU.nluJSONData.NLUTrainDataGenerator;
import model.rasaNLU.regex.RegexManager;
import model.rasaNLU.regex.RegexManagerImpl;
import model.rasaNLU.synonym.SynonymManager;
import model.rasaNLU.synonym.SynonymManagerImpl;

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
    }






