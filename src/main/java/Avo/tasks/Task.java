package Avo.tasks;

public class Task {
    private final String instruction;
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

    /**
     *
     * @return String representation of to-do task in the storage file
     */
    public String getStorageString() {
        String mark = isDone?"x":"o";
        return String.format("T|%s|%s",mark, instruction);
    }
    public String getInstruction(){
        return instruction;
    }
}
