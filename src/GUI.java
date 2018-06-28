import com.gluonhq.ignite.guice.GuiceContext;
import javafx.application.Application;
import javafx.stage.Stage;
import view.WindowManager;

import java.util.Arrays;

public class GUI extends Application {
    private GuiceContext guiceContext;
    @Override
    public void start(Stage primaryStage) throws Exception {
        guiceContext = new GuiceContext(this, ()-> Arrays.asList(new GuiConfig()));
        guiceContext.init();

        final WindowManager stageController = guiceContext.getInstance(WindowManager.class);
        stageController.switchScene(WindowManager.SCENES.START_SCENE);
    }

    public void run(String []args){
        launch(args);
    }

    public static void main(String []args){
        launch(args);
    }
}
