import java.io.File;
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
        File file = new File("data/series.csv");
        System.out.println("File exists: " + file.exists()); // Check if file exists
    }

    public void mainMenu(){
        loadMovies();
        loadSeries();
        System.out.println("total media loaded: " + medias.size());
        while(!loggedIn){
            login.run();
            currentUser = login.getCurrentUser();
            loggedIn = true;
        }

        while(running){
            TextUI.displayMSG("Main Menu");
            TextUI.displayMSG("1. Search for movies or series");
            TextUI.displayMSG("2. See list of watched media");
            TextUI.displayMSG("3. See list of saved Media");
            TextUI.displayMSG("4. Search by genre");
            TextUI.displayMSG("5. Log out");
            TextUI.displayMSG("6. Exit");
           String choice = TextUI.promptText("Enter your choice: ");

           switch(choice){
               case "1":
                   searchMedia();
                   break;
               case "2":
                   displayWatchedMedia();
                   break;
               case "3":
                    displaySavedMedia();
                   break;
               case "4":
                   searchByGenre();
                   break;
               case "5":
                   TextUI.displayMSG("Logging out.");
                   loggedIn = false;
                   mainMenu();
                   break;
               case "6":
                   TextUI.displayMSG("Thank you for using KreinerFlix");
                   login.terminate();
                   running = false;
                   break;
                   default:
                       TextUI.displayMSG("Invalid choice");
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

                // Handle release year as a range
                String releaseYearStr = values[1].trim();
                String[] yearRange = releaseYearStr.split("-"); // Split the year range if it exists
                int releaseYear = Integer.parseInt(yearRange[0].trim()); // Use the start year as releaseYear
                int endYear = (yearRange.length > 1) ? Integer.parseInt(yearRange[1].trim()) : releaseYear; // If end year exists, use it, otherwise set endYear to releaseYear

                ArrayList<String> genres = getGenres(values[2].trim());
                float imdbScore = Float.parseFloat(values[3].trim().replace(",", "."));
                ArrayList<String> seasons = getSeasons(values[4].trim());

                // Add the series object to the parsed list
                parsedSeries.add(new Series(name, releaseYear, genres, imdbScore, seasons));
            }
        }

        series = parsedSeries; // Update the series list with parsed series
        medias.addAll(series);
    }



    private ArrayList<String> getSeasons(String value) {

        ArrayList<String> seasons = new ArrayList<>();
        String[] seasonRanges = value.split(","); // Split by commas to get each season range
        for (String seasonRange : seasonRanges) {
            seasons.add(seasonRange.trim()); // Add each season range as a string
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
                "\nIMDBScore: " + media.getIMDBScore());
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
    public void searchByGenre() {
        String genreInput = TextUI.promptText("Enter the genre you want to search for: ").toLowerCase();
        ArrayList<Media> matchingMedia = new ArrayList<>();

        for (Media media : medias) {
            for (String genre : media.getGenre()) {
                if (genre.toLowerCase().contains(genreInput)) {
                    matchingMedia.add(media);
                    break;
                }
            }
        }
        if (matchingMedia.isEmpty()) {
            TextUI.displayMSG("No media found for the genre: " + genreInput);
            searchByGenre();
        } else {
            TextUI.displayMSG("Found the following media for genre: " + genreInput);
            int counter = 1;
            for (Media media : matchingMedia) {
                TextUI.displayMSG(counter + ". " + media.getMediaName() + " (IMDB Score: " + media.getIMDBScore() + ")");
                counter++;
            }
            // Prompt the user to play media
            int choice = TextUI.promptNumeric("Enter the number of the media you want to play (0 to cancel): ");
            if (choice > 0 && choice <= matchingMedia.size()) {
                currentMedia = matchingMedia.get(choice - 1); // Use 0-indexing
                mediaAction(currentMedia); // Call mediaAction method to save or watch
            } else if (choice == 0) {
                TextUI.displayMSG("Returning to main menu...");
            } else {
                TextUI.displayMSG("Invalid choice, returning to main menu...");
            }
        }
    }

    public void playMedia(){
        TextUI.displayMSG("Now playing " + currentMedia.getMediaName());
        currentUser.addSeenMedia(currentMedia);
        afterWatching();
    }

    public void displayWatchedMedia(){
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
    public void displaySavedMedia(){
        if(currentUser==null){
            TextUI.displayMSG("Error: no user is logged in.");
            return;
        }
        ArrayList<Media>saved = currentUser.getSavedMedia();
        if(saved.isEmpty()) {
            TextUI.displayMSG("Error: no movies has been saved.");
        }else{
            TextUI.displayMSG("Your saved movies: ");
            for (Media media : saved) {
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
