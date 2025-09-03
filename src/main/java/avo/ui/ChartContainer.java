package avo.ui;

import avo.graphs.SquaresChart;
import avo.graphs.TaskPieChart;
import avo.tasks.Task;
import avo.tasks.TaskList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Contains charts to show insights
 */
public class ChartContainer extends VBox {
    /**
     * Constructor for this class
     */
    public ChartContainer(TaskList taskList) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/ChartContainer.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SquaresChart squaresChart = new SquaresChart<>(taskList.getTasks(), Task::getIsDone);
        TaskPieChart pieChart = new TaskPieChart(taskList.getCompletionMap());
        squaresChart.prefWidthProperty().bind(this.widthProperty());
        pieChart.prefWidthProperty().bind(this.widthProperty());
        this.getChildren().addAll(
            squaresChart,
            pieChart
        );
    }
}
