import java.util.Scanner;

public class TextUI {
    private static Scanner scan = new Scanner(System.in);

    public static void displayMSG(String msg){
        System.out.println(msg);
    }

    public static String promptText(String msg){
        System.out.print(msg); //Stil brugeren et spørgsmål
        String input = scan.nextLine();
        return input;
    }

    public static int promptNumeric(String msg){
        System.out.print(msg); //Stil brugeren et spørgsmål
        String input = scan.nextLine();
        int number;
        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e){
            System.out.print("Please type a number: ");
            number = promptNumeric(msg);
        }
        return number;

    }

    public static boolean promptBinary(String msg){
        String input = promptText(msg);
        if(input.equalsIgnoreCase("l")){
            return true;
        }else if(input.equalsIgnoreCase("r")){
            return false;
        } else {
            return promptBinary(msg);
        }
    }
}
