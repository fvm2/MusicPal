package interface_adapter.song_recommendation;

import interface_adapter.ViewManagerModel;
import interface_adapter.artist_recommendation.ArtistRecViewModel;
import interface_adapter.artist_recommendation.ArtistState;
import interface_adapter.menu.MenuViewModel;
import use_case.recommend_artist.RecommendArtistOutputData;
import use_case.recommend_song.RecommendSongOutputBoundary;
import use_case.recommend_song.RecommendSongOutputData;

public class SongRecPresenter implements RecommendSongOutputBoundary {
    private final SongRecViewModel songRecViewModel;
    private final ViewManagerModel viewManagerModel;
    private final MenuViewModel menuViewModel;

    public SongRecPresenter(SongRecViewModel songRecViewModel, ViewManagerModel viewManagerModel, MenuViewModel menuViewModel) {
        this.songRecViewModel = songRecViewModel;
        this.viewManagerModel = viewManagerModel;
        this.menuViewModel = menuViewModel;
    }

    @Override
    public void showRecommendations(RecommendSongOutputData recommendations) {
        final SongState songState = songRecViewModel.getState();
        songState.setRecommendation(recommendations.getRecommendations());
        this.songRecViewModel.setState(songState);
        songRecViewModel.firePropertyChanged();

        viewManagerModel.setState(songRecViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    public void switchToMenuView() {
        viewManagerModel.setState(menuViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
