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

        leftBox.getChildren().add(tableView);
        leftBox.setMinWidth(500);
        leftBox.setMaxWidth(500);
        leftBox.setAlignment(Pos.CENTER);

        VBox rightBox = new VBox(20);

        Text titleText = new Text("Title:");
        TextField titleTextField = new TextField(csvData.getFileName());

        HBox xBox = new HBox(10);
        Text xText = new Text("X coordinate:");
        ChoiceBox<String> xChoiceBox = new ChoiceBox<>();
        xBox.getChildren().addAll(xText, xChoiceBox);
        xBox.setAlignment(Pos.CENTER);

        HBox yBox = new HBox(10);
        Text yText = new Text("Y coordinate:");
        ChoiceBox<String> yChoiceBox = new ChoiceBox<>();
        yBox.getChildren().addAll(yText, yChoiceBox);
        yBox.setAlignment(Pos.CENTER);

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

        HBox colorBox = new HBox(10);
        Text colorText = new Text("Line color:");
        TextField colorTextField = new TextField("blue");
        colorTextField.setMaxWidth(100);
        colorBox.getChildren().addAll(colorText, colorTextField);
        colorBox.setAlignment(Pos.CENTER);

        HBox widthBox = new HBox(10);
        Text widthText = new Text("Line width:");
        ChoiceBox<String> widthChoiceBox = new ChoiceBox<>();
        widthChoiceBox.getItems().addAll("thin", "normal", "thick");
        if(csvData.getData().size() < 20) widthChoiceBox.setValue("thick");
        else if(csvData.getData().size() < 100) widthChoiceBox.setValue("normal");
        else widthChoiceBox.setValue("thin");
        widthBox.getChildren().addAll(widthText, widthChoiceBox);
        widthBox.setAlignment(Pos.CENTER);

        CheckBox markerCheckbox = new CheckBox("Markers");
        if(csvData.getData().size() < 100) markerCheckbox.setSelected(true);

        CheckBox overwriteCheckbox = new CheckBox("Overwrite file");
        overwriteCheckbox.setSelected(true);

        Button btnGenerate = new Button("Generate");
        btnGenerate.setFont(Font.font(30));
        btnGenerate.setOnAction(event -> {
            LatexPlot latexPlot = new LatexPlot(titleTextField.getText(),
                    xChoiceBox.getValue(), yChoiceBox.getValue(),
                    new PlotStyle(colorTextField.getText(), markerCheckbox.isSelected(), widthChoiceBox.getValue()),
                    csvData.makeCoordinates(xChoiceBox.getValue(), yChoiceBox.getValue()));
            try {
                File file = latexPlot.saveToFile(csvData.getFileName(), overwriteCheckbox.isSelected());
                new Alert(Alert.AlertType.INFORMATION, "Successfully saved to output/" + file.getName()).show();
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        });
        Button btnExit = new Button("Exit");
        btnExit.setFont(Font.font(20));
        btnExit.setOnAction(event -> Program.stage.setScene(Program.menuScene));

        rightBox.getChildren().addAll(
                titleText, titleTextField,
                xBox, yBox,
                colorBox,
                widthBox,
                markerCheckbox,
                overwriteCheckbox,
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
