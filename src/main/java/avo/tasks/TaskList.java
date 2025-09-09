package avo.tasks;



import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import avo.exceptions.EmptySearchStringException;
import avo.exceptions.InvalidIndexException;
import avo.storage.Storage;


/**
 * Wraps an arraylist of tasks
 */

public class TaskList {

    private final ArrayList<Task> tasks = new ArrayList<>();
    private Storage storage;
    private boolean isStored = false;
    private int numberDone;
    /**
     * Constructor to use for the tasklist that Avo is managing
     * @param storage storage instance where the tasks are stored
     */
    public TaskList(Storage storage) {
        this.storage = storage;
        this.isStored = true;
        storage.readFile(this);
        sortByDateCreated();
    }

    /**
     * Constructor to use for any task lists made for
     * ad-hoc use
     */
    public TaskList() {
        this.storage = null;
    }

    public int length() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public Task get(int index) throws InvalidIndexException {
        if (index >= tasks.size() || index < 0) {
            throw new InvalidIndexException(index + 1, tasks.size());
        }
        return tasks.get(index);
    }
    @Override
    public String toString() {
        return IntStream.range(0, tasks.size())
                .mapToObj(i -> "\n         " + (i + 1) + "." + tasks.get(i))
                .collect(Collectors.joining());
    }

    /**
     * Deletes task, changes the storage file accordingly
     * @param index index for the task to be deleted
     * @throws InvalidIndexException if the index is less than 1 or more than the total number of tasks
     */
    public void deleteTask(int index) throws InvalidIndexException {
        assert !tasks.isEmpty() : "Invalid number of tasks!";
        if (index >= tasks.size() || index < 0) {
            throw new InvalidIndexException(index + 1, tasks.size());
        }
        tasks.remove(index);
        if (isStored) {
            storage.rewriteFileFromList(tasks);
        }
    }

    /**
     * Marks task as completed, changes the storage file accordingly
     * @param index index of task to be marked
     * @throws InvalidIndexException if the index is less than 1 or more than the total number of tasks
     */
    public void mark(int index) throws InvalidIndexException {
        assert !tasks.isEmpty() : "Invalid number of tasks!";
        if (index > tasks.size() - 1 || index < 0) {
            throw new InvalidIndexException(index + 1, tasks.size());
        } else {
            tasks.get(index).mark();
        }
        if (isStored) {
            storage.rewriteFileFromList(tasks);
        }
        numberDone++;
    }

    /**
     * Adds task, changes the storage file accordingly
     * @param currentTask current task to be added
     * @param isAddingToMemory if the task is being added to the data file
     */
    public void addTask(Task currentTask, boolean isAddingToMemory) {
        tasks.add(currentTask);
        if (isStored && isAddingToMemory) {
            storage.appendToFile(currentTask.getStorageString());
            if (currentTask.getIsDone()) {
                numberDone++;
            }
        }
    }

    /**
     * Unmarks a task in the task list
     * @param index where task is located
     * @throws InvalidIndexException if the index given is invalid
     */
    public void unmark(int index) throws InvalidIndexException {
        assert !tasks.isEmpty() : "Invalid number of tasks!";
        if (index > tasks.size() - 1 || index < 0) {
            throw new InvalidIndexException(index + 1, tasks.size());
        } else {
            tasks.get(index).unmark();
        }
        if (isStored) {
            storage.rewriteFileFromList(tasks);
        }
        numberDone--;
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
        tasks.stream()
                .filter(task -> task.getInstruction().contains(keyword))
                .forEach(task -> results.addTask(task, false));
        return results;
    }
    public long getNumberDoneLW() {
        return tasks.stream()
                .filter(Task::getIsDone)
                .filter(Task::isDoneLastWeek)
                .count();
    }
    public TaskList getTaskLW() {
        TaskList taskList = new TaskList();
        tasks.stream()
                .filter(Task::isDoneLastWeek)
                .forEach(task -> taskList.addTask(task, false));
        return taskList;
    }
    public double getFinishRateLW() {
        long numberCreated = tasks.stream()
                .filter(Task::isMadeLastWeek)
                .count();
        if (numberCreated == 0) {
            return -1;
        }
        long numberDone = tasks.stream()
                .filter(Task::isMadeLastWeek)
                .filter(Task::getIsDone)
                .filter(Task::isDoneLastWeek)
                .count();

        return (double) numberDone / numberCreated;
    }

    /**
     * Sorts the tasks by date and rewrites them in storage accordingly
     */
    public void sortByDateCreated() {
        tasks.sort(Comparator.comparing(Task::getDateCreated));
        storage.rewriteFileFromList(tasks);
    }
    public List<Task> getTasks() {
        return tasks;
    }
    public Map<String, Long> getCompletionMap() {
        return tasks.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getIsDone() ? "Completed" : "Not Completed",
                        Collectors.counting()
                ));
    }

    /**
     * Get a map with the string representation of the end of the weeks as the key,
     * and the corresponding finish rate (FR) for the week as the value.
     * @return The date-finishRate map for tasks completed in the last 30 days
     */
    public Map<String, Double> getFinishRateMap() {
        return tasks.stream()
                .filter(Task::isMadeFourWeeksAgo)
                .collect(Collectors.groupingBy(
                        task -> task.getWeekEnd().toString(),
                        Collectors.averagingDouble(task -> task.getIsDone() ? 1.0 : 0.0)
                ));
    }
    public TaskList getWeeklyTasks() {
        TaskList taskList = new TaskList();
        tasks.stream()
                .filter(Task::isMadeThisWeek)
                .forEach(task -> taskList.addTask(task, false));
        return taskList;
    }
}
