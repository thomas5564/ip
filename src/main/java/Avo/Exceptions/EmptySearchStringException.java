package Avo.Exceptions;

public class EmptySearchStringException extends AvoException {
    public EmptySearchStringException() {
        super("You have to be finding something :d");
    }
}
