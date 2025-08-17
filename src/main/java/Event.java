public class Event extends Task{
    private String startTime;
    private String endTime;
    public Event(String text,String startTime,String endTime) {
        super(text);
        this.startTime = startTime;
        this.endTime = endTime;
    }
    @Override
    public String toString(){
        String duration = String.format("(from: %s to: %s)",startTime,endTime);
        return String.format("[D] %s %s %s",super.toString(),duration);
    }
}
