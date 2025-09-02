package avo.graphs;

import avo.tasks.Task;
import avo.tasks.TaskList;
import javafx.collections.FXCollections;
import javafx.scene.chart.PieChart;

import java.time.LocalDate;
import java.util.function.Function;

public class TaskPieChart extends PieChart {

    /**
     * @param tasks         your TaskList
     * @param dateSelector  which date to use for filtering into the week window
     *                      e.g. Task::getCompletedAt, Task::getLastModifiedDate, Task::getDate
     */
    public TaskPieChart(TaskList tasks, Function<Task, LocalDate> dateSelector) {
        super();
        updateChart(tasks, dateSelector);
        setTitle("Tasks this week");
    }

    /**
     * update chart with the correct values
     * @param tasks tasklist
     * @param dateSelector date selector
     */
    public void updateChart(TaskList tasks, Function<Task, LocalDate> dateSelector) {
        long done = tasks.getNumberDone();
        long notDone = tasks.length() - tasks.getNumberDone();
        setData(FXCollections.observableArrayList(
                new PieChart.Data("Done", done),
                new PieChart.Data("Not Done", notDone)
        ));
    }
}
