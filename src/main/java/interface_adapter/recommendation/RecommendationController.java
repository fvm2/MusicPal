package interface_adapter.recommendation;

import use_case.recommend.RecommendInputBoundary;

public class RecommendationController {

    private final RecommendInputBoundary recommendInteractor;

    public RecommendationController(RecommendInputBoundary recommendInputBoundary) {
        this.recommendInteractor = recommendInputBoundary;
    }

    public void switchToProfileView() {
        recommendInteractor.switchToProfileView();
    }
}
