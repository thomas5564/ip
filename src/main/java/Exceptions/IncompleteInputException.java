package Exceptions;

public class IncompleteInputException extends AvoException {
  public IncompleteInputException(String correctFormat) {
    super("Input must follow: "+correctFormat);
  }
}
