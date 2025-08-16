public class Instruction {
    private String text;
    private boolean isDone;
    public Instruction(String text){
        this.isDone = false;
        this.text = text;
    }
    public void mark(){
        isDone = true;
    }
    public void unmark(){
        isDone = false;
    }
    public String getText(){
        return text;
    }
    public boolean getIsDone(){
        return isDone;
    }
}
