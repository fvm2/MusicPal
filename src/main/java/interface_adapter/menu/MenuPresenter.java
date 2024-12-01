package interface_adapter.menu;

import interface_adapter.ViewManagerModel;
import interface_adapter.artist_recommendation.ArtistRecViewModel;
import interface_adapter.playlist.PlaylistRecViewModel;
import use_case.menu.MenuOutputBoundary;

public class MenuPresenter implements MenuOutputBoundary {
    private final MenuViewModel menuViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ArtistRecViewModel artistRecViewModel;
    private final PlaylistRecViewModel playlistRecViewModel;

    public MenuPresenter(MenuViewModel menuViewModel, ViewManagerModel viewManagerModel) {
        this.menuViewModel = menuViewModel;
        this.viewManagerModel = viewManagerModel;
        this.artistRecViewModel = new ArtistRecViewModel();
        this.playlistRecViewModel = new PlaylistRecViewModel();
    }

    public void switchToArtistRecView() {
        viewManagerModel.setState(artistRecViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    public void switchToPlaylistRecView() {
        viewManagerModel.setState(playlistRecViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
