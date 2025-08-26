package Avo.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDate deadline;
    public Deadline(String instruction,LocalDate deadline){
        super(instruction);
        this.deadline = deadline;
    }
    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        String formattedDate = deadline.format(formatter);
        String deadline = String.format("(by: %s)", formattedDate);
        return String.format("[D]%s%s",super.toString(),deadline);
    }

    /**
     *
     * @return String representation of deadline in the storage file
     */
    @Override
    public String getStorageString(){
        return String.format("D|%s|%s"
                ,super.getStorageString().substring(2)
                ,deadline);
    }
}
