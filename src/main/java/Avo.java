import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Avo {
    //C:\Users\thoma\Downloads\ip\data\avo.txt
    //"data" + File.separator + "avo.txt"
    static String pathName = "C:\\Users\\thoma\\Downloads\\ip\\data\\avo.txt";
    public static Storage storage = new Storage(pathName);
    public static TaskList taskList = new TaskList();
    public static void main(String[] args){
        storage.readFile();
        Ui.greet();
        Ui.uiLoop();
    }
}
