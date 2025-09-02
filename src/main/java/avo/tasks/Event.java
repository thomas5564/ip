package avo.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class to represent tasks with a start and end time
 */
public class Event extends Task {
    private final LocalDate startTime;
    private final LocalDate endTime;

    /**
     * Constructor for this class
     *
     * @param instruction instruction for event
     * @param startTime   starting time for the event
     * @param endTime     ending time for the event
     * @param dateCreated
     */
    public Event(String instruction, LocalDate startTime, LocalDate endTime, LocalDate dateCreated) {
        super(instruction, dateCreated);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        String formattedStartTime = startTime.format(formatter);
        String formattedEndTime = endTime.format(formatter);
        String duration = String.format("(from: %s to: %s)", formattedStartTime, formattedEndTime);
        return String.format("[E]%s%s", super.toString().substring(3), duration);
    }

    /**
     * Produces string representation of event for storage
     * @return String representation of event in the storage file
     */
    @Override
    public String getStorageString() {
        return String.format("E|%s|%s|%s|%s",
                super.getStorageString().substring(2),
                startTime,
                endTime,
                getToday());
    }
}
