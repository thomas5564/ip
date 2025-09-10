package avo.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import avo.exceptions.EmptyDateException;
import avo.exceptions.EmptyInstructionException;
import avo.exceptions.IncompleteInputException;
import avo.exceptions.InvalidEventDateException;
import avo.main.Avo;
import avo.parser.Parser;
import avo.tasks.Deadline;
import avo.tasks.Event;
import avo.tasks.Task;
import avo.tasks.TaskList;

class StorageTest {
    private Path tempDir;
    private Path tempFile;
    private Storage storage;

    @BeforeEach
    void setUp() throws Exception {
        tempDir = Files.createTempDirectory("storagetest");
        tempFile = tempDir.resolve("test.txt");
        Files.createFile(tempFile);
        storage = new Storage(tempFile.toString());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walk(tempDir)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    void testConstructorCreatesScanner() throws Exception {
        assertNotNull(storage);
    }

    @Test
    void testAppendToFile() throws IOException {
        storage.appendToFile("Hello World");
        List<String> lines = Files.readAllLines(tempFile);
        assertTrue(lines.contains("Hello World"));
    }

    @Test
    void testRewriteFileFromList() throws IOException,
            EmptyInstructionException, EmptyDateException, IncompleteInputException, InvalidEventDateException {
        LocalDate today = LocalDate.now();
        Event event = Parser.parseEvent("event play games /from 2003-02-07 /to 2004-12-21");
        Deadline deadline = Parser.parseDeadline("deadline play games /by 2003-02-07");
        Task task = Parser.parseTask("todo play games");
        Avo avo = new Avo();
        TaskList taskList = new TaskList(storage, avo);
        taskList.addTask(event, false);
        taskList.addTask(deadline, false);
        taskList.addTask(task, false);
        storage.rewriteFileFromList(taskList.getTasks());
        List<String> lines = Files.readAllLines(tempFile);
        assertEquals(3, lines.size());
        assertTrue(lines.contains(String.format("T|-|play games|%s", today)));
        assertTrue(lines.contains(String.format("D|-|play games|%s|2003-02-07", today)));
        assertTrue(lines.contains(String.format("E|-|play games|%s|2003-02-07|2004-12-21", today)));
    }
}
