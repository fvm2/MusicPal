package use_case.recommend_song;

public class RecommendSongOutputData {
    private String recommendations;
    public RecommendSongOutputData(String recommendations) {
        this.recommendations = recommendations;
    }
    public String getRecommendations() {
        return recommendations;
    }
}
