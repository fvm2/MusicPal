package use_case.menu;

public interface MenuInputBoundary {

    void openPlayListRec(MenuInputData menuInputData);
    void openArtistRec(MenuInputData menuInputData);
    void openSongRec(MenuInputData menuInputData);
    void openProfile(MenuInputData menuInputData);

    void logout();

}