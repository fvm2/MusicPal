package use_case.recommend_song;

public interface RecommendSongOutputBoundary {

    void showRecommendations(RecommendSongOutputData recommendations);

    void switchToMenuView();
}
