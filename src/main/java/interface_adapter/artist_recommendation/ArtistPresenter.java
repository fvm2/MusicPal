package interface_adapter.artist_recommendation;

import use_case.recommend_artist.RecommendArtistInteractor;
import use_case.recommend_artist.RecommendArtistOutputBoundary;
import use_case.recommend_artist.RecommendArtistOutputData;
import use_case.recommender.Recommender;
import view.ArtistRecView;

public class ArtistPresenter implements RecommendArtistOutputBoundary {
    private final ArtistRecViewModel artistRecViewModel;

    public ArtistPresenter(ArtistRecViewModel artistRecViewModel) {
        this.artistRecViewModel = artistRecViewModel;
    }

    @Override
    public void showRecommendations(RecommendArtistOutputData recommendations) {
        final ArtistRecViewModel artistRecViewModel = new ArtistRecViewModel(recommendations.getRecommendations());
    }
}
