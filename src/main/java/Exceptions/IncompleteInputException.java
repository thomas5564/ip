package Exceptions;

public class IncompleteInputException extends RuntimeException {
  public IncompleteInputException(String correctFormat) {
    super("Input must follow: "+correctFormat);
  }
}
