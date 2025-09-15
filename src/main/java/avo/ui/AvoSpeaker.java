package avo.ui;

import java.time.format.DateTimeParseException;

import avo.commands.Command;
import avo.exceptions.AvoException;
import avo.exceptions.NoIndexException;
import avo.exceptions.UnknownCommandException;
import avo.parser.Parser;
import avo.responses.ByeResponse;
import avo.responses.CommandResponse;
import avo.responses.DeletionResponse;
import avo.responses.ErrorResponse;
import avo.responses.Response;
import avo.tasks.Deadline;
import avo.tasks.Event;
import avo.tasks.Task;
import avo.tasks.TaskList;

/**
 * Contains all methods with regard to the UI.
 */
public class AvoSpeaker {
    private TaskList taskList;

    /**
     * Constructor for this class
     * @param taskList that avo is managing
     */
    public AvoSpeaker(TaskList taskList) {
        this.taskList = taskList;
    }
    /**
     * Greets the user.
     */
    public String greet() {
        String greetString = """
                Hello! I'm Avo
                Let me organise your tasks.
                Do you have anything to do? Key it in and send it over!
                If you are more comfortable using GUI,
                Press ALT to switch to GUI mode
                To view your statistics, press F2.
                """;
        return greetString;
    }

    /**
     * Bids goodbye to the user.
     */
    public String bye() {
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
    public String removeTaskResponse(Task selectedTask, int numberOfTasks) {
        String fullResponse = "Okay, I've removed this task:\n "
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
    public String addTaskResponse(Task currentTask, int numberOfTasks) {
        String fullResponse = "Adding this task,pronto! \n "
                + "         "
                + currentTask.toString()
                + String.format("\n         Now you have %d tasks in the list.", numberOfTasks);
        return fullResponse;
    }

    /**
     * Produces response to marking a task as done
     * @param task to mark
     * @return Appropriate response
     */
    public String markTaskResponse(Task task) {
        String output = "Wahoo, I've marked this task as done:\n";
        return output + task.toString();
    }

    /**
     * Produces response to marking a task as done
     * @param task to unmark
     * @return Appropriate response
     */
    public String unmarkTaskResponse(Task task) {
        String output = "ok :( , I've marked this task as not done yet:\n";
        return output + task.toString();
    }

    /**
     * Produces response for finding a task
     * @param taskList tasklist being managed bu Avo
     * @param searchedString String that is searched for bu the user
     * @return Appropriate response
     */
    public String findTaskResponse(TaskList taskList, String searchedString) {
        if (taskList.isEmpty()) {
            return String.format("um, no tasks containing \"%s\" found!", searchedString);
        } else {
            String output = "Here are the matching tasks in your list:\n";
            return output + taskList;
        }
    }

    /**
     * gets response to stat
     * @return response to stat
     */
    public String statResponse() {
        String lastWeekCount = String.format("You did %d tasks last week", taskList.getNumberDoneLW());
        String lastWeekFR = taskList.getFinishRateLW() == -1
                ? "No tasks were created last week"
                : String.format("Last week's finish rate: %.2f", taskList.getFinishRateLW());
        return lastWeekCount + "\n" + lastWeekFR;
    }

    /**
     * Gets the selected index from a split user input.
     *
     * @param words The user input split into words.
     * @return The selected index.
     * @throws NoIndexException If no index was provided.
     */
    public int getSelectedIndex(String[] words, TaskList taskList) throws NoIndexException {
        if (words.length > 1) {
            return Integer.parseInt(words[1]) - 1;
        } else {
            throw new NoIndexException(taskList.length());
        }
    }

    /**
     * Returns the appropriate response given the user's input
     * @param input from the user
     */
    public Response getResponse(String input) {
        String[] words = new String[50];
        try {
            if (input.isBlank()) {
                return new ErrorResponse("Input is empty :(");
            }
            words = input.split(" ");
            String firstWord = words[0];
            Command command = Parser.parseCommand(firstWord);
            int indexSelected;
            switch (command) {
            case BYE:
                return new ByeResponse(bye());
            case LIST:
                String prefix = "Here are the tasks in your list:";
                String emptyListResponse = "You haven't added any tasks in the list. Add some to get started!";
                String response = taskList.isEmpty()
                        ? emptyListResponse
                        : prefix + taskList.toString();
                return new CommandResponse(
                        response,
                        Command.LIST
                );
            case LISTW:
                String weekprefix = "Here are the tasks from this week:"
                        + "\n Note: the indices you see are the tasks' indices in the main task list";
                String output = taskList.getWeeklyTasks().isEmpty()
                        ? "You haven't added any tasks this week. Add some to get started!"
                        : weekprefix + taskList.getWeeklyTasksString();
                return new CommandResponse(
                        output,
                        Command.LIST
                );
            case MARK:
                indexSelected = getSelectedIndex(words, taskList);
                taskList.mark(indexSelected);
                return new CommandResponse(
                        markTaskResponse(taskList.get(indexSelected)),
                        Command.MARK
                );
            case UNMARK:
                indexSelected = getSelectedIndex(words, taskList);
                taskList.unmark(indexSelected);
                return new CommandResponse(
                        unmarkTaskResponse(taskList.get(indexSelected)),
                        Command.UNMARK
                );
            case DEADLINE:
                Deadline currentDeadline = Parser.parseDeadline(input.strip());
                taskList.addTask(currentDeadline, true);
                return new CommandResponse(
                        addTaskResponse(currentDeadline, taskList.length()),
                        Command.DEADLINE
                );
            case EVENT:
                Event currentEvent = Parser.parseEvent(input.strip());
                taskList.addTask(currentEvent, true);
                return new CommandResponse(
                        addTaskResponse(currentEvent, taskList.length()),
                        Command.EVENT
                );
            case TODO:
                Task currentTask = Parser.parseTask(input.strip());
                taskList.addTask(currentTask, true);
                return new CommandResponse(
                        addTaskResponse(currentTask, taskList.length()),
                        Command.TODO
                );
            case DELETE:
                indexSelected = getSelectedIndex(words, taskList);
                Task taskSelected = taskList.get(indexSelected);
                taskList.deleteTask(indexSelected);
                return new DeletionResponse(
                        removeTaskResponse(taskSelected, taskList.length()),
                        Command.DELETE,
                        taskSelected
                );
            case FIND:
                String searchedString = words.length > 1
                        ? words[1].strip()
                        : "";
                TaskList results = taskList.searchAll(searchedString);
                return new CommandResponse(
                        findTaskResponse(results, searchedString),
                        Command.FIND
                );
            case STAT:
                return new CommandResponse(
                        statResponse(),
                        Command.STAT
                );
            default:
                throw new UnknownCommandException();
            }
        } catch (AvoException e) {
            return new ErrorResponse(e.getMessage());
        } catch (DateTimeParseException e) {
            String customMessage = " was written in the wrong format \n Write dates in yyyy-mm-dd";
            return new ErrorResponse(e.getParsedString() + customMessage);
        } catch (NumberFormatException e) {
            return new ErrorResponse(words[1] + " is not a valid index");
        }
    }
}
