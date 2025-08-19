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
    public static Deadline parseDeadline(String input) throws EmptyInstructionException {
        String instruction = Avo.excludeFirstWord(input.split("/")[0]);
        if(instruction.isEmpty()){
            throw new EmptyInstructionException();
        }
        String deadlineString = Avo.excludeFirstWord(input.split("/")[1]);
        LocalDate deadline = LocalDate.parse(deadlineString);
        return new Deadline(instruction, deadline);
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
    public static Task parseFromStorage(String[] a){
        LocalDate deadline = LocalDate.parse(a[3]);
        Deadline storedEvent =  new Deadline(a[2],deadline);
        boolean isDone = Objects.equals(a[1], "x");
        if(isDone){
            storedEvent.mark();
        }
        return storedEvent;
    }
}
