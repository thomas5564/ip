import java.util.Objects;

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
    public static Task parseTask(String input) throws EmptyInstructionException{
        String instruction = Avo.excludeFirstWord(input);
        if(instruction.equals(input) || instruction.isBlank()){
            throw new EmptyInstructionException();
        }
        return new Task(instruction);
    }
    public static Task parseFromStorage(String[] a){
        Task storedTask = new Task(a[2]);
        boolean isDone = Objects.equals(a[1], "x");
        if(isDone){
            storedTask.mark();
        }
        return storedTask;
    }

    public String getStorageString() {
        String mark = isDone?"x":"o";
        return String.format("T|%s|%s",mark, instruction);
    }
}
