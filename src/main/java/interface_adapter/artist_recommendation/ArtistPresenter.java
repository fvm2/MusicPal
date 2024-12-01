package interface_adapter.artist_recommendation;

import interface_adapter.ViewManagerModel;
import interface_adapter.menu.MenuViewModel;
import use_case.recommend_artist.RecommendArtistOutputBoundary;
import use_case.recommend_artist.RecommendArtistOutputData;

public class ArtistPresenter implements RecommendArtistOutputBoundary {
    private final ArtistRecViewModel artistRecViewModel;
    private final ViewManagerModel viewManagerModel;
    private final MenuViewModel menuViewModel;

    public ArtistPresenter(ArtistRecViewModel artistRecViewModel, ViewManagerModel viewManagerModel, MenuViewModel menuViewModel) {
        this.artistRecViewModel = artistRecViewModel;
        this.viewManagerModel = viewManagerModel;
        this.menuViewModel = menuViewModel;
    }

    @Override
    public void showRecommendations(RecommendArtistOutputData recommendations) {
        final ArtistState artistState = artistRecViewModel.getState();
        artistState.setRecommendation(recommendations.getRecommendations());
        this.artistRecViewModel.setState(artistState);
        artistRecViewModel.firePropertyChanged();

        viewManagerModel.setState(artistRecViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    public void switchToRecView() {
        viewManagerModel.setState(artistRecViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    public void switchToMenuView() {
        viewManagerModel.setState(menuViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}

