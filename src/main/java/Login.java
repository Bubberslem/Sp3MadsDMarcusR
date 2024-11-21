
import java.util.HashMap;
import java.util.Scanner;


public class Login {
    private static final HashMap<String,String> users = new HashMap<>();

    public static void login(){
        users.put("Mads", "MonkeyMads");
        users.put("Marcus","Jesperhaderzoom");

        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;

        System.out.println("Welcome to Kreinerflix!");
        System.out.println("Please login to continue.");

        while (!loggedIn) {
            System.out.println("Enter username: ");
            String username = scanner.nextLine();

            System.out.println("Enter password: ");
            String password = scanner.nextLine();

            if (authenticate(username, password)) {
                System.out.println("Login successful! Welcome, " + username + "!");
                loggedIn = true;
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        }
        scanner.close();
    }
    private static boolean authenticate(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}

