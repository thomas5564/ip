package Exceptions;

public class UnknownCommandException extends AvoException {
    public UnknownCommandException() {
        super("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }
}
