package tasks;

public class Task {
    private String instruction;
    private boolean isDone;
    public Task(String instruction){
        this.isDone = false;
        this.instruction = instruction;
    }
    public void mark(){
        isDone = true;
    }
    public void unmark(){
        isDone = false;
    }
    public String toString(){
        String mark = isDone?"[x]":"[ ]";
        return String.format("[T]%s%s",mark, instruction);
    }
    public String getStorageString() {
        String mark = isDone?"x":"o";
        return String.format("T|%s|%s",mark, instruction);
    }
}
