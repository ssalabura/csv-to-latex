import javafx.application.Application;;
import javafx.stage.Stage;

public class Program extends Application {
    static Stage stage;
    static MenuScene menuScene;
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("CSV to LaTeX");

        menuScene = new MenuScene();
        menuScene.load(primaryStage);

        stage.setScene(menuScene);
        stage.show();
    }
}
