package interface_adapter.artist_recommendation;

import use_case.recommend_artist.RecommendArtistOutputData;

public class ArtistRecViewModel {
    private final String recommendations;

    public ArtistRecViewModel(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getRecommendations() {
        return recommendations;
    }
}
