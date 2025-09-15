package avo.graphs;
import java.util.Map;

import avo.tasks.TaskList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.chart.PieChart;

/**
 * A PieChart that builds slices from the task list. It shows
 * the proportion of tasks done last week out of all the tasks that were created last week.
 * Note: deleting a task made last week will cause the chart to adjust accordingly
 */
public class TaskPieChart extends PieChart {
    private TaskList taskList;
    private Map<String, ? extends Number> dataMap;
    /**
     * Constructor for this class
     * @param taskList to provide hashmap of values
     */
    public TaskPieChart(TaskList taskList) {
        super();
        this.taskList = taskList;
        this.dataMap = taskList.getLastWeekCompletionMap();
        update();
    }

    /**
     * Update the chart
     */
    public void update() {
        Platform.runLater(() -> {
            dataMap = taskList.getTaskLW().getLastWeekCompletionMap();
            var pieData = FXCollections.<PieChart.Data>observableArrayList();
            dataMap.forEach((label, value) ->
                    pieData.add(new PieChart.Data(label, value.doubleValue())));
            setData(pieData);
        });
    }

}
