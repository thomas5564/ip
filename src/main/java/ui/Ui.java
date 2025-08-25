package ui;
import Exceptions.IncompleteInputException;
import main.Avo;
import Exceptions.EmptyInstructionException;
import Exceptions.InvalidIndexException;
import Exceptions.UnknownCommandException;
import tasks.Task;
import tasks.Deadline;
import tasks.Event;
import Commands.Command;

import java.time.format.DateTimeParseException;
import java.util.Scanner;
import parser.Parser;

/**contains all methods with regard to the UI
 *
 */
public class Ui {
    /**greets the user
     *
     */
    public static void greet(){
        String greetString = """
                    
                    Hello! I'm Avo
                    Let me organise your tasks
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

    /**wraps the input string in the border as shown
     *
     * @param s string to be wrapped
     * @return string wrapped with border
     */
    public static String borderfy(String s){
        String output = String.format(
                """
                        ____________________________________________________________
                         %s
                        ____________________________________________________________
                """,s);
        return output;
    }

    /**will print out the input as a response with a border
     *
     * @param response to be wrapped and printed
     */
    public static void respond(String response){
        String borderfiedResponse = borderfy(response);
        System.out.println(borderfiedResponse);
    }

    /**prints out response for the removal of a task
     *
     * @param selectedTask task selected for removal
     * @param numberOfTasks total number of tasks
     */
    public static void removeTaskResponse(Task selectedTask, int numberOfTasks){
        String fullResponse = "Noted. I've removed this task:\n "
                + "         "
                + selectedTask.toString()
                + String.format("\n         Now you have %d tasks in the list.", numberOfTasks);
        Ui.respond(fullResponse);
    }

    /**prints out response for the addition of a task
     *
     * @param currentTask current task to be added
     * @param numberOfTasks total number of tasks in the task list
     */
    public static void addTaskResponse(Task currentTask, int numberOfTasks){
        String fullResponse = "Got it. I've added this task:\n "
                + "         "
                + currentTask.toString()
                + String.format("\n         Now you have %d tasks in the list.", numberOfTasks + 1);
        Ui.respond(fullResponse);
    }

    /**prints out response for the marking of task for completion
     *
     * @param taskString string representation of task
     */
    public static void markTaskResponse(String taskString){
        String output = "OK, I've marked this task as done:\n";
        Ui.respond(output + taskString);
    }

    /**prints out response for the unmarking of task due to incompletion
     *
     * @param taskString string representation of task
     */
    public static void unmarkTaskResponse(String taskString){
        String output = "OK, I've marked this task as not done yet:\n";
        Ui.respond(output + taskString);
    }

    /**
     * while loop that continuously gets the users input and responds accordingly
     */
    public static void uiLoop() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        int indexSelected;
        while (running) {
            try {
                String input = scanner.nextLine();
                String[] words = input.split(" ");
                String firstWord = words[0];
                Command command = Parser.parseCommand(firstWord);
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
                        Deadline currentDeadline = Parser.parseDeadline(input.strip());
                        Avo.taskList.addTask(currentDeadline,false);
                        break;
                    case EVENT:
                        Event currentEvent = Parser.parseEvent(input.strip());
                        Avo.taskList.addTask(currentEvent,false);
                        break;
                    case TODO:
                        Task currentTask = Parser.parseTask(input.strip());
                        Avo.taskList.addTask(currentTask,false);
                        break;
                    case DELETE:
                        indexSelected = Integer.parseInt(words[1]) - 1;
                        Avo.taskList.deleteTask(indexSelected);
                        break;
                    default:
                        throw new UnknownCommandException();
                }
            } catch (UnknownCommandException | EmptyInstructionException | InvalidIndexException |
                     IllegalArgumentException | IncompleteInputException e) {
                Ui.respond(e.getMessage());
            } catch(DateTimeParseException e){
                String customMessage = " was written in the wrong format \n Write dates in yyyy-mm-dd";
                System.out.println(e.getParsedString()+customMessage);
            }
        }
    }
}
