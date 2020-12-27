import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class OptionsScene extends Scene {
    private static StackPane root = new StackPane();
    private static VBox vBox = new VBox(20);

    void load(CSVData csvData) {
        TableView<ArrayList<String>> tableView = new TableView<>();
        for(int i=0; i<csvData.getLabels().size(); i++) {
            TableColumn<ArrayList<String>, String> tableColumn = new TableColumn<>(csvData.getLabels().get(i));
            int finalI = i;
            tableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(finalI)));
            tableView.getColumns().add(tableColumn);
        }
        for(ArrayList<String> line : csvData.getData()) {
            tableView.getItems().add(line);
        }

        Button btnExit = new Button("Exit");
        btnExit.setFont(Font.font(30));
        btnExit.setOnAction(event -> Program.stage.setScene(Program.menuScene));

        vBox.getChildren().addAll(tableView, btnExit);
        vBox.setAlignment(Pos.CENTER);
        root.getChildren().add(vBox);
    }

    public OptionsScene() {
        super(root, 800, 600);
    }
}
