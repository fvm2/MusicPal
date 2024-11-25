package entity;

import java.util.ArrayList;
import java.util.List;

public class UserPreferences {
    private List<String> favoriteSongs;
    private List<String> favoriteArtists;
    private List<String> dislikedSongs;

    public UserPreferences() {
        this.favoriteSongs = new ArrayList<>();
        this.favoriteArtists = new ArrayList<>();
        this.dislikedSongs = new ArrayList<>();
    }

    public void addSongWithArtist(String song, String artist) {
        favoriteSongs.add(song + " - " + artist);
    }

    public List<String> getFavoriteSongs() {
        return favoriteSongs;
    }

    public void addDislikedSong(String song) {
        if (!dislikedSongs.contains(song)) {
            dislikedSongs.add(song);
        }
    }

    public List<String> getDislikedSongs() {
        return dislikedSongs;
    }
}
