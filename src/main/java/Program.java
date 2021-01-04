import javafx.application.Application;;
import javafx.stage.Stage;

public class Program extends Application {
    static Stage stage;
    static MenuScene menuScene;
    static OptionsScene optionsScene;
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("CSV to LaTeX");

        menuScene = new MenuScene();
        menuScene.load(primaryStage);
        optionsScene = new OptionsScene();

        stage.setScene(menuScene);
        stage.show();
    }
}
