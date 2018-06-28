import com.google.inject.AbstractModule;

import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import controller.MainViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import model.*;

import javax.inject.Inject;
import javax.inject.Provider;
import java.io.IOException;

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






}
