import java.util.ArrayList;

public class Series extends Media {
    private ArrayList<String> seasons;

    public Series(String mediaName, int releaseYear, ArrayList<String> genre, float IMDBScore,ArrayList<String> seasons) {
        super(mediaName, releaseYear, genre, IMDBScore);
        this.seasons = seasons;
    }
}
