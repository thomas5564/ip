import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Deadline extends Task{
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
    @Override
    public String getStorageString(){
        return String.format("D|%s|%s",super.getStorageString().substring(2),deadline);
    }
}
