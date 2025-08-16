import java.util.Objects;
import java.util.Scanner;

public class Avo {
    private static int instructionIndex = 0;
    private static Instruction[] instructions = new Instruction[100];
    public static void greet(){
        String greetString = "Hello! I'm Avo \n         What can I do for you?";
        System.out.println(borderfy(greetString));
    }
    public static void bye(){
        String byeString = "Bye. Hope to see you again soon!";
        System.out.println(borderfy(byeString));
    }
    public static void mark(int index){

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
    public static String listString(Instruction[] instructions){
        StringBuilder listString = new StringBuilder();
        for(int i = 0; i< instructions.length;i++){
            if(instructions[i] == null){
                break;
            }
            String mark = instructions[i].getIsDone()?"[x]":"[]";
            String line = "\n         " + (i+1) + "."+ mark + instructions[i].getText();
            listString.append(line);
        }
        return listString.toString();
    }
    public static void handleMarking(boolean isMarking, int index){
        String output;
        if (isMarking) {
            instructions[index].mark();
            output = "Nice! I've marked this task as done:"+listString(instructions);
        } else {
            instructions[index].unmark();
            output = "OK, I've marked this task as not done yet:"+listString(instructions);
        }
        System.out.println(borderfy(output));
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        greet();
        boolean running = true;
        while(running){
            String input = scanner.nextLine();
            Instruction currentInstruction = new Instruction(input);
            String[] words = input.split( " ");
            String firstWord = words[0];
            switch(firstWord){
                case "bye":
                    bye();
                    running = false;
                    break;
                case "list":
                    System.out.println("Here are the tasks in your list:");
                    System.out.println(borderfy(listString(instructions)));
                    break;
                case "mark":
                case "unmark": {
                    int indexSelected = Integer.parseInt(words[1]) - 1;
                    boolean isMarking = firstWord.equals("mark");
                    handleMarking(isMarking,indexSelected);
                    break;
                }
                default:
                    instructions[instructionIndex] = currentInstruction;
                    instructionIndex++;
                    String fullResponse = "added: " + input;
                    respond(scanner,fullResponse);
            }
        }
    }
}
