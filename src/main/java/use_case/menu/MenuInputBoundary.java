package use_case.menu;

public interface MenuInputBoundary {
    void openProfile(MenuInputData menuInputData);

    void openArtistRec(MenuInputData menuInputData);
    void openSongRec(MenuInputData menuInputData);
    void openPlayListRec(MenuInputData menuInputData);
    void logout();
}
