package avo.tasks;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * Class to represent tasks
 */
public class Task {
    private final String instruction;
    private boolean isDone;
    private final LocalDate dateCreated;
    private LocalDate dateDone;

    /**
     * Constructor for this class
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
     * Marks the task as done and sets the date of completion to current date
     */
    public void mark() {
        this.dateDone = LocalDate.now();
        isDone = true;
    }

    /**
     * Marks the task as done and sets the date of completion to stored date
     */
    public void markFromStorage(LocalDate dateDone) {
        this.dateDone = dateDone;
        isDone = true;
    }

    /**
     * unmarks the task
     */
    public void unmark() {
        isDone = false;
        this.dateDone = null;
    }

    @Override
    public String toString() {
        String mark = isDone ? "[x]" : "[ ]";
        return String.format("[T]%s %s", mark, instruction);
    }

    /**
     * Produces string representation of task for storage
     * @return String representation of to-do task in the storage file
     */
    public String getStorageString() {
        String mark = isDone ? dateDone.toString() : "-";
        return String.format("T|%s|%s|%s", mark, instruction, dateCreated);
    }

    public String getInstruction() {
        return instruction;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public LocalDate getDateDone() {
        return dateDone;
    }

    public void setDateDone(LocalDate dateDone) {
        this.dateDone = dateDone;
    }

    // -------------------------------
    // Week-related helper methods
    // -------------------------------

    private LocalDate getStartOfThisWeek() {
        LocalDate today = LocalDate.now();
        return today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    private LocalDate getStartOfLastWeek() {
        return getStartOfThisWeek().minusWeeks(1);
    }

    private LocalDate getEndOfLastWeek() {
        return getStartOfThisWeek().minusDays(1);
    }

    /**
     * Returns true if the task was created this week (Mon–Sun).
     */
    public boolean isMadeThisWeek() {
        LocalDate startOfThisWeek = getStartOfThisWeek();
        return !dateCreated.isBefore(startOfThisWeek);
    }

    /**
     * Returns true if the task was created last week (Mon–Sun).
     */
    public boolean isMadeLastWeek() {
        LocalDate startOfLastWeek = getStartOfLastWeek();
        LocalDate endOfLastWeek = getEndOfLastWeek();
        return !dateCreated.isBefore(startOfLastWeek)
                && !dateCreated.isAfter(endOfLastWeek);
    }

    /**
     * Returns true if the task was created exactly 4 weeks ago (Mon–Sun of that week).
     */
    public boolean isMadeFourWeeksAgo() {
        LocalDate startOfFourWeeksAgo = getStartOfThisWeek().minusWeeks(4);
        LocalDate endOfFourWeeksAgo = startOfFourWeeksAgo.plusDays(6);
        return !dateCreated.isBefore(startOfFourWeeksAgo)
                && !dateCreated.isAfter(endOfFourWeeksAgo);
    }

    /**
     * Returns true if the task was completed last week (Mon–Sun).
     */
    public boolean isDoneLastWeek() {
        if (dateDone == null) {
            return false;
        }
        LocalDate startOfLastWeek = getStartOfLastWeek();
        LocalDate endOfLastWeek = getEndOfLastWeek();
        return !dateDone.isBefore(startOfLastWeek)
                && !dateDone.isAfter(endOfLastWeek);
    }

    /**
     * Returns the Sunday of the week this task was created.
     */
    public LocalDate getWeekEnd() {
        return dateCreated.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    }
}
