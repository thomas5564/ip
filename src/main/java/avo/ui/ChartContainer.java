package avo.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import avo.graphs.SquaresChart;
import avo.graphs.TaskBarChart;
import avo.graphs.TaskPieChart;
import avo.tasks.Task;
import avo.tasks.TaskList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

/**
 * Contains charts to show insights
 */
public class ChartContainer extends VBox {
    private List<Updateable> graphs;
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
        TaskPieChart pieChart = new TaskPieChart(taskList);
        TaskBarChart barChart = new TaskBarChart(taskList);
        barChart.prefWidthProperty().bind(this.widthProperty());
        squaresChart.prefWidthProperty().bind(this.widthProperty());
        pieChart.prefWidthProperty().bind(this.widthProperty());
        this.graphs = new ArrayList<>();
        graphs.add(squaresChart);
        graphs.add(pieChart);
        graphs.add(barChart);
        this.getChildren().addAll(
            squaresChart,
            pieChart,
            barChart
        );
    }

    /**
     * Updates the charts on
     */
    public void updateCharts() {
        for (Updateable u: graphs) {
            u.update();
        }
    }
}
