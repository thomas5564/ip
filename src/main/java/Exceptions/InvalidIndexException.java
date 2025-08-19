package Exceptions;

public class InvalidIndexException extends Exception {
    public InvalidIndexException(int selectedIndex, int numberOfEntries) {
        super(numberOfEntries == 0
                ? String.format("index %d does not exist, there are no more entries left!",selectedIndex)
                :String.format("index %d does not exist, select a index from 1 to %d",selectedIndex,numberOfEntries));
    }
}
