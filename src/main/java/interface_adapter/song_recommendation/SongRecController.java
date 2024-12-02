package interface_adapter.song_recommendation;

import use_case.recommend_song.RecommendSongInputBoundary;
import use_case.recommend_song.RecommendSongInputData;

import java.util.List;

public class SongRecController {
    private final RecommendSongInputBoundary recommendSongInteractor;

    public SongRecController(RecommendSongInputBoundary recommendSongInteractor) {
        this.recommendSongInteractor = recommendSongInteractor;
    }

    public void execute(List<String> songs) {
        final RecommendSongInputData recommendSongInputData = new RecommendSongInputData(songs);

        recommendSongInteractor.execute(recommendSongInputData);
    }

    public void switchToMenuView() {
        recommendSongInteractor.switchToMenuView();
    }
}
