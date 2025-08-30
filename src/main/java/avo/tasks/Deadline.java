package avo.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class for deadlines
 */
public class Deadline extends Task {
    private LocalDate deadline;

    /**
     * Constructor for this class
     * @param instruction instruction for task
     * @param deadline date time object that represents deadline
     */
    public Deadline(String instruction, LocalDate deadline) {
        super(instruction);
        this.deadline = deadline;
    }
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        String formattedDate = deadline.format(formatter);
        String deadline = String.format("(by: %s)", formattedDate);
        return String.format("[D]%s%s", super.toString().substring(4), deadline);
    }

    /**
     * Produces string representation of deadline for storage
     * @return String representation of deadline in the storage file
     */
    @Override
    public String getStorageString() {
        return String.format("D|%s|%s",
                super.getStorageString().substring(2),
                deadline);
    }
}
