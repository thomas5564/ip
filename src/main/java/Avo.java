import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Avo {
    //"C:/Users/thoma/Downloads/ip/data/avo.txt"
    //"data" + File.separator + "avo.txt";
    private static String pathName = "C:/Users/thoma/Downloads/ip/data/avo.txt";
    private static Scanner fileScanner;
    private static File storageFile;
    private static int numberOfTasks = 0;
    private static Task[] tasks = new Task[100];
    public static void greet(){
    String greetString = """
                
                        Hello! I'm Avo
                        Let me organise your tasks for the day
                        Input your tasks as such:
                        Todo - todo <instruction>
                        Deadline - deadline <instruction> /by <deadline in YYYY-MM-DD>
                        Event - event <instruction> /from <start date in YYYY-MM-DD> /to <end date in YYYY-MM-DD>
                """;
        System.out.println(borderfy(greetString));
    }
    public static void bye(){
        String byeString = "Bye. Hope to see you again soon!";
        System.out.println(borderfy(byeString).stripTrailing());
    }
    public static String borderfy(String s){
        String output = String.format(
                """
                        ____________________________________________________________
                         %s
                        ____________________________________________________________
                """,s);
        return output;
    }
    public static void respond(String response){
        String borderfiedResponse = borderfy(response);
        System.out.println(borderfiedResponse);
    }
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
            System.out.println(borderfy(output));
        }
        rewriteFileFromList();
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
        System.out.println(borderfy(fullResponse));
        rewriteFileFromList();
    }

    public static void mark(int index) throws InvalidIndexException {
        if(index > numberOfTasks-1 || index<0){
            throw new InvalidIndexException(index,numberOfTasks);
        }else{
            String output;
            tasks[index].mark();
            output = "Nice! I've marked this task as done:"+listString(tasks);
            System.out.println(borderfy(output));
        }
        rewriteFileFromList();
    }

    public static void addToList(Task currentTask){
        tasks[numberOfTasks] = currentTask;
        String fullResponse = "Got it. I've added this task:\n "
                + "         "
                + currentTask.toString()
                + String.format("\n         Now you have %d tasks in the list.", numberOfTasks + 1);
        respond(fullResponse);
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
    public static void rewriteFileFromList(){
        try{
            int counter = 0;
            FileWriter fileClearer = new FileWriter(pathName, false);
            fileClearer.append("");
            while(counter<numberOfTasks){
                appendToFile(pathName,tasks[counter].getStorageString());
                counter++;
            }
        }catch(FileNotFoundException e){
            System.out.println("File is not found. If you want your tasks to be saved,\n " +
                    "add the file and start the program again");
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        Scanner scanner = new Scanner(System.in);
        greet();
        boolean running = true;
        int indexSelected;
        while(running){
                try{
                    String input = scanner.nextLine();
                    String[] words = input.split( " ");
                    String firstWord = words[0];
                    Command command = parseCommand(firstWord);
                    switch(command) {
                    case BYE:
                        bye();
                        running = false;
                        break;
                    case LIST:
                        String showList = "Here are the tasks in your list:";
                        System.out.println(borderfy(showList + listString(tasks)));
                        break;
                    case MARK:
                        indexSelected = Integer.parseInt(words[1]) - 1;
                        mark(indexSelected);
                        break;
                    case UNMARK:
                        indexSelected = Integer.parseInt(words[1]) - 1;
                        unmark(indexSelected);
                        break;
                    case DEADLINE:
                        Deadline currentDeadline = Deadline.parseDeadline(input.strip());
                        addToList(currentDeadline);
                        break;
                    case EVENT:
                        Event currentEvent = Event.parseEvent(input.strip());
                        addToList(currentEvent);
                        break;
                    case TODO:
                        Task currentTask = Task.parseTask(input.strip());
                        addToList(currentTask);
                        break;
                    case DELETE:
                        indexSelected = Integer.parseInt(words[1]) - 1;
                        deleteTask(indexSelected);
                        break;
                    default:
                        throw new UnknownCommandException();
                    }
                } catch (UnknownCommandException | EmptyInstructionException | InvalidIndexException e) {
                    System.out.println(borderfy(e.getMessage()));
                } catch (DateTimeParseException e){
                    System.out.println(borderfy("Invalid date format! try again but in yyyy-mm-dd"));
                }
        }
    }
}
