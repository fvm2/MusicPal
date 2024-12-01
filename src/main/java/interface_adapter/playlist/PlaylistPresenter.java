package interface_adapter.playlist;

import interface_adapter.ViewManagerModel;
import interface_adapter.artist_recommendation.ArtistRecViewModel;
import interface_adapter.artist_recommendation.ArtistState;
import interface_adapter.menu.MenuViewModel;
import use_case.recommend_artist.RecommendArtistOutputData;
import use_case.recommend_playlist.RecommendPlaylistOutputBoundary;
import use_case.recommend_playlist.RecommendPlaylistOutputData;

public class PlaylistPresenter implements RecommendPlaylistOutputBoundary {
    private final PlaylistRecViewModel playlistRecViewModel;
    private final ViewManagerModel viewManagerModel;
    private final MenuViewModel menuViewModel;

    public PlaylistPresenter(PlaylistRecViewModel playlistRecViewModel, ViewManagerModel viewManagerModel, MenuViewModel menuViewModel) {
        this.playlistRecViewModel = playlistRecViewModel;
        this.viewManagerModel = viewManagerModel;
        this.menuViewModel = menuViewModel;
    }

    @Override
    public void showRecommendations(RecommendPlaylistOutputData recommendations) {
        final PlaylistState playlistState = playlistRecViewModel.getState();
        playlistState.setRecommendation(recommendations.getRecommendations());
        this.playlistRecViewModel.setState(playlistState);
        playlistRecViewModel.firePropertyChanged();

        viewManagerModel.setState(playlistRecViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    public void switchToMenuView() {
        viewManagerModel.setState(menuViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}


