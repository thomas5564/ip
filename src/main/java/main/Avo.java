package main;
import storage.Storage;
import ui.Ui;
import tasks.TaskList;

import java.io.File;

/**Main file used to run Avo
 *
 */
public class Avo {
    public static String pathName = "data" + File.separator + "avo.txt";
    public static Storage storage = new Storage(pathName);
    public static TaskList taskList = new TaskList();
    public static void main(String[] args){
        Ui.greet();
        Ui.uiLoop();
    }
}
