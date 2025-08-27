package avo.tasks;
import avo.exceptions.EmptySearchStringException;
import avo.exceptions.InvalidIndexException;
import avo.main.Avo;
import avo.ui.AvoSpeaker;


/**
 * List of tasks where the tasks are added. Has a maximum capacity of 100.
 */

public class TaskList {
    private int numberOfTasks = 0;
    private final Task[] tasks = new Task[100];

    public int length() {
        return numberOfTasks;
    }
    public boolean isEmpty() {
        return numberOfTasks == 0;
    }
    @Override
    public String toString() {
        StringBuilder listString = new StringBuilder();
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] == null) {
                break;
            }
            String line = "\n         " + (i + 1) + "." + tasks[i].toString();
            listString.append(line);
        }
        return listString.toString();
    }

    /**
     * Deletes task, changes the storage file accordingly
     * @param index index for the task to be deleted
     * @throws InvalidIndexException if the index is less than 1 or more than the total number of tasks
     */
    public void deleteTask(int index) throws InvalidIndexException {
        if (index >= numberOfTasks || index < 0) {
            throw new InvalidIndexException(index + 1, numberOfTasks);
        }
        Task selectedTask = tasks[index];
        for (int i = index; i < numberOfTasks - 1; i++) {
            tasks[i] = tasks[i + 1];
        }
        tasks[numberOfTasks - 1] = null;
        numberOfTasks--;
        AvoSpeaker.removeTaskResponse(selectedTask, numberOfTasks);
    }

    /**
     * Marks task as completed, changes the storage file accordingly
     * @param index index of task to be marked
     * @throws InvalidIndexException if the index is less than 1 or more than the total number of tasks
     */
    public void mark(int index) throws InvalidIndexException {
        if (index > numberOfTasks - 1 || index < 0) {
            throw new InvalidIndexException(index, numberOfTasks);
        } else {
            tasks[index].mark();
            AvoSpeaker.markTaskResponse(tasks[index].toString());
        }
        Avo.getStorage().rewriteFileFromList(numberOfTasks, tasks);
    }

    /**
     * Adds task, changes the storage file accordingly
     * @param currentTask current task to be added
     * @param isAddingToMemory if the task is being added to the data file
     */
    public void addTask(Task currentTask, boolean isAddingToMemory) {
        tasks[numberOfTasks] = currentTask;
        numberOfTasks++;
        if (isAddingToMemory) {
            Avo.getStorage().appendToFile(currentTask.getStorageString());
            AvoSpeaker.addTaskResponse(currentTask, numberOfTasks);
        }
    }

    /**
     * Unmarks a task in the task list
     * @param index where task is located
     * @throws InvalidIndexException if the index given is invalid
     */
    public void unmark(int index) throws InvalidIndexException {
        if (index > numberOfTasks - 1 || index < 0) {
            throw new InvalidIndexException(index, numberOfTasks);
        } else {
            tasks[index].unmark();
            AvoSpeaker.unmarkTaskResponse(tasks[index].toString());
        }
        Avo.getStorage().rewriteFileFromList(numberOfTasks, tasks);
    }

    /**
     * Prints out a list of tasks with instructions that contain a certain keyword
     * @param keyword word that the user searched up
     */
    public void searchAll(String keyword) throws EmptySearchStringException {
        if (keyword.isEmpty()) {
            throw new EmptySearchStringException();
        }
        TaskList results = new TaskList();
        for (int i = 0; i < numberOfTasks; i++) {
            if (tasks[i].getInstruction().contains(keyword)) {
                results.addTask(tasks[i], false);
            }
        }
        AvoSpeaker.findTaskResponse(results, keyword);
    }
}
