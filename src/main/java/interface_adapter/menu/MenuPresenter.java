package interface_adapter.menu;

import interface_adapter.ViewManagerModel;
import interface_adapter.artist_recommendation.ArtistRecViewModel;
import interface_adapter.playlist.PlaylistRecViewModel;
import interface_adapter.song_recommendation.SongRecViewModel;
import use_case.menu.MenuOutputBoundary;

public class MenuPresenter implements MenuOutputBoundary {
    private final MenuViewModel menuViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ArtistRecViewModel artistRecViewModel;
    private final PlaylistRecViewModel playlistRecViewModel;
    private final SongRecViewModel songRecViewModel;

    public MenuPresenter(MenuViewModel menuViewModel, ViewManagerModel viewManagerModel) {
        this.menuViewModel = menuViewModel;
        this.viewManagerModel = viewManagerModel;
        this.artistRecViewModel = new ArtistRecViewModel();
        this.playlistRecViewModel = new PlaylistRecViewModel();
        this.songRecViewModel = new SongRecViewModel();
    }

    @Override
    public void switchToArtistRecView() {
        viewManagerModel.setState(artistRecViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToPlaylistRecView() {
        viewManagerModel.setState(playlistRecViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToSongRecView() {
        viewManagerModel.setState(songRecViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
