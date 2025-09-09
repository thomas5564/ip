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
import javafx.scene.layout.VBox;

/**
 * Contains charts to show insights
 */
public class ChartContainer extends VBox {
    private List<Updateable> graphs;
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
        SquaresChart squaresChart = new SquaresChart<>(
                taskList.getWeeklyTasks().getTasks(),
                Task::getIsDone
        );
        TaskPieChart pieChart = new TaskPieChart(taskList.getTaskLW());
        TaskBarChart barChart = new TaskBarChart(taskList);
        barChart.prefWidthProperty().bind(this.widthProperty());
        squaresChart.prefWidthProperty().bind(this.widthProperty());
        pieChart.prefWidthProperty().bind(this.widthProperty());
        this.graphs = new ArrayList<>();
        graphs.add(squaresChart);
        graphs.add(pieChart);
        graphs.add(barChart);
        barChart.setMinHeight(400);
        squaresContainer.getChildren().add(squaresChart);
        pieContainer.getChildren().add(pieChart);
        barContainer.getChildren().add(barChart);
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
