package parser;

import Exceptions.IncompleteInputException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import Commands.Command;
import Exceptions.UnknownCommandException;
import Exceptions.EmptyInstructionException;
import java.time.LocalDate;
import java.util.Objects;

public class Parser {
    public static Command parseCommand(String input) throws UnknownCommandException {
        try {
            return Command.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnknownCommandException();
        }
    }
    public static Deadline parseDeadline(String input) throws EmptyInstructionException {
        if (!input.startsWith("deadline")) {
            throw new IllegalArgumentException("Input must start with 'deadline'");
        }

        String[] parts = input.split(" /by ");
        if (parts.length != 2) {
            throw new IncompleteInputException("deadline <instruction> /by <date>");
        }

        String instruction = parts[0].substring("deadline".length()).trim();
        if (instruction.isEmpty()) {
            throw new EmptyInstructionException();
        }

        String deadlineDateString = parts[1].trim();
        LocalDate deadlineDate = LocalDate.parse(deadlineDateString);

        return new Deadline(instruction, deadlineDate);
    }

    public static Event parseEvent(String input) throws EmptyInstructionException {
        if (!input.startsWith("event")) {
            throw new IllegalArgumentException("Input must start with 'event'");
        }

        String[] parts = input.split(" /from | /to ");
        if (parts.length != 3) {
            throw new IncompleteInputException("event <instruction> /from <date> /to <date>");
        }

        String eventInstruction = parts[0].substring("event".length()).trim();
        if (eventInstruction.isEmpty()) {
            throw new EmptyInstructionException();
        }

        String startTimeString = parts[1].trim();
        String endTimeString = parts[2].trim();

        LocalDate startTime = LocalDate.parse(startTimeString);
        LocalDate endTime = LocalDate.parse(endTimeString);

        return new Event(eventInstruction, startTime, endTime);
    }

    public static Task parseTask(String input) throws EmptyInstructionException {
        if (!input.startsWith("todo")) {
            throw new IllegalArgumentException("Input must start with 'todo'");
        }
        String instruction = input.substring("todo".length()).trim();
        if (instruction.isEmpty()) {
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
