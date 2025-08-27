package avo.exceptions;

public class EmptySearchStringException extends AvoException {
    public EmptySearchStringException() {
        super("You have to be finding something :d");
    }
}
