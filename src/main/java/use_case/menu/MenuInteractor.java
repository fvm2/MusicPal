package use_case.menu;

public class MenuInteractor implements MenuInputBoundary {
    private final MenuOutputBoundary menuPresenter;

    public MenuInteractor(MenuOutputBoundary menuPresenter) {
        this.menuPresenter = menuPresenter;
    }

    @Override
    public void openProfile(MenuInputData menuInputData) {
        MenuOutputData menuOutputData = new MenuOutputData(menuInputData.username());
        menuPresenter.openProfile(menuOutputData);
    }

    @Override
    public void openArtistRec(MenuInputData menuInputData) {
        MenuOutputData menuOutputData = new MenuOutputData(menuInputData.username());
        menuPresenter.openArtistRec(menuOutputData);
    }

    @Override
    public void openSongRec(MenuInputData menuInputData) {
        MenuOutputData menuOutputData = new MenuOutputData(menuInputData.username());
        menuPresenter.openSongRec(menuOutputData);
    }

    @Override
    public void openPlayListRec(MenuInputData menuInputData) {
        MenuOutputData menuOutputData = new MenuOutputData(menuInputData.username());
        menuPresenter.openPlayListRec(menuOutputData);
    }

    @Override
    public void logout() {
        menuPresenter.logout();
    }
}