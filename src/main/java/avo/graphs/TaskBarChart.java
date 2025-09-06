package avo.graphs;

import avo.tasks.TaskList;
import avo.ui.Updateable;
import javafx.collections.FXCollections;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;

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
        var pieData = FXCollections.<PieChart.Data>observableArrayList();
    }
}
