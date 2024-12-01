package use_case.recommend_playlist;

public class RecommendPlaylistOutputData {
    private String recommendations;
    public RecommendPlaylistOutputData(String recommendations) {
        this.recommendations = recommendations;
    }
    public String getRecommendations() {
        return recommendations;
    }
}
