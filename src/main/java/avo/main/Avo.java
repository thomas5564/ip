package avo.main;
import avo.storage.Storage;
import avo.ui.Ui;
import avo.tasks.TaskList;

import java.io.File;

/**Main file used to run Avo
 *
 */
public class Avo {
    public static String pathName = "data" + File.separator + "avo.txt";
    public static TaskList taskList = new TaskList();
    public static Storage storage = new Storage(pathName);
    public static void main(String[] args){
        Ui.greet();
        Ui.uiLoop();
    }
}
