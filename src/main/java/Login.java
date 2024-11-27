
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class Login {
    private ArrayList<User> users = new ArrayList<>();
    private User currentUser;


    public User getCurrentUser() {
        return currentUser;
    }

    public void run() {
        System.out.println("Welcome to Kreinerflix!");
        loadUserFromFile();
        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;

        while (!loggedIn) {
            TextUI.displayMSG("1. Login");
            TextUI.displayMSG("2. Create a new user");
            TextUI.displayMSG("Choose an option: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                loggedIn = login();
            } else if (choice.equals("2")) {
                createUser();
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
        TextUI.displayMSG("You logged in!");

    }

    private boolean login() {
        TextUI.displayMSG("Please log in to continue.");

        String username = TextUI.promptText("Enter username: ");

        String password = TextUI.promptText("Enter password: ");

        for (User s : users) {
        if (s.getUsername().equalsIgnoreCase(username) && s.getPassword().equalsIgnoreCase(password)) {
            System.out.println("Login successful! Welcome, " + username + "!");
            currentUser = s;
            return true;
        }
    }
                System.out.println("Invalid username or password. Please try again. ");
                return false;
}

        private void createUser(){
            TextUI.displayMSG("Create a new user.");
            String username = TextUI.promptText("Enter a new username: ");

            if (isUsernameTaken(username)) {
                TextUI.displayMSG("Username already exists. Please try again.");
            } else {
                String password = TextUI.promptText("Enter a password: ");

                users.add(new User(username, password));
                System.out.println("User created sucessfully! You can now login.");
            }
        }

        public boolean isUsernameTaken (String username){
            for (User s : users) {
                if (s.getUsername().equalsIgnoreCase(username)) {
                    return true;
                }
            }
            return false;
    }

    public void saveUserToFile(){
        ArrayList<String> userData = new ArrayList<>();
        for(User s : users){
            userData.add(s.getUsername()+","+s.getPassword());
        }
        FileIO.saveData(userData,"data/userdata.csv");
    }

    public void loadUserFromFile(){
        ArrayList<String> userData = FileIO.readData("data/userdata.csv");
        for (String s : userData) {
            String[] data = s.split(",");
            if (data.length == 2) {
                users.add(new User(data[0], data[1]));
            }
        }
    }
}