package avo.ui;

import java.time.format.DateTimeParseException;

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
    public static String greet() {
        String greetString = """
                Hello! I'm Avo
                Let me organise your tasks
                Input your tasks as such:
                Todo - todo <instruction>
                Deadline - deadline <instruction> /by <deadline in YYYY-MM-DD>
                Event - event <instruction> /from <start date in YYYY-MM-DD>
                       /to <end date in YYYY-MM-DD>
                """;
        return greetString;
    }

    /**
     * Bids goodbye to the user.
     */
    public static String bye() {
        String byeString = "Bye. Hope to see you again soon!";
        return byeString.stripTrailing();
    }

    /**
     * Prints out response for the removal of a task.
     *
     * @param selectedTask   The task selected for removal.
     * @param numberOfTasks  The total number of tasks.
     * @return Full response for removing a task
     */
    public static String removeTaskResponse(Task selectedTask, int numberOfTasks) {
        String fullResponse = "Noted. I've removed this task:\n "
                + "         "
                + selectedTask.toString()
                + String.format("\n         Now you have %d tasks in the list.", numberOfTasks);
        return fullResponse;
    }

    /**
     * Prints out response for the addition of a task.
     *
     * @param currentTask    The current task to be added.
     * @param numberOfTasks  The total number of tasks in the task list.
     * @return Full response for adding a task
     */
    public static String addTaskResponse(Task currentTask, int numberOfTasks) {
        String fullResponse = "Got it. I've added this task:\n "
                + "         "
                + currentTask.toString()
                + String.format("\n         Now you have %d tasks in the list.", numberOfTasks + 1);
        return fullResponse;
    }

    /**
     * Produces response to marking a task as done
     * @param taskList being managed by Avo
     * @return Appropriate response
     */
    public static String markTaskResponse(TaskList taskList) {
        String output = "OK, I've marked this task as done:\n";
        return output + taskList.toString();
    }

    /**
     * Produces response to marking a task as done
     * @param taskList being managed by Avo
     * @return Appropriate response
     */
    public static String unmarkTaskResponse(TaskList taskList) {
        String output = "OK, I've marked this task as not done yet:\n";
        return output + taskList.toString();
    }

    /**
     * Produces response for finding a task
     * @param taskList tasklist being managed bu Avo
     * @param searchedString String that is searched for bu the user
     * @return Appropriate response
     */
    public static String findTaskResponse(TaskList taskList, String searchedString) {
        if (taskList.isEmpty()) {
            return String.format("No tasks containing \"%s\" found!", searchedString);
        } else {
            String output = "Here are the matching tasks in your list:\n";
            return output + taskList;
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
     * Returns the appropriate response given the user's input
     * @param input from the user
     */
    public static String getResponse(String input) {
        String[] words = new String[50];
        try {
            words = input.split(" ");
            String firstWord = words[0];
            Command command = Parser.parseCommand(firstWord);
            int indexSelected;
            TaskList taskList = Avo.getTaskList();
            switch (command) {
            case BYE:
                return bye();
            case LIST:
                String prefix = "Here are the tasks in your list:";
                return prefix + taskList.toString();
            case MARK:
                indexSelected = getSelectedIndex(words);
                taskList.mark(indexSelected);
                return markTaskResponse(taskList);
            case UNMARK:
                indexSelected = getSelectedIndex(words);
                taskList.unmark(indexSelected);
                return unmarkTaskResponse(taskList);
            case DEADLINE:
                Deadline currentDeadline = Parser.parseDeadline(input.strip());
                taskList.addTask(currentDeadline, false);
                return addTaskResponse(currentDeadline, taskList.length());
            case EVENT:
                Event currentEvent = Parser.parseEvent(input.strip());
                taskList.addTask(currentEvent, false);
                return addTaskResponse(currentEvent, taskList.length());
            case TODO:
                Task currentTask = Parser.parseTask(input.strip());
                taskList.addTask(currentTask, false);
                return addTaskResponse(currentTask, taskList.length());
            case DELETE:
                indexSelected = getSelectedIndex(words);
                taskList.deleteTask(indexSelected);
                return removeTaskResponse(taskList.get(indexSelected),
                        taskList.length());
            case FIND:
                String searchedString = words.length > 1
                        ? words[1].strip()
                        : "";
                TaskList results = taskList.searchAll(searchedString);
                return findTaskResponse(results, searchedString);
            default:
                throw new UnknownCommandException();
            }
        } catch (AvoException e) {
            return e.getMessage();
        } catch (DateTimeParseException e) {
            String customMessage = " was written in the wrong format \n Write dates in yyyy-mm-dd";
            return e.getParsedString() + customMessage;
        } catch (NumberFormatException e) {
            return words[1] + " is not a valid index";
        }
    }
}
