import java.util.ArrayList;

public abstract class Media {
    private String mediaName;
    private int releaseYear;
    private ArrayList<String> genre;
    private float IMDBScore;

    public Media(String mediaName, int releaseYear, ArrayList<String> genre, float IMDBScore) {
        this.mediaName = mediaName;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.IMDBScore = IMDBScore;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public ArrayList<String> getGenre() {
        return this.genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }

    public float getIMDBScore() {
        return IMDBScore;
    }

    public void setIMDBScore(float IMDBScore) {
        this.IMDBScore = IMDBScore;
    }
}