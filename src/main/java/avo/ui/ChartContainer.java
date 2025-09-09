package avo.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import avo.graphs.SquaresChart;
import avo.graphs.TaskBarChart;
import avo.graphs.TaskPieChart;
import avo.tasks.Task;
import avo.tasks.TaskList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Contains charts to show insights
 */
public class ChartContainer extends VBox {
    private final List<Updateable> graphs = new ArrayList<>();

    @FXML
    private VBox squaresContainer;
    @FXML
    private VBox pieContainer;
    @FXML
    private VBox barContainer;

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

        addBarChart(taskList);
        addPieChart(taskList);
        addSquaresChart(taskList);
    }

    /**
     * Updates all charts
     */
    public void updateCharts() {
        for (Updateable u : graphs) {
            u.update();
        }
    }

    /**
     * Adds the pie chart showing the completion rate of last week's tasks
     * @param taskList of all tasks
     */
    public void addPieChart(TaskList taskList) {
        if (taskList.getTaskLW().isEmpty()) {
            Label noTasksLabel = new Label("no tasks added last week");
            noTasksLabel.getStyleClass().add("no-tasks");
            pieContainer.getChildren().add(noTasksLabel);
            return;
        }

        TaskPieChart pieChart = new TaskPieChart(taskList);
        pieChart.prefWidthProperty().bind(this.widthProperty());
        graphs.add(pieChart);
        pieContainer.getChildren().add(pieChart);
    }

    /**
     * Adds the squares chart showing the completion rate of this week's tasks
     * @param taskList of all tasks
     */
    public void addSquaresChart(TaskList taskList) {
        if (taskList.getWeeklyTasks().isEmpty()) {
            Label noTasksLabel = new Label("no tasks added this week");
            noTasksLabel.getStyleClass().add("no-tasks");
            squaresContainer.getChildren().add(noTasksLabel);
            return;
        }

        SquaresChart squaresChart = new SquaresChart(
                taskList,
                Task::getIsDone
        );
        squaresChart.prefWidthProperty().bind(this.widthProperty());
        graphs.add(squaresChart);
        squaresContainer.getChildren().add(squaresChart);
    }

    /**
     * Adds the bar chart showing the completion rate for the past 4 weeks
     * @param taskList of all tasks
     */
    public void addBarChart(TaskList taskList) {
        if (taskList.getFinishRateMap().isEmpty()) {
            Label noTasksLabel = new Label("no tasks added in the last 4 weeks");
            noTasksLabel.getStyleClass().add("no-tasks");
            barContainer.getChildren().add(noTasksLabel);
            return;
        }

        TaskBarChart barChart = new TaskBarChart(taskList);
        barChart.prefWidthProperty().bind(this.widthProperty());
        barChart.setMinHeight(400);
        graphs.add(barChart);
        barContainer.getChildren().add(barChart);
    }
}
