import java.util.Scanner;

public class Ui {
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
    public static void removeTaskResponse(Task selectedTask, int numberOfTasks){
        String fullResponse = "Noted. I've removed this task:\n "
                + "         "
                + selectedTask.toString()
                + String.format("\n         Now you have %d tasks in the list.", numberOfTasks);
        Ui.respond(fullResponse);
    }
    public static void addTaskResponse(Task currentTask, int numberOfTasks){
        String fullResponse = "Got it. I've added this task:\n "
                + "         "
                + currentTask.toString()
                + String.format("\n         Now you have %d tasks in the list.", numberOfTasks + 1);
        Ui.respond(fullResponse);
    }
    public static void markTaskResponse(String listString){
        String output = "OK, I've marked this task as done:";
        Ui.respond(output);
    }
    public static void unmarkTaskResponse(String listString){
        String output = "OK, I've marked this task as not done yet:";
        Ui.respond(output);
    }
    public static void uiLoop() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        int indexSelected;
        while (running) {
            try {
                String input = scanner.nextLine();
                String[] words = input.split(" ");
                String firstWord = words[0];
                Command command = Avo.parseCommand(firstWord);
                switch (command) {
                    case BYE:
                        Ui.bye();
                        running = false;
                        break;
                    case LIST:
                        String showList = "Here are the tasks in your list:";
                        System.out.println(Ui.borderfy(showList + Avo.taskList.toString()));
                        break;
                    case MARK:
                        indexSelected = Integer.parseInt(words[1]) - 1;
                        Avo.taskList.mark(indexSelected);
                        break;
                    case UNMARK:
                        indexSelected = Integer.parseInt(words[1]) - 1;
                        Avo.taskList.unmark(indexSelected);
                        break;
                    case DEADLINE:
                        Deadline currentDeadline = Deadline.parseDeadline(input.strip());
                        Avo.taskList.addTask(currentDeadline,false);
                        break;
                    case EVENT:
                        Event currentEvent = Event.parseEvent(input.strip());
                        Avo.taskList.addTask(currentEvent,false);
                        break;
                    case TODO:
                        Task currentTask = Task.parseTask(input.strip());
                        Avo.taskList.addTask(currentTask,false);
                        break;
                    case DELETE:
                        indexSelected = Integer.parseInt(words[1]) - 1;
                        Avo.taskList.deleteTask(indexSelected);
                        break;
                    default:
                        throw new UnknownCommandException();
                }
            } catch (UnknownCommandException | EmptyInstructionException | InvalidIndexException e) {
                Ui.respond(e.getMessage());
            }
        }
    }
}
