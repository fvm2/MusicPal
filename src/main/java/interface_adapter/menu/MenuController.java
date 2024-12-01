package interface_adapter.menu;

import use_case.menu.MenuInputBoundary;
import use_case.menu.MenuInputData;

public class MenuController {
    private final MenuInputBoundary menuInteractor;

    public MenuController(MenuInputBoundary menuInteractor) {
        this.menuInteractor = menuInteractor;
    }

    public void openProfile(String username) {
        menuInteractor.openProfile(new MenuInputData(username));
    }

    public void openArtistRecommendations(String username) {
        menuInteractor.openArtistRec(new MenuInputData(username));
    }

    public void openSongRecommendations(String username) {
        menuInteractor.openSongRec(new MenuInputData(username));
    }

    public void openPlaylistRecommendations(String username) {
        menuInteractor.openPlayListRec(new MenuInputData(username));
    }

    public void logout() {
        menuInteractor.logout();
    }
}
