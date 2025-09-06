package avo.graphs;

import avo.tasks.TaskList;
import avo.ui.Updateable;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;

import java.util.Map;

/**
 * Bar chart that shows finish rate on each week
 */
public class TaskBarChart extends BarChart implements Updateable {
    private TaskList taskList;
    /**
     * @param axis label for an axis
     * @param axis1 label for another axis
     * @param taskList tasklist of all tasks
     */
    public TaskBarChart(Axis axis, Axis axis1, TaskList taskList) {
        super(axis, axis1);
        this.taskList = taskList;
    }

    @Override
    public void update() {
        Platform.runLater(() -> {
            Map<String, Double> finishRateMap = taskList.getFinishRateMap();

            // Create a new series for the bar chart
            Series<String, Number> series = new Series<>();
            series.setName("Weekly Finish Rate");

            // Add data points (week → rate)
            finishRateMap.forEach((week, rate) -> {
                series.getData().add(new Data<>(week, rate * 100)); // as percentage
            });

            this.getData().clear();
            this.getData().add(series);
        });
    }
}
