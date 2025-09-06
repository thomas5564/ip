package avo.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import avo.exceptions.EmptyDateException;
import avo.exceptions.EmptyInstructionException;
import avo.exceptions.IncompleteInputException;
import avo.tasks.Deadline;
import avo.tasks.Event;
import avo.tasks.Task;

public class ParserTest {
    @Test
    public void tasksParserTest() throws EmptyInstructionException, IncompleteInputException, EmptyDateException {
        String today = LocalDate.now().toString();

        Event event = Parser.parseEvent("event play games /from 2003-02-07 /to 2004-12-21");
        // Actual order from Event.getStorageString():
        // E|mark|instruction|dateCreated|start|end
        assertEquals(
                String.format("E|-|play games|%s|2003-02-07|2004-12-21", today),
                event.getStorageString()
        );

        Deadline deadline = Parser.parseDeadline("deadline play games /by 2003-02-07");
        // D|mark|instruction|dueDate|dateCreated
        assertEquals(
                String.format("D|-|play games|2025-09-06|2003-02-07", today),
                deadline.getStorageString()
        );

        Task task = Parser.parseTask("todo play games");
        // T|mark|instruction|dateCreated
        assertEquals(
                String.format("T|-|play games|%s", today),
                task.getStorageString()
        );
    }

}

