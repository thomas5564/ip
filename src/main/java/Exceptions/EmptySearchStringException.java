package Exceptions;

public class EmptySearchStringException extends Exception {
    public EmptySearchStringException() {
        super("You have to be finding something :d");
    }
}
