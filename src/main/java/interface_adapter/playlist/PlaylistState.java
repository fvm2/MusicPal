package interface_adapter.playlist;

import java.util.List;

/**
 * The state for the Playlist View Model.
 */
public class PlaylistState {
    private int numSongs;
    private String numSongsError;
    private String recommendation = "";
    private String recommendationError;

    public String getRecommendation() {
        return recommendation;
    }

    public String getRecommendationError() {
        return recommendationError;
    }

    public int getNumSongs() {
        return numSongs;
    }

    public String getNumSongsError() {
        return numSongsError;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public void setRecommendationError(String usernameError) {
        this.recommendationError = recommendationError;
    }

    public void setNumSongsError(String numSongsError) {
        this.numSongsError = numSongsError;
    }

    public void setNumSongs(int numSongs) {
        this.numSongs = numSongs;
    }

    @Override
    public String toString() {
        return "PlaylistState{"
                + "Requested Songs='" + numSongs + '\''
                + ", Recommended Songs='" + recommendation + '\''
                + '}';
    }
}


