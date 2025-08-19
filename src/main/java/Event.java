import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Event extends Task{
    private final LocalDate startTime;
    private final LocalDate endTime;
    public Event(String instruction,LocalDate startTime,LocalDate endTime) {
        super(instruction);
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public static Event parseEvent(String input) throws EmptyInstructionException {
        String eventInstruction = Avo.excludeFirstWord(input.split("/")[0]);
        if(eventInstruction.equals(input)){
            throw new EmptyInstructionException();
        }
        String startTimeString = Avo.excludeFirstWord(input.split("/")[1]).strip();
        String endTimeString = Avo.excludeFirstWord(input.split("/")[2]).strip();
        LocalDate startTime = LocalDate.parse(startTimeString);
        LocalDate endTime = LocalDate.parse(endTimeString);
        Event currentEvent = new Event(eventInstruction, startTime ,endTime);
        return currentEvent;
    }

    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        String formattedStartTime = startTime.format(formatter);
        String formattedEndTime = endTime.format(formatter);
        String duration = String.format("(from: %sto: %s)",formattedStartTime,formattedEndTime);
        return String.format("[E]%s%s",super.toString(),duration);
    }
    @Override
    public String getStorageString(){
        return String.format("E|%s|%s|%s",super.getStorageString().substring(2),
                startTime,
                endTime);
    }
    public static Task parseFromStorage(String[] a){
        LocalDate startTime = LocalDate.parse(a[3].strip());
        LocalDate endTime = LocalDate.parse(a[4].strip());
        Event storedEvent =  new Event(a[2],endTime,startTime);
        boolean isDone = Objects.equals(a[1], "x");
        if(isDone){
            storedEvent.mark();
        }
        return storedEvent;
    }
}
