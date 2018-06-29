package main;

import com.google.inject.AbstractModule;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import model.entity.EntityManager;
import model.entity.EntityManagerImpl;
import model.intent.IntentManager;
import model.intent.IntentManagerImpl;
import model.regex.RegexManager;
import model.regex.RegexManagerImpl;
import model.synonym.SynonymManager;
import model.synonym.SynonymManagerImpl;

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




}