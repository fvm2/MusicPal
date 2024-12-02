package use_case.recommend_song;

public interface RecommendSongInputBoundary {

    void execute(RecommendSongInputData recommendSongInputData);

    void switchToMenuView();
}
