import javafx.application.Application;;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Program extends Application {
    static Stage stage;
    static MenuScene menuScene;
    static OptionsScene optionsScene;
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("CSV to LaTeX");
        stage.getIcons().add(new Image("icon.png"));

        menuScene = new MenuScene();
        menuScene.load(primaryStage);
        optionsScene = new OptionsScene();

        stage.setScene(menuScene);
        stage.show();
    }
}
