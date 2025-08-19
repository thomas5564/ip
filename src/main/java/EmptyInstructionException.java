public class EmptyInstructionException extends Exception {
    public EmptyInstructionException() {
        super("OOPS!!! The instruction of a task cannot be empty.");
    }
}

