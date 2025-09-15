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
    private final List<Updateable> updateables = new ArrayList<>();
    private TaskList taskList;
    @FXML
    private VBox squaresContainer;
    @FXML
    private VBox pieContainer;
    @FXML
    private VBox barContainer;
    private TaskBarChart barChart;
    private TaskPieChart pieChart;

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
        this.taskList = taskList;
        updateBarContainer();
        updatePieContainer();
        updateSquaresContainer();
    }

    /**
     * updates the charts containing data from previous weeks
     */
    public void updatePastDataCharts() {
        pieChart.update();
        barChart.update();
    }

    /**
     * Adds the pie chart showing the completion rate of last week's tasks
     */
    public void updatePieContainer() {
        if (taskList.getTaskLW().isEmpty()) {
            Label noTasksLabel = new Label("no tasks added last week");
            noTasksLabel.getStyleClass().add("no-tasks");
            pieContainer.getChildren().add(noTasksLabel);
            return;
        }
        pieChart = new TaskPieChart(taskList);
        pieChart.prefWidthProperty().bind(this.widthProperty());
        pieContainer.getChildren().add(pieChart);
    }

    /**
     * Adds the squares chart showing the completion rate of this week's tasks
     */
    public void updateSquaresContainer() {
        SquaresChart squaresChart = new SquaresChart(
                taskList,
                Task::getIsDone
        );
        updateables.add(squaresChart);
        if (taskList.getWeeklyTasks().isEmpty()) {
            squaresContainer.getChildren().clear();
            Label noTasksLabel = new Label("no tasks added this week");
            noTasksLabel.getStyleClass().add("no-tasks");
            squaresContainer.getChildren().add(noTasksLabel);
        } else {
            squaresContainer.getChildren().clear();
            squaresContainer.getChildren().add(squaresChart);
            squaresChart.prefWidthProperty().bind(this.widthProperty());
        }
    }

    /**
     * Adds the bar chart showing the completion rate for the past 4 weeks
     */
    public void updateBarContainer() {
        if (taskList.getFinishRateMap().isEmpty()) {
            Label noTasksLabel = new Label("no tasks added in the last 4 weeks");
            noTasksLabel.getStyleClass().add("no-tasks");
            barContainer.getChildren().add(noTasksLabel);
            return;
        }

        barChart = new TaskBarChart(taskList);
        barChart.prefWidthProperty().bind(this.widthProperty());
        barChart.setMinHeight(400);
        barContainer.getChildren().add(barChart);
    }
}
