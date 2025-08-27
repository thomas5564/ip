package avo.exceptions;

public class EmptyInstructionException extends AvoException {
    public EmptyInstructionException() {
        super("OOPS!!! The instruction of a task cannot be empty.");
    }
}

