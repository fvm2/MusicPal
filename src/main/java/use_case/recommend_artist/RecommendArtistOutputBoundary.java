package use_case.recommend_artist;

public interface RecommendArtistOutputBoundary {
    void showRecommendations(RecommendArtistOutputData recommendations);

    void switchToRecView();

    void switchToMenuView();
}
