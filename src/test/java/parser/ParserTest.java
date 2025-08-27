package parser;

import avo.exceptions.EmptyInstructionException;
import avo.exceptions.IncompleteInputException;
import avo.parser.Parser;
import org.junit.jupiter.api.Test;
import avo.tasks.Deadline;
import avo.tasks.Event;
import avo.tasks.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    public void TasksParserTest() throws EmptyInstructionException, IncompleteInputException {
        Event event = Parser.parseEvent("event play games /from 2003-02-07 /to 2004-12-21");
        assertEquals(event.getStorageString(), "E|o|play games|2003-02-07|2004-12-21");
        Deadline deadline = Parser.parseDeadline("deadline play games /by 2003-02-07");
        assertEquals(deadline.getStorageString(), "D|o|play games|2003-02-07");
        Task task = Parser.parseTask("todo play games");
        assertEquals(task.getStorageString(), "T|o|play games");
    }
}
