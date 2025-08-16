public class Avo {
    public static void greet(){
        String greetString =
                """
                        ____________________________________________________________
                         Hello! I'm Avo
                         What can I do for you?
                        ____________________________________________________________
                """;
        System.out.println(greetString);
    }
    public static void bye(){
        String byeString =
                """
                        Bye. Hope to see you again soon!
                        ____________________________________________________________
                """;
        System.out.println(byeString);
    }
    public static void main(String[] args) {
        greet();
        bye();
    }
}
