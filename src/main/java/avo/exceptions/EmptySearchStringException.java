package avo.exceptions;

/**
 * Exception for when the searched string is empty
 */
public class EmptySearchStringException extends AvoException {
    public EmptySearchStringException() {
        super("You have to be finding something :d");
    }
}
