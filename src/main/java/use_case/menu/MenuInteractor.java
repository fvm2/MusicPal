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

}