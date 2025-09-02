package avo.graphs;

import java.util.List;
import java.util.function.Predicate;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * A row of squares representing items in a list.
 * Each square is filled green if it passes the condition,
 * red if it fails.
 */
public class SquaresChart<T> extends HBox {
    private static final int SIZE = 40;

    /**
     * Builds a line of squares and colours them in if the item they
     * represent returns true for a predicate
     * @param items
     * @param condition
     */
    public SquaresChart(List<T> items, Predicate<T> condition) {
        super(5); // spacing

        for (T item : items) {
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
