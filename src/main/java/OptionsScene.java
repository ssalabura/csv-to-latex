import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class OptionsScene extends Scene {
    private static StackPane root = new StackPane();

    void load(CSVData csvData) {
        root.getChildren().clear();
        HBox hBox = new HBox(20);
        VBox leftBox = new VBox(20);
        VBox rightBox = new VBox(20);

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

        Text titleText = new Text("Title:");
        TextField titleTextField = new TextField(csvData.getFileName());

        Text xText = new Text("x Label:");
        ChoiceBox<String> xChoiceBox = new ChoiceBox<>();

        Text yText = new Text("y Label:");
        ChoiceBox<String> yChoiceBox = new ChoiceBox<>();

        for(String column : csvData.getLabels()) {
            if(csvData.isColumnParseable(column)) {
                xChoiceBox.getItems().add(column);
                yChoiceBox.getItems().add(column);
            }
        }
        try {
            xChoiceBox.setValue(xChoiceBox.getItems().get(0));
            yChoiceBox.setValue(yChoiceBox.getItems().get(1));
        } catch(IndexOutOfBoundsException ignored) { }

        CheckBox overwriteCheckbox = new CheckBox("Overwrite file");
        overwriteCheckbox.setSelected(true);

        Text todo = new Text("TODO: some other options");

        Button btnGenerate = new Button("Generate");
        btnGenerate.setFont(Font.font(30));
        btnGenerate.setOnAction(event -> {
            LatexPlot latexPlot = new LatexPlot(titleTextField.getText(), xChoiceBox.getValue(), yChoiceBox.getValue(), csvData.makeCoordinates(xChoiceBox.getValue(), yChoiceBox.getValue()));
            try {
                File file = latexPlot.saveToFile(csvData.getFileName(), overwriteCheckbox.isSelected());
                new Alert(Alert.AlertType.CONFIRMATION, "Successfully saved to " + file.getName()).show();
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        });
        Button btnExit = new Button("Exit");
        btnExit.setFont(Font.font(20));
        btnExit.setOnAction(event -> Program.stage.setScene(Program.menuScene));

        leftBox.getChildren().add(tableView);
        leftBox.setMinWidth(500);
        leftBox.setMaxWidth(500);
        leftBox.setAlignment(Pos.CENTER);
        rightBox.getChildren().addAll(
                titleText, titleTextField,
                xText, xChoiceBox,
                yText, yChoiceBox,
                overwriteCheckbox,
                todo,
                btnGenerate, btnExit);
        rightBox.setAlignment(Pos.CENTER);

        hBox.getChildren().addAll(leftBox, rightBox);
        hBox.setAlignment(Pos.CENTER);
        root.getChildren().add(hBox);
    }

    public OptionsScene() {
        super(root, 800, 600);
    }
}
