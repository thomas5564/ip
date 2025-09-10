package avo.tasks;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * Class to represent tasks.
 */
public class Task {
    private final String instruction;
    private boolean isDone;
    private final LocalDate dateCreated;
    private LocalDate dateDone;

    /**
     * Constructor for this class.
     *
     * @param instruction instruction for the task
     * @param dateCreated date the task was created
     */
    public Task(String instruction, LocalDate dateCreated) {
        this.isDone = false;
        this.instruction = instruction;
        this.dateCreated = dateCreated;
        this.dateDone = null;
    }
    /**
     * Marks the task as done and sets the date of completion to the current date.
     */
    public void mark() {
        this.dateDone = LocalDate.now();
        isDone = true;
    }
    public int getIndex(TaskList taskList) {
        return taskList.getTasks().indexOf(this);
    }

    /**
     * Marks the task as done and sets the date of completion to a stored date.
     *
     * @param dateDone the stored date when the task was completed
     */
    public void markFromStorage(LocalDate dateDone) {
        this.dateDone = dateDone;
        isDone = true;
    }

    /**
     * Unmarks the task and clears the completion date.
     */
    public void unmark() {
        isDone = false;
        this.dateDone = null;
    }

    /**
     * Returns a string representation of the task.
     *
     * @return a string in the format "[T][x] instruction" or "[T][ ] instruction"
     */
    @Override
    public String toString() {
        String mark = isDone ? "[x]" : "[ ]";
        return String.format("[T]%s %s", mark, instruction);
    }

    /**
     * Produces a string representation of the task for storage.
     *
     * @return a storage string in the format "T|doneDate|-|instruction|dateCreated"
     */
    public String getStorageString() {
        String mark = isDone ? dateDone.toString() : "-";
        return String.format("T|%s|%s|%s", mark, instruction, dateCreated);
    }

    /**
     * Gets the instruction of the task.
     *
     * @return the task instruction
     */
    public String getInstruction() {
        return instruction;
    }

    /**
     * Returns whether the task is done.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean getIsDone() {
        return isDone;
    }

    /**
     * Gets the date the task was created.
     *
     * @return the creation date of the task
     */
    public LocalDate getDateCreated() {
        return dateCreated;
    }

    /**
     * Gets the date the task was completed.
     *
     * @return the completion date, or null if not completed
     */
    public LocalDate getDateDone() {
        return dateDone;
    }

    /**
     * Sets the completion date of the task.
     *
     * @param dateDone the date the task was completed
     */
    public void setDateDone(LocalDate dateDone) {
        this.dateDone = dateDone;
    }

    // -------------------------------
    // Week-related helper methods
    // -------------------------------

    private boolean isBetween(LocalDate target, LocalDate start, LocalDate end) {
        return !target.isBefore(start) && !target.isAfter(end);
    }

    /**
     * Returns true if the task was created this week (Mon–Sun).
     *
     * @return true if the task was created this week
     */
    public boolean isMadeThisWeek() {
        LocalDate start = getStartOfThisWeek();
        LocalDate end = start.plusDays(6);
        return isBetween(dateCreated, start, end);
    }

    /**
     * Returns true if the task was created last week (Mon–Sun).
     *
     * @return true if the task was created last week
     */
    public boolean isMadeLastWeek() {
        LocalDate start = getStartOfLastWeek();
        LocalDate end = getEndOfLastWeek();
        return isBetween(dateCreated, start, end);
    }

    /**
     * Returns true if the task was created exactly four weeks ago (Mon–Sun of that week).
     *
     * @return true if the task was created four weeks ago
     */
    public boolean isMadeFourWeeksAgo() {
        LocalDate start = getStartOfThisWeek().minusWeeks(4);
        LocalDate end = getStartOfThisWeek().minusDays(1);
        return isBetween(dateCreated, start, end);
    }

    /**
     * Returns true if the task was completed last week (Mon–Sun).
     *
     * @return true if the task was completed last week
     */
    public boolean isDoneLastWeek() {
        if (dateDone == null) {
            return false;
        }
        LocalDate start = getStartOfLastWeek();
        LocalDate end = getEndOfLastWeek();
        return isBetween(dateDone, start, end);
    }

    private LocalDate getStartOfThisWeek() {
        LocalDate today = LocalDate.now();
        return today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    private LocalDate getStartOfLastWeek() {
        return getStartOfThisWeek().minusWeeks(1);
    }

    private LocalDate getEndOfLastWeek() {
        return getStartOfLastWeek().plusDays(6);
    }
    public LocalDate getWeekEnd() {
        return dateCreated.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).plusDays(7);
    }
    public boolean getIsDoneInWeekCreated() {
        LocalDate start = dateCreated.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = start.plusDays(6);
        return isDone && isBetween(dateDone, start, end);
    }
}
