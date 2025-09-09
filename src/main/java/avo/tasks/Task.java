package avo.tasks;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * Class to represent tasks
 */
public class Task {
    private LocalDate today = LocalDate.now();
    private final String instruction;
    private boolean isDone;
    private LocalDate dateCreated;
    private LocalDate startOfThisWeek = today.with(
            TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)
    );
    private LocalDate startOfLastWeek = startOfThisWeek.minusWeeks(1);
    private LocalDate dateDone;

    /**
     * Constructor for this class
     *
     * @param instruction instruction for the class
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
        this.setDateDone(today);
        isDone = true;
    }
    /**
     * Marks the task as done and sets the date of completion to stored date
     */
    public void markFromStorage(LocalDate dateDone) {
        this.setDateDone(dateDone);
        isDone = true;
    }
    public void unmark() {
        isDone = false;
    }

    @Override
    public String toString() {
        String mark = isDone ? "[x]" : "[ ]";
        return String.format("[T]%s%s", mark, instruction);
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
    public LocalDate getToday() {
        return today;
    }

    public boolean getIsDone() {
        return isDone;
    }
    /**
     * Returns true if the task was created last week (Mon to Today)
     **/
    public boolean isMadeThisWeek() {
        LocalDate d = dateCreated;
        return !d.isBefore(startOfThisWeek);
    }
    /**
     * Returns true if the task was created 4 weeks ago
     **/
    public boolean isMadeFourWeeksAgo() {
        LocalDate startDay = startOfThisWeek.minusDays(1).minusWeeks(4);
        return dateCreated.isAfter(startDay) && dateCreated.isBefore(today);
    }

    /**
     * Returns true if the task was created last week (Mon to Sun)
     * */
    public boolean isMadeLastWeek() {
        return !dateCreated.isBefore(startOfLastWeek)
                && !dateCreated.isAfter(startOfThisWeek.minusDays(1));
    }
    public boolean isDoneLastWeek() {
        return dateDone != null && !dateDone.isBefore(startOfLastWeek)
                && !dateDone.isAfter(startOfThisWeek.minusDays(1));
    }

    public void setDateDone(LocalDate dateDone) {
        this.dateDone = dateDone;
    }
    public LocalDate getDateCreated() {
        return dateCreated;
    }
    public LocalDate getWeekEnd() {
        return dateCreated.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    }
}
