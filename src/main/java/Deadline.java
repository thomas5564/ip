import java.util.Objects;

public class Deadline extends Task{
    private String dueDate;
    public Deadline(String text,String dueDate){
        super(text);
        this.dueDate = dueDate;
    }
    public static Deadline parseDeadline(String input) throws EmptyInstructionException {
        String instruction = Avo.excludeFirstWord(input.split("/")[0]);
        if(instruction.isEmpty()){
            throw new EmptyInstructionException();
        }
        String deadline = Avo.excludeFirstWord(input.split("/")[1]);
        Deadline currentDeadline = new Deadline(instruction, deadline);
        return currentDeadline;
    }
    @Override
    public String toString(){
        String deadline = String.format("(by: %s)",dueDate);
        return String.format("[D]%s%s",super.toString(),deadline);
    }
    @Override
    public String getStorageString(){
        return String.format("D|%s|%s",super.getStorageString().substring(2),dueDate);
    }
    public static Task parseFromStorage(String[] a){
        Deadline storedEvent =  new Deadline(a[2],a[3]);
        boolean isDone = Objects.equals(a[1], "x");
        if(isDone){
            storedEvent.mark();
        }
        return storedEvent;
    }
}
