package domain;

import java.util.ArrayList;
import java.util.List;

public class UserPreferences {
    private List<String> favoriteSongs;
    private List<String> favoriteArtists;

    public UserPreferences() {
        this.favoriteSongs = new ArrayList<>();
        this.favoriteArtists = new ArrayList<>();
    }

    public void addSongWithArtist(String song, String artist) {
        favoriteSongs.add(song + " - " + artist);
    }

    public List<String> getFavoriteSongs() {
        return favoriteSongs;
    }
}
