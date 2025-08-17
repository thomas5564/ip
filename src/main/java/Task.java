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
        return String.format("%s%s",mark, instruction);
    }
    public static Task parseTask(String input) throws EmptyInstructionException{
        String instruction = Avo.excludeFirstWord(input);
        if(instruction.isEmpty()){
            throw new EmptyInstructionException();
        }
        return new Task(instruction);
    }
}
