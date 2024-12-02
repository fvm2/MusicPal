package use_case.recommend_playlist;

public interface RecommendPlaylistOutputBoundary {

    void showRecommendations(RecommendPlaylistOutputData recommendations);

    void switchToMenuView();
}
