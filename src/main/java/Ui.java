import java.util.Scanner;

public class Ui {
    public static void greet(){
        String greetString = """
                    Hello! I'm Avo
                    Let me organise your tasks for the day
                    Input your tasks as such:
                    Todo - todo <instruction>
                    Deadline - deadline <instruction> /by <deadline in YYYY-MM-DD>
                    Event - event <instruction> /from <start date in YYYY-MM-DD> /to <end date in YYYY-MM-DD>
                """;
        System.out.println(borderfy(greetString));

    }
    public static void bye(){
        String byeString = "Bye. Hope to see you again soon!";
        System.out.println(borderfy(byeString).stripTrailing());
    }
    public static String borderfy(String s){
        String output = String.format(
                """
                        ____________________________________________________________
                         %s
                        ____________________________________________________________
                """,s);
        return output;
    }
    public static void respond(String response){
        String borderfiedResponse = borderfy(response);
        System.out.println(borderfiedResponse);
    }
    public static void uiLoop() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        int indexSelected;
        while (running) {
            try {
                String input = scanner.nextLine();
                String[] words = input.split(" ");
                String firstWord = words[0];
                Command command = Avo.parseCommand(firstWord);
                switch (command) {
                    case BYE:
                        Ui.bye();
                        running = false;
                        break;
                    case LIST:
                        String showList = "Here are the tasks in your list:";
                        System.out.println(Ui.borderfy(showList + Avo.listString(Avo.tasks)));
                        break;
                    case MARK:
                        indexSelected = Integer.parseInt(words[1]) - 1;
                        Avo.mark(indexSelected);
                        break;
                    case UNMARK:
                        indexSelected = Integer.parseInt(words[1]) - 1;
                        Avo.unmark(indexSelected);
                        break;
                    case DEADLINE:
                        Deadline currentDeadline = Deadline.parseDeadline(input.strip());
                        Avo.addToList(currentDeadline);
                        break;
                    case EVENT:
                        Event currentEvent = Event.parseEvent(input.strip());
                        Avo.addToList(currentEvent);
                        break;
                    case TODO:
                        Task currentTask = Task.parseTask(input.strip());
                        Avo.addToList(currentTask);
                        break;
                    case DELETE:
                        indexSelected = Integer.parseInt(words[1]) - 1;
                        Avo.deleteTask(indexSelected);
                        break;
                    default:
                        throw new UnknownCommandException();
                }
            } catch (UnknownCommandException | EmptyInstructionException | InvalidIndexException e) {
                Ui.respond(e.getMessage());
            }
        }
    }
}
