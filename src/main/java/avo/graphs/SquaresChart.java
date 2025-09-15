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
    private final TaskList taskList;
    private final Predicate<Task> condition;

    /**
     * constructor for this class
     * @param taskList that will produce that weekly tasks
     * @param condition on which to evaluate each item
     */
    public SquaresChart(TaskList taskList, Predicate<Task> condition) {
        super(5);
        this.taskList = taskList;
        this.condition = condition;
        addSquares(); // initial paint
    }

    @Override
    public void update() {
        this.getChildren().clear();
        addSquares();
    }

    private void addSquares() {
        // recompute directly from the live main list every time
        List<Task> weekly = taskList.getWeeklyTasks();
        System.out.println("Weekly count: " + weekly.size());
        for (Task item : weekly) {
            Rectangle square = new Rectangle(SIZE, SIZE);
            square.setFill(condition.test(item) ? Color.LIMEGREEN : Color.CRIMSON);
            square.setStroke(Color.BLACK);
            this.getChildren().add(square);
        }
    }
}
