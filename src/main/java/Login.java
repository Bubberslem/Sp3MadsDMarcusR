
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class Login {
    private static final HashMap<String,String> users = new HashMap<>();
    private static String input;

    public static void login() {
        TextUI.displayMSG("Please login");
        users.put("Mads", "MonkeyMads");
        users.put("Marcus", "Jesperhaderzoom");

        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;

        System.out.println("Welcome to Kreinerflix!");


        while (!loggedIn) {
            TextUI.displayMSG("\n1. Login");
            TextUI.displayMSG("2. Create a new user");
            TextUI.displayMSG("Choose an option: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                loggedIn = login(scanner);
            } else if (choice.equals("2")) {
                createUser(scanner);
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
        KreinerFlix kreinerFlix = new KreinerFlix();

        boolean exit = false;
        while (!exit) {
            System.out.println("\n1. Search for Movies");
            System.out.println("2. Search for Series");
            System.out.println("3. Browse category");
            System.out.println("4. Logout");
            System.out.println("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Enter a search term: ");
                    input = scanner.nextLine();
                    kreinerFlix.SearchMovies(input);
                    break;

                 case "2":
                        System.out.println("Enter a search term: ");
                        input = scanner.nextLine();
                        kreinerFlix.SearchSeries(input);

                        break;
                case "3":
                    System.out.println("Enter a search term: ");
                     input = scanner.nextLine();
                    kreinerFlix.SearchSeries(input);
                    break;

                case "4":
                    exit = true;
                    System.out.println("Logged out. Goodbye!");
                    break;
                default:
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

            if (authenticateUser(username, password)) {
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
        public static boolean authenticateUser (String username, String password){
            return users.containsKey(username) && users.get(username).equals(password);
    }

    public static class UserFileHandler {

        private static void saveUsersToCSV(List<User> user, String filePath) {
            try (FileWriter writer = new FileWriter(filePath)) {
                for (User user : users) {
                    writer.write(user.getUsername() + "," + user.getPassword() + "\n");
                }
                System.out.println("User has been save to " + filePath);
            } catch (IOException e) {
                System.err.println("Error saving user: " + e.getMessage());
            }
        }
    }

    public static class UserFile {

        private static String filePath;

        private static List<User> loadUsersFromCSV(String filePath) {
            UserFile.filePath = filePath;
            List<User> users = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        users.add(new User(parts[0], parts[1]));
                    }
                }
                System.out.println("User has loaded successfully from " + filePath);
            } catch (IOException e) {
                System.err.println("Error loading users: " + e.getMessage());
            }
            return users;
        }
    }
}