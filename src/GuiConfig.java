import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import model.IntentManager;
import model.IntentManagerImpl;

public class GuiConfig extends AbstractModule {




    @Override
    protected void configure() {

    }

    @Provides
    IntentManager provideIntentManager(){
        IntentManager manager = new IntentManagerImpl();
        return manager;
    }
}
