import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KreinerFlix {
    private ArrayList<Movie> movies;
    private ArrayList<Series> series;

    public KreinerFlix() {

      movies = FileIO.readData("data/movies.csv");
      series = FileIO.readData("data/series.csv");
    }

    public void loginOrRegister(){
        Login.login();


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
    }

    private ArrayList<String> getGenres(String genreString) {
        ArrayList<String> genres = new ArrayList<>();
        String[] genreArray = genreString.split(","); // Split by commas
        for (String genre : genreArray) {
            genres.add(genre.trim()); // Trim spaces and add each genre
        }
        return genres;
    }


    public void SearchMovies(String query) {
        System.out.println("\nSearch Results for \"" + query + "\"");
        boolean found = false;
        System.out.println("\nMovies: ");
        for (Movie m : movies) {
            if (m.toLowerCase().contains(query.toLowerCase())) {
                System.out.println("-" + m);
                found = true;
            }
        }
        if (found) {
            System.out.println("No matches found");
        }
    }
    public void SearchSeries(String query) {
        System.out.println("\nSearch Results for \"" + query + "\"");
        boolean found = false;
        System.out.println("\nSeries: ");
        for (Series s : series) {
            if (s.toLowerCase().contains(query.toLowerCase())) {
                System.out.println("-"+series);
                found = true;
            }
        }
        if (found) {
            System.out.println("No matches found");
        }
    }
}
