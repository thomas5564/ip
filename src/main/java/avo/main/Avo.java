package avo.main;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import avo.storage.Storage;
import avo.tasks.TaskList;
import avo.ui.AvoSpeaker;


/**
 * Main file used to run Avo
 */
public class Avo {
    private static final String PATH_NAME = "data" + File.separator + "avo.txt";
    private Storage storage;
    private TaskList taskList;
    private AvoSpeaker speaker;

    /**
     * Constructor for this class. Catches potential errors that could be
     * thrown when a new data file is made
     */
    public Avo() {
        try {
            this.storage = new Storage(PATH_NAME);
            this.taskList = new TaskList(storage);
            this.speaker = new AvoSpeaker(taskList);
        } catch (IOException e) {
            System.err.println("Could not resolve JAR location: " + e.getMessage());
        } catch (URISyntaxException e) {
            System.err.println("I/O error while creating file: " + e.getMessage());
        }
    }

    public AvoSpeaker getSpeaker() {
        return speaker;
    }
    public TaskList getTaskList() {
        return taskList;
    }
}
