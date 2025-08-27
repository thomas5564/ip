package avo.exceptions;

/**
 * Exception for when the user input is incomplete
 */
public class IncompleteInputException extends AvoException {
    public IncompleteInputException(String correctFormat) {
        super("Input must follow: " + correctFormat);
    }
}
