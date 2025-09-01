package avo.exceptions;

/**
 * Parent exception for all custom exceptions used in Avo
 */
public class AvoException extends Exception {
    public AvoException(String message) {
        super(message);
    }
}
