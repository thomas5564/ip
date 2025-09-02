package avo.tasks;

import java.time.LocalDate;

/**
 * Class to represent tasks
 */
public class Task {
    private LocalDate today = LocalDate.now();
    private final String instruction;
    private boolean isDone;

    /**
     * Constructor for this class
     *
     * @param instruction instruction for the class
     * @param dateCreated
     */
    public Task(String instruction, LocalDate dateCreated) {
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
        return String.format("T|%s|%s|%s", mark, instruction, today);
    }
    public String getInstruction() {
        return instruction;
    }
    public LocalDate getToday() {
        return today;
    }
}
