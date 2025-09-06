package avo.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TaskTest {
    @Test
    public void storageStringTest() {
        LocalDate dateCreated = LocalDate.parse("2004-12-21");

        Event event = new Event("play games",
                LocalDate.parse("2003-02-07"),
                LocalDate.parse("2004-12-21"),
                dateCreated);
        assertEquals("E|-|play games|2004-12-21|2003-02-07|2004-12-21",
                event.getStorageString());

        Deadline deadline = new Deadline("play games",
                LocalDate.parse("2003-02-07"), dateCreated);
        assertEquals("D|-|play games|2004-12-21|2003-02-07",
                deadline.getStorageString());

        Task task = new Task("play games", dateCreated);
        assertEquals("T|-|play games|2004-12-21",
                task.getStorageString());
    }
}
