package use_case.recommend;


public interface RecommendInputBoundary {

    void execute(RecommendInputData inputData);

    void switchToProfileView();

}
