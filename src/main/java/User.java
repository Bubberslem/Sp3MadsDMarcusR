import java.util.ArrayList;
import java.util.Collection;

public class User {
    private String username;
    private String password;
    private ArrayList<Media> seenMedia;
    private ArrayList<Media> savedMedia;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.seenMedia = new ArrayList<>();
        this.savedMedia = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Media> getSeenMedia() {
        return seenMedia;
    }

    public void setSeenMedia(ArrayList<Media> seenMedia) {
        this.seenMedia = seenMedia;
    }

    public ArrayList<Media> getSavedMedia() {
        return savedMedia;
    }

    public void setSavedMedia(ArrayList<Media> savedMedia) {
        this.savedMedia = savedMedia;
    }
    public void addSavedMedia(Media media) {
        savedMedia.add(media);
    }

    public void removeSavedMedia(Media media) {
        savedMedia.remove(media);
    }
    public void addSeenMedia(Media media) {
        seenMedia.add(media);
    }

    @Override
    public String toString() {
        return "User: " + username;
    }

    public ArrayList<Media> getSaved() {
        return savedMedia;
    }
}
