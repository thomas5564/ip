public class Task {
    private String text;
    private boolean isDone;
    public Task(String text){
        this.isDone = false;
        this.text = text;
    }
    public void mark(){
        isDone = true;
    }
    public void unmark(){
        isDone = false;
    }
    public String toString(){
        String mark = isDone?"[x]":"[ ]";
        return String.format("%s %s",mark,text);
    }
}
