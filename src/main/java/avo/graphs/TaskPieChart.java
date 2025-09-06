package avo.graphs;
import java.util.Map;

import avo.tasks.TaskList;
import avo.ui.Updateable;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.chart.PieChart;

/**
 * A generic PieChart that builds slices from the task list
 */
public class TaskPieChart extends PieChart implements Updateable {
    private TaskList taskList;
    private Map<String, ? extends Number> dataMap;
    /**
     * Constructor for this class
     * @param taskList to provide hashmap of values
     */
    public TaskPieChart(TaskList taskList) {
        super();
        this.taskList = taskList;
        this.dataMap = taskList.getCompletionMap();
        update();
    }
    @Override
    public void update() {
        Platform.runLater(() -> {
            dataMap = taskList.getCompletionMap();
            var pieData = FXCollections.<PieChart.Data>observableArrayList();
            dataMap.forEach((label, value) ->
                    pieData.add(new PieChart.Data(label, value.doubleValue())));
            setData(pieData);
        });
    }

}
