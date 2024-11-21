
import java.util.HashMap;
import java.util.Scanner;


public class Login {
    private static final HashMap<String,String> users = new HashMap<>();

    public static void login() {
        users.put("Mads", "MonkeyMads");
        users.put("Marcus", "Jesperhaderzoom");

        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;

        System.out.println("Welcome to Kreinerflix!");


        while (!loggedIn) {
            System.out.println("\n1. Login");
            System.out.println("2. Create a new user");
            System.out.println("Choose an option: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                loggedIn = login(scanner);
            } else if (choice.equals("2")) {
                createUser(scanner);
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
            scanner.close();
        }
        private static boolean login(Scanner scanner){
            System.out.println("Please log in to continue.");
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (authenticate(username, password)) {
                System.out.println("Login successful! Welcome, " + username + "!");
                return true;
            } else {
                System.out.println("Invalid username or password. Please try again.");
                return false;

            }
        }


private static void createUser(Scanner scanner){
            System.out.println("Create a new user.");
            System.out.println("Enter a new username: ");
            String newUsername = scanner.nextLine();

            if (users.containsKey(newUsername)) {
                System.out.println("Username already exists. Please try again.");
            } else {
                System.out.println("Enter a new password: ");
                String newPassword = scanner.nextLine();
                users.put(newUsername, newPassword);
                System.out.println("User created sucessfully! You can now login.");
            }
        }

                private static boolean authenticate (String username, String password){
                    return users.containsKey(username) && users.get(username).equals(password);
                }


            }