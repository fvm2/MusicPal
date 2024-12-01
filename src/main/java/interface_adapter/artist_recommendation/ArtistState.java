package interface_adapter.artist_recommendation;

/**
 * The state for the Artist View Model.
 */
public class ArtistState {
    private String artistName;
    private String artistNameError;
    private String recommendation = "";
    private String recommendationError;

    public String getRecommendation() {
        return recommendation;
    }

    public String getRecommendationError() {
        return recommendationError;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistNameError() {
        return artistNameError;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public void setRecommendationError(String usernameError) {
        this.recommendationError = recommendationError;
    }

    public void setArtistNameError(String artistNameError) {
        this.artistNameError = artistNameError;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public String toString() {
        return "ArtistState{"
                + "artist='" + recommendation + '}';
    }
}

