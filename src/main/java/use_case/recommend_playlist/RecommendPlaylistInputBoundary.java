package use_case.recommend_playlist;

import java.util.List;

public interface RecommendPlaylistInputBoundary {

    void execute(RecommendPlaylistInputData recommendPlaylistInputData);

    void switchToMenuView();
}
