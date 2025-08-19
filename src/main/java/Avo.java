import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Avo {
    private static String pathName = "data" + File.separator + "avo.txt";
    private static Scanner fileScanner;
    private static File storageFile;
    private static int numberOfTasks = 0;
    static Task[] tasks = new Task[100];
    public static String listString(Task[] tasks){
        StringBuilder listString = new StringBuilder();
        for(int i = 0; i< tasks.length; i++){
            if(tasks[i] == null){
                break;
            }
            String line = "\n         " + (i+1) + "."+ tasks[i].toString();
            listString.append(line);
        }
        return listString.toString();
    }
    public static void unmark(int index) throws InvalidIndexException {
        if(index > numberOfTasks-1 || index<0){
            throw new InvalidIndexException(index,numberOfTasks);
        }else{
            String output;
            tasks[index].unmark();
            output = "OK, I've marked this task as not done yet:"+listString(tasks);
            Ui.respond(output);
        }
    }
    public static void deleteTask(int index) throws InvalidIndexException {
        if (index >= numberOfTasks || index < 0) {
            throw new InvalidIndexException(index + 1, numberOfTasks);
        }
        Task selectedTask = tasks[index];
        for (int i = index; i < numberOfTasks - 1; i++) {
            tasks[i] = tasks[i + 1];
        }
        tasks[numberOfTasks - 1] = null;
        numberOfTasks--;
        String fullResponse = "Noted. I've removed this task:\n "
                + "         "
                + selectedTask.toString()
                + String.format("\n         Now you have %d tasks in the list.", numberOfTasks);
        Ui.respond(fullResponse);
    }

    public static void mark(int index) throws InvalidIndexException {
        if(index > numberOfTasks-1 || index<0){
            throw new InvalidIndexException(index,numberOfTasks);
        }else{
            String output;
            tasks[index].mark();
            output = "Nice! I've marked this task as done:"+listString(tasks);
            Ui.respond(output);
        }
    }
    public static void addToList(Task currentTask){
        tasks[numberOfTasks] = currentTask;
        String fullResponse = "Got it. I've added this task:\n "
                + "         "
                + currentTask.toString()
                + String.format("\n         Now you have %d tasks in the list.", numberOfTasks + 1);
        Ui.respond(fullResponse);
        numberOfTasks++;
        appendToFile(pathName, currentTask.getStorageString());
    }

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
                        tasks[numberOfTasks] = nextTask;
                        numberOfTasks++;
                        break;
                    case 'D':
                        nextTask = Deadline.parseFromStorage(elements);
                        tasks[numberOfTasks] = nextTask;
                        numberOfTasks++;
                        break;
                    case 'E':
                        nextTask = Event.parseFromStorage(elements);
                        tasks[numberOfTasks] = nextTask;
                        numberOfTasks++;
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

    private static void appendToFile(String filePath, String textToAdd){
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
