package entity;

import junit.framework.TestCase;
import java.util.Arrays;
import java.util.List;

public class PreferenceTest extends TestCase {
    private Preference preference;

    public void setUp() throws Exception {
        super.setUp();
        preference = new Preference();
        preference.setUserId(1);
        preference.setPreferenceId(1);
    }

    public void testGetUserId() {
        assertEquals(1, preference.getUserId());
    }

    public void testSetUserId() {
        preference.setUserId(2);
        assertEquals(2, preference.getUserId());
    }

    public void testGetPreferenceId() {
        assertEquals(1, preference.getPreferenceId());
    }

    public void testSetPreferenceId() {
        preference.setPreferenceId(2);
        assertEquals(2, preference.getPreferenceId());
    }

    public void testAddSong() {
        preference.addSong("Sweet Child O' Mine by Guns N' Roses");
        assertEquals(1, preference.getSongs().size());
        assertTrue(preference.getSongs().contains("Sweet Child O' Mine by Guns N' Roses"));

        // Test invalid format
        try {
            preference.addSong("Invalid Song Format");
            fail("Should throw IllegalArgumentException for invalid song format");
        } catch (IllegalArgumentException e) {
            assertEquals("Song must be in format 'songName by artistName'", e.getMessage());
        }
    }

    public void testAddArtist() {
        preference.addArtist("Guns N' Roses");
        assertEquals(1, preference.getArtists().size());
        assertTrue(preference.getArtists().contains("Guns N' Roses"));
    }

    public void testAddAlbum() {
        preference.addAlbum("Appetite for Destruction by Guns N' Roses");
        assertEquals(1, preference.getAlbums().size());
        assertTrue(preference.getAlbums().contains("Appetite for Destruction by Guns N' Roses"));

        // Test invalid format
        try {
            preference.addAlbum("Invalid Album Format");
            fail("Should throw IllegalArgumentException for invalid album format");
        } catch (IllegalArgumentException e) {
            assertEquals("Album must be in format 'albumName by artistName'", e.getMessage());
        }
    }

    public void testAddGenre() {
        preference.addGenre("Rock");
        assertEquals(1, preference.getGenres().size());
        assertTrue(preference.getGenres().contains("Rock"));
    }

    public void testGetSongs() {
        preference.addSong("Song1 by Artist1");
        preference.addSong("Song2 by Artist2");
        List<String> songs = preference.getSongs();
        assertEquals(2, songs.size());
        assertTrue(songs.contains("Song1 by Artist1"));
        assertTrue(songs.contains("Song2 by Artist2"));
    }

    public void testGetGenres() {
        preference.addGenre("Rock");
        preference.addGenre("Metal");
        List<String> genres = preference.getGenres();
        assertEquals(2, genres.size());
        assertTrue(genres.contains("Rock"));
        assertTrue(genres.contains("Metal"));
    }

    public void testGetArtists() {
        preference.addArtist("Artist1");
        preference.addArtist("Artist2");
        List<String> artists = preference.getArtists();
        assertEquals(2, artists.size());
        assertTrue(artists.contains("Artist1"));
        assertTrue(artists.contains("Artist2"));
    }

    public void testGetAlbums() {
        preference.addAlbum("Album1 by Artist1");
        preference.addAlbum("Album2 by Artist2");
        List<String> albums = preference.getAlbums();
        assertEquals(2, albums.size());
        assertTrue(albums.contains("Album1 by Artist1"));
        assertTrue(albums.contains("Album2 by Artist2"));
    }

    public void testSetSongs() {
        List<String> songs = Arrays.asList("Song1 by Artist1", "Song2 by Artist2");
        preference.setSongs(songs);
        assertEquals(songs, preference.getSongs());
    }

    public void testSetGenres() {
        List<String> genres = Arrays.asList("Rock", "Metal");
        preference.setGenres(genres);
        assertEquals(genres, preference.getGenres());
    }

    public void testSetArtists() {
        List<String> artists = Arrays.asList("Artist1", "Artist2");
        preference.setArtists(artists);
        assertEquals(artists, preference.getArtists());
    }

    public void testSetAlbums() {
        List<String> albums = Arrays.asList("Album1 by Artist1", "Album2 by Artist2");
        preference.setAlbums(albums);
        assertEquals(albums, preference.getAlbums());
    }

    public void testListImmutability() {
        // Test that returned lists cannot modify the internal state
        preference.addSong("Song1 by Artist1");
        List<String> songs = preference.getSongs();
        songs.add("Song2 by Artist2");
        assertEquals(1, preference.getSongs().size());

        preference.addGenre("Rock");
        List<String> genres = preference.getGenres();
        genres.add("Metal");
        assertEquals(1, preference.getGenres().size());
    }

    public void testEmptyLists() {
        Preference newPreference = new Preference();
        assertNotNull(newPreference.getSongs());
        assertNotNull(newPreference.getGenres());
        assertNotNull(newPreference.getArtists());
        assertNotNull(newPreference.getAlbums());
        assertTrue(newPreference.getSongs().isEmpty());
        assertTrue(newPreference.getGenres().isEmpty());
        assertTrue(newPreference.getArtists().isEmpty());
        assertTrue(newPreference.getAlbums().isEmpty());
    }
}