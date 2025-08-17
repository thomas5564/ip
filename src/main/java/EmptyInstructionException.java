public class EmptyInstructionException extends Exception {
    public EmptyInstructionException() {
        super("OOPS!!! The description of a todo cannot be empty.");
    }
}

