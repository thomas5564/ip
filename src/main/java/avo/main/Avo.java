package avo.main;
import java.io.File;

import avo.storage.Storage;
import avo.tasks.TaskList;
import avo.ui.AvoSpeaker;


/**
 * Main file used to run Avo
 */
public class Avo {
    private static String pathName = "data" + File.separator + "avo.txt";
    private static TaskList taskList = new TaskList();
    private static Storage storage = new Storage(getPathName());
    public static void main(String[] args) {
        AvoSpeaker.greet();
        AvoSpeaker.uiLoop();
    }

    public static String getPathName() {
        return pathName;
    }

    public static TaskList getTaskList() {
        return taskList;
    }

    public static Storage getStorage() {
        return storage;
    }
}
