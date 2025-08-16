import java.util.Objects;
import java.util.Scanner;

public class Avo {
    private static int instructionIndex = 0;
    private static String[] instructions = new String[100];
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
    public static String listString(String[] instructions){
        StringBuilder listString = new StringBuilder();
        for(int i = 0; i< instructions.length;i++){
            if(instructions[i] == null){
                break;
            }
            String line = (i+1) + ". " + instructions[i] + "\n";
            if (i > 0){
                line = "         " + line;
            }
            listString.append(line);
        }
        return listString.toString();
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        greet();
        while(true){
            String input = scanner.nextLine();
            if (Objects.equals(input, "bye")){
                bye();
                break;
            } else if (Objects.equals(input, "list")) {
                System.out.println(borderfy(listString(instructions)));
            } else{
                instructions[instructionIndex] = input;
                instructionIndex++;
                String fullResponse = "added: " + input;
                respond(scanner,fullResponse);
            }
        }
    }
}
