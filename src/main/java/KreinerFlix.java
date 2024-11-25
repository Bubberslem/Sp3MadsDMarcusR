import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KreinerFlix {
    private ArrayList<Movies> movies;
    private ArrayList<Series> series;

    public KreinerFlix() {

      movies = FileIO.readData("data/movies.csv");
      series = FileIO.readData("data/series.csv");
    }

    public void loginOrRegister(){
        Login.login();


    }


    public void loadMovies() {
        ArrayList<String> data = FileIO.readData("data/movie.txt");
        for (String s : data) {
            String[] values = s.replace(" ", "").split(";");
            movies.add(new Movie(values[0], Integer.parseInt(values[1]), getGenres(values[2]), Float.parseFloat(values[3].replace(",", "."))));
        }
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
