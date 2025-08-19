package main;
import Storage.Storage;
import Ui.Ui;
import Tasks.TaskList;

public class Avo {
    //C:\Users\thoma\Downloads\ip\data\avo.txt
    //"data" + File.separator + "avo.txt"
    public static String pathName = "C:\\Users\\thoma\\Downloads\\ip\\data\\avo.txt";
    public static Storage storage = new Storage(pathName);
    public static TaskList taskList = new TaskList();
    public static void main(String[] args){
        storage.readFile();
        Ui.greet();
        Ui.uiLoop();
    }
}
