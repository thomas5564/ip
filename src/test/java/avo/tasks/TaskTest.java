package avo.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;


public class TaskTest {
    @Test
    public void storageStringTest() {
        Event event = new Event("play games",
                LocalDate.parse("2003-02-07"),
                LocalDate.parse("2004-12-21"), dateCreated);
        assertEquals(event.getStorageString(), "E|o|play games|2003-02-07|2004-12-21");
        Deadline deadline = new Deadline("play games",
                LocalDate.parse("2003-02-07"), dateCreated);
        assertEquals(deadline.getStorageString(), "D|o|play games|2003-02-07");
        Task task = new Task("play games", dateCreated);
        assertEquals(task.getStorageString(), "T|o|play games");
    }

}
