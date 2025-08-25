package main;
import storage.Storage;
import ui.Ui;
import tasks.TaskList;

import java.io.File;

public class Avo {
    //C:\Users\thoma\Downloads\ip\data\avo.txt
    public static String pathName = "data" + File.separator + "avo.txt";
    public static TaskList taskList = new TaskList();
    public static Storage storage = new Storage(pathName);
    public static void main(String[] args){
        Ui.greet();
        Ui.uiLoop();
    }
}
