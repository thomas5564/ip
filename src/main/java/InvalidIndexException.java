public class InvalidIndexException extends Exception {
    public InvalidIndexException(int selectedIndex, int numberOfEntries) {
        super(String.format("index %d does not exist, select a index from 1 to %d",selectedIndex,numberOfEntries-1));
    }
}
