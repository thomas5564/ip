package avo.exceptions;

public class NoIndexException extends AvoException {
    public NoIndexException(int numberOfTasks) {
        super(String.format("Give me an index, pick an index from 1 to %d",numberOfTasks));
    }
}
