package use_case.menu;

public class MenuInteractor implements MenuInputBoundary {

    private final MenuOutputBoundary menuPresenter;
    public MenuInteractor(MenuOutputBoundary menuOutputBoundary){
        this.menuPresenter = menuOutputBoundary;
    }

    @Override
    public void switchToArtistRecView() {
        menuPresenter.switchToArtistRecView();
    }

    @Override
    public void switchToPlaylistRecView() {
        menuPresenter.switchToPlaylistRecView();
    }

    @Override
    public void switchToSongRecView() { menuPresenter.switchToSongRecView(); }

    @Override
    public void switchToProfileView() {
        menuPresenter.switchToProfileView();
    }
}

