package interface_adapter.playlist;

import use_case.recommend_playlist.RecommendPlaylistInputBoundary;
import use_case.recommend_playlist.RecommendPlaylistInputData;

import java.util.List;

public class PlaylistController {
    private final RecommendPlaylistInputBoundary recommendPlaylistInteractor;

    public PlaylistController(RecommendPlaylistInputBoundary recommendPlaylistInteractor) {
        this.recommendPlaylistInteractor = recommendPlaylistInteractor;
    }

    public void execute(List<String> songs) {
        final RecommendPlaylistInputData recommendPlaylistInputData = new RecommendPlaylistInputData(songs);

        recommendPlaylistInteractor.execute(recommendPlaylistInputData);
    }

    public void switchToMenuView() {
        recommendPlaylistInteractor.switchToMenuView();
    }
}
