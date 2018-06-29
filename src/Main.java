import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import main.GUI;
import main.GuiConfig;

public class Main {

    public static void main(String [] args){

        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                install(new GuiConfig());
            }
        });


        GUI gui = injector.getInstance(GUI.class);

        try{
            gui.run(args);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
