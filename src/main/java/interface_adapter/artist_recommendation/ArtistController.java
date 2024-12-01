package interface_adapter.artist_recommendation;

import use_case.recommend_artist.RecommendArtistInputBoundary;
import use_case.recommend_artist.RecommendArtistInputData;

public class ArtistController {
    private final RecommendArtistInputBoundary recommendArtistInteractor;

    public ArtistController(RecommendArtistInputBoundary recommendArtistInteractor) {
        this.recommendArtistInteractor = recommendArtistInteractor;
    }

    public void execute(String artistName) {
        RecommendArtistInputData recommendArtistInputData = new RecommendArtistInputData(artistName);

        recommendArtistInteractor.execute(recommendArtistInputData);
    }

    public void switchToRecView() {
        recommendArtistInteractor.switchToRecView();
    }

    public void switchToMenuView() {
        recommendArtistInteractor.switchToMenuView();
    }
}
