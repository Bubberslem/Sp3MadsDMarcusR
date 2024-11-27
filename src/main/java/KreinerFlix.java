import java.util.ArrayList;
import java.util.Scanner;

public class KreinerFlix {
    private  ArrayList<Media> medias;
    private ArrayList<Movie> movies;
    private ArrayList<Series> series;
    private ArrayList<User> users;
    private Media currentMedia;
    private User currentUser;
    Login login = new Login();
    boolean running = true;
    boolean loggedIn = false;


    public KreinerFlix() {
        this.users = new ArrayList<User>();
        this.medias = new ArrayList<Media>();
        this.movies = new ArrayList<Movie>();
        this.series = new ArrayList<Series>();
    }

    public void mainMenu(){

        while(!loggedIn){
            login.run();
            currentUser = login.getCurrentUser();
            loggedIn = true;
        }
        Scanner scan = new Scanner(System.in);


        while(running){
            TextUI.displayMSG("Main Menu");
            TextUI.displayMSG("1. Search for media");
            TextUI.displayMSG("2. See list of watched movies");
            TextUI.displayMSG("3. See list of watched series");
            TextUI.displayMSG("4. See list of saved Media");
            TextUI.displayMSG("5. Search by genre");
            TextUI.displayMSG("6. Log out");
            TextUI.displayMSG("7. Exit");
           String choice = TextUI.promptText("Enter your choice: ");

           switch(choice){
               case "1":
                   searchMedia();
                   break;
               case "2":
                   displayWatchedMovies();
                   break;
               case "6":
                   TextUI.displayMSG("Logging out.");
                   loggedIn = false;
                   mainMenu();
                   break;
               case "7":
                   TextUI.displayMSG("Thank you for using KreinerFlix");
                   login.saveUserToFile();
                   running = false;
                   break;


           }
        }
    }


    public void loadMovies() {
        ArrayList<String> lines = FileIO.readData("data/movies.csv"); // Load lines from the file
        ArrayList<Movie> parsedMovies = new ArrayList<>(); // Temporary list for parsed movies

        for (String line : lines) {
            String[] values = line.split(";"); // Split the line into values based on `;`
            if (values.length >= 4) { // Ensure the line has enough data
                String name = values[0].trim();
                int releaseYear = Integer.parseInt(values[1].trim());
                ArrayList<String> genres = getGenres(values[2].trim());
                float imdbScore = Float.parseFloat(values[3].trim().replace(",", ".")); // Replace ',' with '.'
                parsedMovies.add(new Movie(name, releaseYear, genres, imdbScore)); // Create and add Movie object
            }
        }

        movies = parsedMovies; // Update the movies list with parsed movies
        medias.addAll(movies);
    }

    public void loadSeries() {
        ArrayList<String> lines = FileIO.readData("data/series.csv"); // Load lines from the file
        ArrayList<Series> parsedSeries = new ArrayList<>(); // Temporary list for parsed series

        for (String line : lines) {
            String[] values = line.split(";"); // Split the line into values based on `;`
            if (values.length >= 5) { // Ensure the line has enough data
                String name = values[0].trim();
                int releaseYear = Integer.parseInt(values[1].trim());
                ArrayList<String> genres = getGenres(values[2].trim());
                float imdbScore = Float.parseFloat(values[3].trim().replace(",", "."));
                ArrayList<String> seasons = getSeasons(values[4].trim());
                parsedSeries.add(new Series(name, releaseYear, genres, imdbScore, seasons)); // Create and add Series object
            }
        }

        series = parsedSeries; // Update the series list with parsed series
        medias.addAll(series);
    }

    private ArrayList<String> getSeasons(String seasonsString) {
        ArrayList<String> seasons = new ArrayList<>();
        String[] seasonsArray = seasonsString.split(","); // Split by commas
        for (String season : seasonsArray) {
            seasons.add(season.trim()); // Trim spaces and add each season
        }
        return seasons;
    }


    public void searchMedia() {
        String input = TextUI.promptText("Enter search term: ");
        for (int i = 0; i < medias.size(); i++) {
            if (medias.get(i).getMediaName().equalsIgnoreCase(input)) {
                currentMedia = medias.get((i));
                mediaAction(medias.get(i));
            }
        }
    }

    public void mediaAction(Media media) {
        TextUI.displayMSG("Title: " + media.getMediaName() +
                "\nIMDBScore:" + media.getIMDBScore());
       mediaMenu();
    }

    private void mediaMenu() {
        if(currentUser == null){
            TextUI.displayMSG("Error: no user is logged in.");
            return;
        }

        String choice;

        // Check if current media is already saved by the user
        if (currentUser.getSaved().contains(currentMedia)) {
            // Display options if the media is saved
            choice = TextUI.promptText("You have the following options: Type 'Play' to play, Type 'Remove' to Remove from list, Type 'Menu' to return to the Main menu\n ");
        } else {
            // Display options if the media is not saved
            choice = TextUI.promptText("You have the following options: Type 'Play' to play, Type 'add' to add to list, Type 'Menu' to return to the Main menu\n ");
        }

        // Use switch to handle user choice
        switch (choice.toLowerCase()) {
            case "play":
                playMedia(); // Play media
                break;

            case "remove":
                if (currentUser.getSaved().contains(currentMedia)) {
                    currentUser.removeSavedMedia(currentMedia); // Remove media from saved list
                }
                break;

            case "add":
                if (!currentUser.getSaved().contains(currentMedia)) {
                    currentUser.addSavedMedia(currentMedia); // Add media to saved list
                    TextUI.displayMSG("Added Media to saved list: " + currentMedia.getMediaName());
                }
                break;

            case "menu":
                mainMenu(); // Go back to main menu
                break;

            default:
                TextUI.displayMSG("Invalid choice. Please try again");
                mediaMenu(); // Ask the user for input again if the choice is invalid
                break;
        }
    }


    private ArrayList<String> getGenres(String genreString) {
        ArrayList<String> genres = new ArrayList<>();
        String[] genreArray = genreString.split(","); // Split by commas
        for (String genre : genreArray) {
            genres.add(genre.trim()); // Trim spaces and add each genre
        }
        return genres;
    }

    public void playMedia(){
        TextUI.displayMSG("Now playing " + currentMedia.getMediaName());
        currentUser.addSeenMedia(currentMedia);
        afterWatching();
    }

    public void displayWatchedMovies(){
        if(currentUser==null){
            TextUI.displayMSG("Error: no user is logged in.");
            return;
        }
        ArrayList<Media>watched = currentUser.getSeenMedia();
        if(watched.isEmpty()) {
            TextUI.displayMSG("Error: no movies has been watched.");
        }else{
            TextUI.displayMSG("Your watched movies: ");
            for (Media media : watched) {
                TextUI.displayMSG(media.getMediaName());
            }
        }
    }

    public void afterWatching() {
        String choice = TextUI.promptText("Thanks for watching. Go back to main menu(Menu) or exit(Exit): ");
        switch (choice.toLowerCase()) {
            case "menu":
                mainMenu();
                break;
            case "exit":
                running = false;
                TextUI.displayMSG("Thank you for using KreinerFlix");
                break;
            default:
                TextUI.displayMSG("Invalid choice try again.");
                afterWatching();
                break;
        }
    }
}
