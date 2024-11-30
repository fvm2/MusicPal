package use_case.recommend_artist;

public class RecommendArtistOutputData {
    private String recommendations;

    public RecommendArtistOutputData(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getRecommendations() {
        return recommendations;
    }
}
