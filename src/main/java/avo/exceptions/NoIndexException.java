package avo.exceptions;

/**
 * Exception for when the index is empty
 */
public class NoIndexException extends AvoException {
    public NoIndexException(int numberOfTasks) {
        super(String.format("Give me an index, pick an index from 1 to %d", numberOfTasks));
    }
}
