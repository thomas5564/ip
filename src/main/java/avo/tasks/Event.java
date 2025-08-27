package avo.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private final LocalDate startTime;
    private final LocalDate endTime;
    public Event(String instruction,LocalDate startTime,LocalDate endTime) {
        super(instruction);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        String formattedStartTime = startTime.format(formatter);
        String formattedEndTime = endTime.format(formatter);
        String duration = String.format("(from: %s to: %s)",formattedStartTime,formattedEndTime);
        return String.format("[E]%s%s",super.toString(),duration);
    }

    /**
     *
     * @return String representation of event in the storage file
     */
    @Override
    public String getStorageString(){
        return String.format("E|%s|%s|%s",super.getStorageString().substring(2),
                startTime,
                endTime);
    }
}
