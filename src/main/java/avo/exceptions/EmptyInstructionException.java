package avo.exceptions;

/**
 * Exception for empty instructions
 */
public class EmptyInstructionException extends AvoException {
    public EmptyInstructionException() {
        super("OOPS!!! The instruction of a task cannot be empty.");
    }
}

