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

        // overall chart height
        this.setPrefHeight(600.0);
        this.setMaxHeight(Double.MAX_VALUE);

        // Configure X axis
        CategoryAxis xAxis = (CategoryAxis) getXAxis();
        xAxis.setLabel("Week");
        xAxis.setTickLabelRotation(90);

        // Configure Y axis
        NumberAxis yAxis = (NumberAxis) getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);
        yAxis.setTickUnit(20);
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
