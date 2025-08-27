package avo.parser;
import java.time.LocalDate;
import java.util.Objects;

import avo.commands.Command;
import avo.exceptions.EmptyInstructionException;
import avo.exceptions.IncompleteInputException;
import avo.exceptions.UnknownCommandException;
import avo.tasks.Deadline;
import avo.tasks.Event;
import avo.tasks.Task;


/**
 * contains all methods used to interpret strings
 */
public class Parser {
    /**
     * interprets strings as commands
     */
    public static Command parseCommand(String input) throws UnknownCommandException {
        try {
            return Command.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnknownCommandException();
        }
    }

    /**
     * Converts a valid user input string to a {@code Deadline}.
     *
     * @param input the input string from the user
     * @return a {@code Deadline} object created from the input
     * @throws EmptyInstructionException if the input is empty or invalid
     */
    public static Deadline parseDeadline(String input) throws EmptyInstructionException, IncompleteInputException {
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

    /**
     * Converts a valid user input string to a {@code Event}.
     *
     * @param input the input string from the user
     * @return a {@code Event} object created from the input
     * @throws EmptyInstructionException if the input is empty or invalid
     */
    public static Event parseEvent(String input) throws EmptyInstructionException, IncompleteInputException {
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

    /**
     * Converts a valid user input string to a {@code Task}.
     *
     * @param input the input string from the user
     * @return a {@code Task} object created from the input
     * @throws EmptyInstructionException if the input is empty or invalid
     */
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

    /**
     * Converts storage string to the task that it represents
     * @param entryString storage string of the task it represents
     * @return the task that the input string represents
     */
    public static Task parseTaskFromStorage(String entryString) {
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

    /**
     * Converts storage string to the event that it represents
     * @param string storage string for a event
     * @return a {@code Event} object created from the storage string
     */
    public static Task parseEventFromStorage(String string) {
        String[] a = string.split("\\|");
        LocalDate startTime = LocalDate.parse(a[3].strip());
        LocalDate endTime = LocalDate.parse(a[4].strip());
        Event storedEvent = new Event(a[2], endTime, startTime);
        boolean isDone = Objects.equals(a[1], "x");
        if (isDone) {
            storedEvent.mark();
        }
        return storedEvent;
    }

    /**
     * Converts storage string to the deadline that it represents
     * @param string storage string for a deadline
     * @return a {@code Deadline} object created from the storage string
     */
    public static Task parseDeadlineFromStorage(String string) {
        String[] a = string.split("\\|");
        LocalDate deadline = LocalDate.parse(a[3]);
        Deadline storedEvent = new Deadline(a[2], deadline);
        boolean isDone = Objects.equals(a[1], "x");
        if (isDone) {
            storedEvent.mark();
        }
        return storedEvent;
    }
    /**
     * Converts storage string to the to-do that it represents
     * @param string storage string for a to-do
     * @return a {@code Task} object created from the storage string
     */
    public static Task parseTodoFromStorage(String string) {
        String[] a = string.split("//|");
        Task storedTask = new Task(a[2]);
        boolean isDone = Objects.equals(a[1], "x");
        if (isDone) {
            storedTask.mark();
        }
        return storedTask;
    }
}
