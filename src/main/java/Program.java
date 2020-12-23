import javafx.application.Application;;
import javafx.stage.Stage;

public class Program extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("CSV to LaTeX");

        MenuScene menuScene = new MenuScene();
        menuScene.load(primaryStage);

        primaryStage.setScene(menuScene);
        primaryStage.show();
    }
}
