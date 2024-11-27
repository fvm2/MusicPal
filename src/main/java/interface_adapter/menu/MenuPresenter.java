package interface_adapter.menu;

import use_case.menu.MenuOutputBoundary;
import use_case.menu.MenuOutputData;

public class MenuPresenter implements MenuOutputBoundary {
    private final MenuViewModel menuViewModel;

    public MenuPresenter(MenuViewModel menuViewModel) {
        this.menuViewModel = menuViewModel;
    }

    @Override
    public void openProfile(MenuOutputData menuOutputData) {
        menuViewModel.setScreen("Profile");
        menuViewModel.setUsername(menuOutputData.username());
    }

    @Override
    public void openArtistRec(MenuOutputData menuOutputData) {
        menuViewModel.setScreen("Artist Recommendations");
        menuViewModel.setUsername(menuOutputData.username());
    }

    @Override
    public void openPlayListRec(MenuOutputData menuOutputData) {
        menuViewModel.setScreen("Playlist Recommendations");
        menuViewModel.setUsername(menuOutputData.username());
    }

    @Override
    public void openSongRec(MenuOutputData menuOutputData) {
        menuViewModel.setScreen("Song Recommendations");
        menuViewModel.setUsername(menuOutputData.username());
    }

    @Override
    public void logout() {
        menuViewModel.setScreen("Login");
    }
}


