import java.time.LocalDate;
import java.util.Objects;

public class Parser {
    public static String excludeFirstWord(String input){
        int firstSpace = input.indexOf(" ");
        return input.substring(firstSpace + 1);
    }
    public static Command parseCommand(String input) throws UnknownCommandException {
        try {
            return Command.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnknownCommandException();
        }
    }
    public static Deadline parseDeadline(String input) throws EmptyInstructionException {
        String instruction = Parser.excludeFirstWord(input.split("/")[0]);
        if(instruction.isEmpty()){
            throw new EmptyInstructionException();
        }
        String deadlineString = excludeFirstWord(input.split("/")[1]);
        LocalDate deadline = LocalDate.parse(deadlineString);
        return new Deadline(instruction, deadline);
    }

    public static Event parseEvent(String input) throws EmptyInstructionException {
        String eventInstruction = excludeFirstWord(input.split("/")[0]);
        if(eventInstruction.equals(input)){
            throw new EmptyInstructionException();
        }
        String startTimeString = excludeFirstWord(input.split("/")[1]).strip();
        String endTimeString = excludeFirstWord(input.split("/")[2]).strip();
        LocalDate startTime = LocalDate.parse(startTimeString);
        LocalDate endTime = LocalDate.parse(endTimeString);
        Event currentEvent = new Event(eventInstruction, startTime ,endTime);
        return currentEvent;
    }
    public static Task parseTask(String input) throws EmptyInstructionException{
        String instruction = excludeFirstWord(input);
        if(instruction.equals(input) || instruction.isBlank()){
            throw new EmptyInstructionException();
        }
        return new Task(instruction);
    }
    public static Task parseTaskFromStorage(String entryString){
        Task currentTask;
        char firstLetter = entryString.charAt(0);
        switch (firstLetter) {
            case 'T':
                currentTask = Parser.parseTodoFromStorage(entryString);
                break;
            case 'D':
                currentTask = Parser.parseDeadlineFromStorage(entryString);
                break;
            case 'E':
                currentTask = Parser.parseEventFromStorage(entryString);
                break;
            default:
                System.out.println("invalid entry!");
                return null;
        }
        return currentTask;
    }

    public static Task parseEventFromStorage(String string){
        String[] a = string.split("\\|");
        LocalDate startTime = LocalDate.parse(a[3].strip());
        LocalDate endTime = LocalDate.parse(a[4].strip());
        Event storedEvent =  new Event(a[2],endTime,startTime);
        boolean isDone = Objects.equals(a[1], "x");
        if(isDone){
            storedEvent.mark();
        }
        return storedEvent;
    }
    public static Task parseDeadlineFromStorage(String string){
        String[] a = string.split("\\|");
        LocalDate deadline = LocalDate.parse(a[3]);
        Deadline storedEvent =  new Deadline(a[2],deadline);
        boolean isDone = Objects.equals(a[1], "x");
        if(isDone){
            storedEvent.mark();
        }
        return storedEvent;
    }

    public static Task parseTodoFromStorage(String string){
        String[] a = string.split("//|");
        Task storedTask = new Task(a[2]);
        boolean isDone = Objects.equals(a[1], "x");
        if(isDone){
            storedTask.mark();
        }
        return storedTask;
    }

}
