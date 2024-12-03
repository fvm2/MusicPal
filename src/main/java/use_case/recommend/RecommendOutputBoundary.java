package use_case.recommend;

public interface RecommendOutputBoundary {

    void popRecommendation(RecommendOutputData recommendOutputData);

    void switchToProfileView();
}
