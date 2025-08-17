import java.util.Scanner;

public class Avo {
    private static int numberOfTasks = 0;
    private static Task[] tasks = new Task[100];
    public static void greet(){
        String greetString = "Hello! I'm Avo\n         What can I do for you?";
        System.out.println(borderfy(greetString));
    }
    public static void bye(){
        String byeString = "Bye. Hope to see you again soon!";
        System.out.println(borderfy(byeString));
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
    }
    public static void deleteTask(int index) throws InvalidIndexException {
        Task selectedTask = tasks[index];
        if(index > numberOfTasks-1 || index<0){
            throw new InvalidIndexException(index,numberOfTasks);
        }else{
            for(int i = index + 1; i<numberOfTasks-1;i++){
                tasks[i] = tasks[i-1];
            }
        }
        String fullResponse = "Noted. I've removed this task:\n "
                + "         "
                + selectedTask.toString()
                + String.format("\n         Now you have %d tasks in the list.", numberOfTasks - 1);
        System.out.println(borderfy(fullResponse));
        numberOfTasks--;
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
    }
    public static void addToList(Task currentTask){
        tasks[numberOfTasks] = currentTask;
        String fullResponse = "Got it. I've added this task:\n "
                + "         "
                + currentTask.toString()
                + String.format("\n         Now you have %d tasks in the list.", numberOfTasks + 1);
        respond(fullResponse);
        numberOfTasks++;
    }

    public static String excludeFirstWord(String input){
        int firstSpace = input.indexOf(" ");
        String result = input.substring(firstSpace + 1);
        return result;
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        greet();
        boolean running = true;
        int indexSelected;
        while(running){
                try{
                    String input = scanner.nextLine();
                    String[] words = input.split( " ");
                    String firstWord = words[0];
                    switch(firstWord) {
                        case "bye":
                            bye();
                            running = false;
                            break;
                        case "list":
                            String showList = "Here are the tasks in your list:";
                            System.out.println(borderfy(showList + listString(tasks)));
                            break;
                        case "mark":
                            indexSelected = Integer.parseInt(words[1]) - 1;
                            mark(indexSelected);
                            break;
                        case "unmark":
                            indexSelected = Integer.parseInt(words[1]) - 1;
                            unmark(indexSelected);
                            break;
                        case "deadline":
                            Deadline currentDeadline = Deadline.parseDeadline(input);
                            addToList(currentDeadline);
                            break;
                        case "event":
                            Event currentEvent = Event.parseEvent(input);
                            addToList(currentEvent);
                            break;
                        case "todo":
                            Task currentTask = Task.parseTask(input);
                            addToList(currentTask);
                            break;
                        case "delete":
                            indexSelected = Integer.parseInt(words[1]) - 1;
                            deleteTask(indexSelected);
                            break;
                        default:
                            throw new UnknownCommandException();
                    }
                } catch (UnknownCommandException | EmptyInstructionException | InvalidIndexException e) {
                    System.out.println(borderfy(e.getMessage()));
                }
        }
    }
}
