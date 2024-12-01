package use_case.recommend_artist;

public interface RecommendArtistInputBoundary {

    void execute(RecommendArtistInputData inputData);

    void switchToRecView();

    void switchToMenuView();
}
