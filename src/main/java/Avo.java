import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Avo {
    //C:\Users\thoma\Downloads\ip\data\avo.txt
    //"data" + File.separator + "avo.txt"
    static String pathName = "C:\\Users\\thoma\\Downloads\\ip\\data\\avo.txt";
    private static Scanner fileScanner;
    private static File storageFile;
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
    public static void readFile(String pathName){
        try{
            System.out.println(String.format("checking %s",pathName));
            storageFile  = new File(pathName);
            fileScanner = new Scanner(storageFile);
            while (fileScanner.hasNext()) {
                Task nextTask;
                String nextEntry =  fileScanner.nextLine();
                String[] elements = nextEntry.split("\\|");
                char firstLetter = nextEntry.charAt(0);
                switch(firstLetter){
                    case 'T':
                        nextTask = Task.parseFromStorage(elements);
                        taskList.addTask(nextTask,true);
                        break;
                    case 'D':
                        nextTask = Deadline.parseFromStorage(elements);
                        taskList.addTask(nextTask,true);
                        break;
                    case 'E':
                        nextTask = Event.parseFromStorage(elements);
                        taskList.addTask(nextTask,true);
                        break;
                    default:
                        System.out.println("invalid entry!");
                }
            }
            fileScanner.close();
        }catch(FileNotFoundException e){
            System.out.println("File is not found. If you want your tasks to be saved,\n " +
                    "add the file and start the program again");
        }
    }

    static void appendToFile(String filePath, String textToAdd){
        try{
            FileWriter fw = new FileWriter(filePath, true); // create a FileWriter in append mode
            fw.write(textToAdd + "\n");
            fw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args){
        readFile(pathName);
        Ui.greet();
        Ui.uiLoop();
    }
}
