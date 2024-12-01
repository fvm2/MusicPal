package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents user preferences for music recommendations.
 * Stores lists of favorite songs, genres, artists, and albums.
 */
public class Preference {
    private int userId;
    private int preferenceId;
    private List<String> songs;
    private List<String> genres;
    private List<String> artists;
    private List<String> albums;

    public Preference() {
        this.songs = new ArrayList<>();
        this.genres = new ArrayList<>();
        this.artists = new ArrayList<>();
        this.albums = new ArrayList<>();
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(int preferenceId) {
        this.preferenceId = preferenceId;
    }

    /**
     * List management for songs.
     * @param song new song to be added.
     */
    public void addSong(String song) throws IllegalArgumentException {
        if (!song.contains(" by ")) {
            throw new IllegalArgumentException("Song must be in format 'songName by artistName'");
        }
        songs.add(song);
    }

    /**
     * List management for artists.
     * @param artist new artist to be added.
     */
    public void addArtist(String artist) {
        artists.add(artist);
    }

    /**
     * List management for albums.
     * @param album new album to be added.
     */
    public void addAlbum(String album) throws IllegalArgumentException {
        if (!album.contains(" by ")) {
            throw new IllegalArgumentException("Album must be in format 'albumName by artistName'");
        }
        albums.add(album);
    }

    /**
     * List management for genres.
     * @param genre new genre to be added.
     */
    public void addGenre(String genre) {
        genres.add(genre);
    }

    public List<String> getSongs() {
        return new ArrayList<>(songs);
    }

    public List<String> getGenres() {
        return new ArrayList<>(genres);
    }

    public List<String> getArtists() {
        return new ArrayList<>(artists);
    }

    public List<String> getAlbums() {
        return new ArrayList<>(albums);
    }

    public void setSongs(List<String> songs) {
        this.songs = new ArrayList<>(songs);
    }

    public void setGenres(List<String> genres) {
        this.genres = new ArrayList<>(genres);
    }

    public void setArtists(List<String> artists) {
        this.artists = new ArrayList<>(artists);
    }

    public void setAlbums(List<String> albums) {
        this.albums = new ArrayList<>(albums);
    }
}
