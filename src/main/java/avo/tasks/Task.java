package avo.tasks;

/**
 * Class to represent tasks
 */
public class Task {
    private final String instruction;
    private boolean isDone;

    /**
     * Constructor for this class
     * @param instruction instruction for the class
     */
    public Task(String instruction) {
        this.isDone = false;
        this.instruction = instruction;
    }
    public void mark() {
        isDone = true;
    }
    public void unmark() {
        isDone = false;
    }
    @Override
    public String toString() {
        String mark = isDone ? "[x]" : "[ ]";
        return String.format("[T]%s%s", mark, instruction);
    }

    /**
     * Produces string representation of task for storage
     * @return String representation of to-do task in the storage file
     */
    public String getStorageString() {
        String mark = isDone ? "x" : "o";
        return String.format("T|%s|%s", mark, instruction);
    }
    public String getInstruction() {
        return instruction;
    }
}
