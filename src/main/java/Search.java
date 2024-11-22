import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Search {
    private final List<String> movies = new ArrayList<>();
    private final List<String> series = new ArrayList<>();

    public Search() {
LoadData("movies.csv", movies);
LoadData("series.csv", series);
    }
    private void LoadData(String fileName, List<String> list) {
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                list.add(fileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + fileName + " not found. Please make sure it exists.");
        }
    }
    public void Search(String query) {
        System.out.println("\nSearch Results for \""+ query +"\"");
        boolean found = false;

        System.out.println("\nMovies: ");
        for (String movie : movies) {
            if (movie.toLowerCase().contains(query.toLowerCase())) {
                System.out.println("-"+movie);
                found = true;
            }
        }
        System.out.println("\nSeries: ");
        for (String series : series) {
            if (series.toLowerCase().contains(query.toLowerCase())) {
                System.out.println("-"+series);
                found = true;
            }
        }
        if (found) {
            System.out.println("No matches found");
        }
    }
}
