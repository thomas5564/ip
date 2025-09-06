package avo.graphs;

import java.util.Map;

import avo.tasks.TaskList;
import avo.ui.Updateable;
import javafx.application.Platform;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

/**
 * Bar chart that shows finish rate on each week
 */
public class TaskBarChart extends BarChart<String, Number> implements Updateable {
    private TaskList taskList;
    /**
     * @param taskList tasklist of all tasks
     */
    public TaskBarChart(TaskList taskList) {
        super(new CategoryAxis(), new NumberAxis());
        this.taskList = taskList;

        // Configure axes
        CategoryAxis xAxis = (CategoryAxis) getXAxis();
        xAxis.setLabel("Week");

        NumberAxis yAxis = (NumberAxis) getYAxis();
        yAxis.setLabel("Finish rate (%)");
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis, null, "%"));
        update();
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
