package avo.exceptions;

import java.time.LocalDate;

/**
 * Thrown when event start time is after event end time
 */
public class InvalidEventDateException extends AvoException {
    /**
     * Constructs a message telling the user he accidentally enters a
     * start time that is after the end time
     * @param startTime
     * @param endTime
     */
    public InvalidEventDateException(LocalDate startTime, LocalDate endTime) {
        super(String.format("No time travelling allowed!"
                + "\n event start (%s) is somehow after event end (%s)", startTime, endTime));
    }
}
