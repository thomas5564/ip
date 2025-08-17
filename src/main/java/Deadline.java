public class Deadline extends Task{
    private String dueDate;
    public Deadline(String text,String dueDate){
        super(text);
        this.dueDate = dueDate;
    }
    public static Deadline parseDeadline(String input){
        String instruction = Avo.excludeFirstWord(input.split("/")[0]);
        String deadline = Avo.excludeFirstWord(input.split("/")[1]);
        Deadline currentDeadline = new Deadline(instruction, deadline);
        return currentDeadline;
    }
    @Override
    public String toString(){
        String deadline = String.format("(by: %s)",dueDate);
        return String.format("[D]%s%s",super.toString(),deadline);
    }
}
