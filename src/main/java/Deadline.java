public class Deadline extends Task{
    private String dueDate;
    public Deadline(String text,String dueDate){
        super(text);
        this.dueDate = dueDate;
    }
    @Override
    public String toString(){
        String deadline = String.format("(by: %s)",dueDate);
        return String.format("[D] %s %s %s",super.toString(),deadline);
    }
}
