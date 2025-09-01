package avo.exceptions;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Exception thrown when date(s) are missing
 */
public class EmptyDateException extends AvoException {
    public EmptyDateException(String... nameOfDates) {
        super(buildMessage(nameOfDates));
    }

    private static String buildMessage(String... names) {
        if (names.length == 1) {
            return "Missing required date: " + names[0];
        } else {
            String joined = Arrays.stream(names)
                    .collect(Collectors.joining(", "));
            return "Missing required dates: " + joined;
        }
    }
}

