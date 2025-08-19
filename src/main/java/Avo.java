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

    public static String excludeFirstWord(String input){
        int firstSpace = input.indexOf(" ");
        String result = input.substring(firstSpace + 1);
        return result;
    }
    public static Command parseCommand(String input) throws UnknownCommandException {
        try {
            return Command.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnknownCommandException();
        }
    }

    public static void main(String[] args){
        storage.readFile();
        Ui.greet();
        Ui.uiLoop();
    }
}
