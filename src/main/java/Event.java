import java.util.Objects;

public class Event extends Task{
    private String startTime;
    private String endTime;
    public Event(String text,String startTime,String endTime) {
        super(text);
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public static Event parseEvent(String input) throws EmptyInstructionException {
        String eventInstruction = Avo.excludeFirstWord(input.split("/")[0]);
        if(eventInstruction.equals(input)){
            throw new EmptyInstructionException();
        }
        String startTime = Avo.excludeFirstWord(input.split("/")[1]);
        String endTime = Avo.excludeFirstWord(input.split("/")[2]);
        Event currentEvent = new Event(eventInstruction, startTime,endTime);
        return currentEvent;
    }
    @Override
    public String toString(){
        String duration = String.format("(from: %sto: %s)",startTime,endTime);
        return String.format("[E]%s%s",super.toString(),duration);
    }
    @Override
    public String getStorageString(){
        return String.format("E|%s|%s|%s",super.getStorageString().substring(2),
                startTime,
                endTime);
    }
    public static Task parseFromStorage(String[] a){
        Event storedEvent =  new Event(a[2],a[3],a[4]);
        boolean isDone = Objects.equals(a[1], "x");
        if(isDone){
            storedEvent.mark();
        }
        return storedEvent;
    }
}
