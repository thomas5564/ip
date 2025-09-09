package avo.graphs;

import java.util.List;
import java.util.function.Predicate;

import avo.tasks.Task;
import avo.tasks.TaskList;
import avo.ui.Updateable;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * A row of squares representing items in a list.
 * Each square is filled green if it passes the condition,
 * red if it fails.
 */
public class SquaresChart extends HBox implements Updateable {
    private static final int SIZE = 10;
    private TaskList taskList;
    private List<Task> items;
    private Predicate<Task> condition;
    /**
     * Builds a line of squares and colours them in if the item they
     * represent returns true for a predicate
     * @param taskList to be presented
     * @param condition condition for them to pass the test
     */
    public SquaresChart(TaskList taskList, Predicate<Task> condition) {
        super(5); // spacing
        this.items = taskList.getTasks();
        this.condition = condition;
        this.taskList = taskList;
        addSquares();
    }

    @Override
    public void update() {
        this.getChildren().clear();
        addSquares();
    }

    /**
     * Adds squares to the chart
     */
    public void addSquares() {
        items = taskList.getWeeklyTasks().getTasks();
        for (Task item : items) {
            Rectangle square = new Rectangle(SIZE, SIZE);

            if (condition.test(item)) {
                square.setFill(Color.LIMEGREEN);
            } else {
                square.setFill(Color.CRIMSON);
            }
            square.setStroke(Color.BLACK); // outline
            this.getChildren().add(square);
        }
    }
}
