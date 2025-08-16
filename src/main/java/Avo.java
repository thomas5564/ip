import java.util.Objects;
import java.util.Scanner;

public class Avo {
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
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        greet();
        while(true){
            String input = scanner.nextLine();
            if (!Objects.equals(input, "bye")){
                respond(scanner,input);
            }else{
                bye();
                break;
            }
        }
    }
}
