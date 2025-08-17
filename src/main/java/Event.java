public class Event extends Task{
    private String startTime;
    private String endTime;
    public Event(String text,String startTime,String endTime) {
        super(text);
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public static Event parseEvent(String input){
        String eventInstruction = Avo.excludeFirstWord(input.split("/")[0]);
        String startTime = Avo.excludeFirstWord(input.split("/")[2]);
        String endTime = Avo.excludeFirstWord(input.split("/")[1]);
        Event currentEvent = new Event(eventInstruction, startTime,endTime);
        return currentEvent;
    }
    @Override
    public String toString(){
        String duration = String.format("(from: %s to: %s)",startTime,endTime);
        return String.format("[E]%s%s",super.toString(),duration);
    }
}
