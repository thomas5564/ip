package avo.main;
import java.io.File;

import avo.storage.Storage;
import avo.tasks.TaskList;
import avo.ui.AvoSpeaker;


/**
 * Main file used to run Avo
 */
public class Avo {
    private String pathName = "data" + File.separator + "avo.txt";
    private Storage storage = new Storage(getPathName());
    private TaskList taskList = new TaskList(storage);
    private AvoSpeaker speaker = new AvoSpeaker(taskList);

    public String getPathName() {
        return pathName;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public Storage getStorage() {
        return storage;
    }

    public AvoSpeaker getSpeaker() {
        return speaker;
    }
}
