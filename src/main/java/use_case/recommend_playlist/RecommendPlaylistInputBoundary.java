package use_case.recommend_playlist;

public interface RecommendPlaylistInputBoundary {

    void execute(RecommendPlaylistInputData recommendPlaylistInputData);

    void switchToMenuView();
}
