package avo.exceptions;

/**
 * Exception for invalid indexes
 */
public class InvalidIndexException extends AvoException {
    /**
     * Constructor for this exception
     * @param selectedIndex index selected
     * @param numberOfEntries number of entries in the task list
     */
    public InvalidIndexException(int selectedIndex, int numberOfEntries) {
        super(numberOfEntries == 0
                ? String.format("index %d does not exist, there are no more entries left!", selectedIndex + 1)
                : String.format("index %d does not exist, select a index from 1 to %d",
                selectedIndex + 1,
                numberOfEntries));
    }
}
