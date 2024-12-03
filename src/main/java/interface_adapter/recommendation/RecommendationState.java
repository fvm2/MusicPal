package interface_adapter.recommendation;

public class RecommendationState {

    private String recommendationName;
    private int recommendationId;

    public void setRecommendationId(int recommendationId) {
        this.recommendationId = recommendationId;
    }

    public void setRecommendationName(String recommendationName) {
        this.recommendationName = recommendationName;
    }

    public String getRecommendationName() {
        return recommendationName;
    }

    public int getRecommendationId() {
        return recommendationId;
    }
}
