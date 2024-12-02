package use_case.menu;

public class MenuInteractor implements MenuInputBoundary{

    final MenuOutputBoundary menuPresenter;
    public MenuInteractor(MenuOutputBoundary menuOutputBoundary){
        this.menuPresenter = menuOutputBoundary;
    }

    @Override
    public void openProfile(MenuInputData menuInputData){
        MenuOutputData menuOutputData = new MenuOutputData(menuInputData.getUsername());
        menuPresenter.openProfile(menuOutputData);
    }

    @Override
    public void openArtistRec(MenuInputData menuInputData) {
        MenuOutputData menuOutputData = new MenuOutputData(menuInputData.getUsername());
        menuPresenter.openArtistRec(menuOutputData);
    }

    @Override
    public void openSongRec(MenuInputData menuInputData) {
        MenuOutputData menuOutputData = new MenuOutputData(menuInputData.getUsername());
        menuPresenter.openSongRec(menuOutputData);
    }

    @Override
    public void openPlayListRec(MenuInputData menuInputData){
        MenuOutputData menuOutputData = new MenuOutputData(menuInputData.getUsername());
        menuPresenter.openPlayListRec(menuOutputData);
    }

    @Override
    public void logout(){menuPresenter.logout();}

}