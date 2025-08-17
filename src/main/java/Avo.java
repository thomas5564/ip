import java.util.Scanner;

public class Avo {
    private static int taskIndex = 0;
    private static Task[] tasks = new Task[100];
    public static void greet(){
        String greetString = "Hello! I'm Avo \n         What can I do for you?";
        System.out.println(borderfy(greetString));
    }
    public static void bye(){
        String byeString = "Bye. Hope to see you again soon!";
        System.out.println(borderfy(byeString));
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
    public static void respond(Scanner scanner,String response){
        String borderfiedResponse = borderfy(response);
        System.out.println(borderfiedResponse);
    }
    public static String listString(Task[] tasks){
        StringBuilder listString = new StringBuilder();
        for(int i = 0; i< tasks.length; i++){
            if(tasks[i] == null){
                break;
            }
            String line = "\n         " + (i+1) + "."+ tasks[i].toString();
            listString.append(line);
        }
        return listString.toString();
    }
    public static void unmark(int index){
        String output;
        tasks[index].unmark();
        output = "OK, I've marked this task as not done yet:"+listString(tasks);
        System.out.println(borderfy(output));
    }
    public static void mark(int index){
        String output;
        tasks[index].mark();
        output = "Nice! I've marked this task as done:"+listString(tasks);
        System.out.println(borderfy(output));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        greet();
        boolean running = true;
        int indexSelected;
        while(running){
            String input = scanner.nextLine();
            Task currentTask = new Task(input);
            String[] words = input.split( " ");
            String firstWord = words[0];
            switch(firstWord){
                case "bye":
                    bye();
                    running = false;
                    break;
                case "list":
                    System.out.println("Here are the tasks in your list:");
                    System.out.println(borderfy(listString(tasks)));
                    break;
                case "mark":
                    indexSelected = Integer.parseInt(words[1]) - 1;
                    mark(indexSelected);
                    break;
                case "unmark":
                    indexSelected = Integer.parseInt(words[1]) - 1;
                    unmark(indexSelected);
                    break;
                default:
                    tasks[taskIndex] = currentTask;
                    taskIndex++;
                    String fullResponse = "added: " + input;
                    respond(scanner,fullResponse);
            }
        }
    }
}
