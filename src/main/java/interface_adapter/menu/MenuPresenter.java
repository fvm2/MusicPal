package interface_adapter.menu;

import interface_adapter.ViewManagerModel;
import interface_adapter.artist_recommendation.ArtistRecViewModel;
import use_case.menu.MenuOutputBoundary;

import javax.swing.text.View;

public class MenuPresenter implements MenuOutputBoundary {
    private final MenuViewModel menuViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ArtistRecViewModel artistRecViewModel;

    public MenuPresenter(MenuViewModel menuViewModel, ViewManagerModel viewManagerModel) {
        this.menuViewModel = menuViewModel;
        this.viewManagerModel = viewManagerModel;
        this.artistRecViewModel = new ArtistRecViewModel();
    }

    public void switchToArtistRecView() {
        viewManagerModel.setState(artistRecViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
