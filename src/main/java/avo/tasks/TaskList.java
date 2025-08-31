package avo.tasks;
import avo.exceptions.EmptySearchStringException;
import avo.exceptions.InvalidIndexException;
import avo.storage.Storage;

/**
 * List of tasks where the tasks are added. Has a maximum capacity of 100.
 */

public class TaskList {
    private int numberOfTasks = 0;
    private final Task[] tasks = new Task[100];
    private Storage storage;
    private boolean isStored = false;

    /**
     * Constructor to use for the tasklist that Avo is managing
     * @param storage storage instance where the tasks are stored
     */
    public TaskList(Storage storage) {
        this.storage = storage;
        this.isStored = true;
        storage.readFile(this);
    }

    /**
     * Constructor to use for any task lists made for
     * ad-hoc use
     */
    public TaskList() {
        this.storage = null;
    }

    public int length() {
        return numberOfTasks;
    }
    public boolean isEmpty() {
        return numberOfTasks == 0;
    }
    public Task get(int index) throws InvalidIndexException {
        if (index >= numberOfTasks || index < 0) {
            throw new InvalidIndexException(index + 1, numberOfTasks);
        }
        return tasks[index];
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
        for (int i = index; i < numberOfTasks - 1; i++) {
            tasks[i] = tasks[i + 1];
        }
        tasks[numberOfTasks - 1] = null;
        numberOfTasks--;
        if (isStored) {
            storage.rewriteFileFromList(numberOfTasks, tasks);
        }
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
        }
        if (isStored) {
            storage.rewriteFileFromList(numberOfTasks, tasks);
        }
    }

    /**
     * Adds task, changes the storage file accordingly
     * @param currentTask current task to be added
     * @param isAddingToMemory if the task is being added to the data file
     */
    public void addTask(Task currentTask, boolean isAddingToMemory) {
        tasks[numberOfTasks] = currentTask;
        numberOfTasks++;
        if (isStored && isAddingToMemory) {
            storage.appendToFile(currentTask.getStorageString());
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
        }
        if (isStored) {
            storage.rewriteFileFromList(numberOfTasks, tasks);
        }
    }

    /**
     * Prints out a list of tasks with instructions that contain a certain keyword
     * @param keyword word that the user searched up
     * @return Tasklist containing all tasks with a matching instruction
     * @throws EmptySearchStringException if the searched string is empty
     */
    public TaskList searchAll(String keyword) throws EmptySearchStringException {
        if (keyword.isEmpty()) {
            throw new EmptySearchStringException();
        }
        TaskList results = new TaskList();
        for (int i = 0; i < numberOfTasks; i++) {
            if (tasks[i].getInstruction().contains(keyword)) {
                results.addTask(tasks[i], false);
            }
        }
        return results;
    }
}
