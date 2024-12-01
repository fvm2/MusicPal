package interface_adapter.menu;

import use_case.menu.MenuInputBoundary;

public class MenuController {
    private final MenuInputBoundary menuInteractor;

    public MenuController(MenuInputBoundary menuInteractor) {
        this.menuInteractor = menuInteractor;
    }

    public void switchToArtistRecView() {
        menuInteractor.switchToArtistRecView();
    }
}
