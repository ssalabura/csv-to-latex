import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MenuScene extends Scene {
    private static StackPane root = new StackPane();
    private static VBox vbox = new VBox(50);

    public void load(Stage stage) {
        Text title = new Text("CSV to LaTeX");
        title.setFont(Font.font(50));

        Button btnLoadFile = new Button("Load file...");
        btnLoadFile.setFont(Font.font(30));
        btnLoadFile.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load .csv file...");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV","*.csv"));
            File file = fileChooser.showOpenDialog(stage);
            if(file != null) {
                try {
                    Program.optionsScene.load(new CSVData(file));
                    stage.setScene(Program.optionsScene);
                } catch (IOException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                    e.printStackTrace();
                }
            }
        });

        Button btnExit = new Button("Exit");
        btnExit.setFont(Font.font(30));
        btnExit.setOnAction(event -> System.exit(0));

        vbox.getChildren().addAll(title, btnLoadFile, btnExit);
        vbox.setAlignment(Pos.CENTER);
        root.getChildren().add(vbox);
    }

    public MenuScene() {
        super(root, 800, 600);
    }
}
