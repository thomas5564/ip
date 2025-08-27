package avo.ui;

import java.time.format.DateTimeParseException;
import java.util.Scanner;

import avo.commands.Command;
import avo.exceptions.AvoException;
import avo.exceptions.NoIndexException;
import avo.exceptions.UnknownCommandException;
import avo.main.Avo;
import avo.parser.Parser;
import avo.tasks.Deadline;
import avo.tasks.Event;
import avo.tasks.Task;
import avo.tasks.TaskList;

/**
 * Contains all methods with regard to the UI.
 */
public class AvoSpeaker {

    /**
     * Greets the user.
     */
    public static void greet() {
        String greetString = """
                Hello! I'm Avo
                Let me organise your tasks
                Input your tasks as such:
                Todo - todo <instruction>
                Deadline - deadline <instruction> /by <deadline in YYYY-MM-DD>
                Event - event <instruction> /from <start date in YYYY-MM-DD>
                       /to <end date in YYYY-MM-DD>
                """;
        System.out.println(borderfy(greetString));
    }

    /**
     * Bids goodbye to the user.
     */
    public static void bye() {
        String byeString = "Bye. Hope to see you again soon!";
        System.out.println(borderfy(byeString).stripTrailing());
    }

    /**
     * Wraps the input string in the border as shown.
     *
     * @param s The string to be wrapped.
     * @return The string wrapped with a border.
     */
    public static String borderfy(String s) {
        String output = String.format("""
                ____________________________________________________________
                 %s
                ____________________________________________________________
                """, s);
        return output;
    }

    /**
     * Prints out the input as a response with a border.
     *
     * @param response The response to be wrapped and printed.
     */
    public static void respond(String response) {
        String borderfiedResponse = borderfy(response);
        System.out.println(borderfiedResponse);
    }

    /**
     * Prints out response for the removal of a task.
     *
     * @param selectedTask   The task selected for removal.
     * @param numberOfTasks  The total number of tasks.
     */
    public static void removeTaskResponse(Task selectedTask, int numberOfTasks) {
        String fullResponse = "Noted. I've removed this task:\n "
                + "         "
                + selectedTask.toString()
                + String.format("\n         Now you have %d tasks in the list.", numberOfTasks);
        AvoSpeaker.respond(fullResponse);
    }

    /**
     * Prints out response for the addition of a task.
     *
     * @param currentTask    The current task to be added.
     * @param numberOfTasks  The total number of tasks in the task list.
     */
    public static void addTaskResponse(Task currentTask, int numberOfTasks) {
        String fullResponse = "Got it. I've added this task:\n "
                + "         "
                + currentTask.toString()
                + String.format("\n         Now you have %d tasks in the list.", numberOfTasks + 1);
        AvoSpeaker.respond(fullResponse);
    }

    /**
     * Prints out response for the marking of a task for completion.
     *
     * @param taskString The string representation of the task.
     */
    public static void markTaskResponse(String taskString) {
        String output = "OK, I've marked this task as done:\n";
        AvoSpeaker.respond(output + taskString);
    }

    /**
     * Prints out response for the unmarking of a task due to incompletion.
     *
     * @param taskString The string representation of the task.
     */
    public static void unmarkTaskResponse(String taskString) {
        String output = "OK, I've marked this task as not done yet:\n";
        AvoSpeaker.respond(output + taskString);
    }

    /**
     * Prints out response for tasks matching a search.
     *
     * @param taskList       The list of tasks.
     * @param searchedString The string being searched for.
     */
    public static void findTaskResponse(TaskList taskList, String searchedString) {
        if (taskList.isEmpty()) {
            AvoSpeaker.respond(String.format("No tasks containing \"%s\" found!", searchedString));
        } else {
            String output = "Here are the matching tasks in your list:\n";
            AvoSpeaker.respond(output + taskList);
        }
    }

    /**
     * Gets the selected index from a split user input.
     *
     * @param words The user input split into words.
     * @return The selected index.
     * @throws NoIndexException If no index was provided.
     */
    public static int getSelectedIndex(String[] words) throws NoIndexException {
        if (words.length > 1) {
            return Integer.parseInt(words[1]) - 1;
        } else {
            throw new NoIndexException(Avo.getTaskList().length());
        }
    }

    /**
     * Continuously gets the user's input and responds accordingly.
     */
    public static void uiLoop() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        String[] words = new String[50];
        while (running) {
            try {
                String input = scanner.nextLine();
                words = input.split(" ");
                String firstWord = words[0];
                Command command = Parser.parseCommand(firstWord);
                int indexSelected;
                switch (command) {
                case BYE:
                    AvoSpeaker.bye();
                    running = false;
                    break;
                case LIST:
                    String showList = "Here are the tasks in your list:";
                    System.out.println(AvoSpeaker.borderfy(showList + Avo.getTaskList().toString()));
                    break;
                case MARK:
                    indexSelected = getSelectedIndex(words);
                    Avo.getTaskList().mark(indexSelected);
                    break;
                case UNMARK:
                    indexSelected = getSelectedIndex(words);
                    Avo.getTaskList().unmark(indexSelected);
                    break;
                case DEADLINE:
                    Deadline currentDeadline = Parser.parseDeadline(input.strip());
                    Avo.getTaskList().addTask(currentDeadline, false);
                    break;
                case EVENT:
                    Event currentEvent = Parser.parseEvent(input.strip());
                    Avo.getTaskList().addTask(currentEvent, false);
                    break;
                case TODO:
                    Task currentTask = Parser.parseTask(input.strip());
                    Avo.getTaskList().addTask(currentTask, false);
                    break;
                case DELETE:
                    indexSelected = getSelectedIndex(words);
                    Avo.getTaskList().deleteTask(indexSelected);
                    break;
                case FIND:
                    String searchedString = words.length > 1
                            ? words[1].strip()
                            : "";
                    Avo.getTaskList().searchAll(searchedString);
                    break;
                default:
                    throw new UnknownCommandException();
                }
            } catch (AvoException e) {
                AvoSpeaker.respond(e.getMessage());
            } catch (DateTimeParseException e) {
                String customMessage = " was written in the wrong format \n Write dates in yyyy-mm-dd";
                AvoSpeaker.respond(e.getParsedString() + customMessage);
            } catch (NumberFormatException e) {
                AvoSpeaker.respond(words[1] + " is not a valid index");
            }
        }
    }
}
