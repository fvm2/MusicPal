package use_case.menu;

public interface MenuInputBoundary {

    void switchToArtistRecView();

    void switchToPlaylistRecView();

    void switchToSongRecView();

    void openPlayListRec(MenuInputData menuInputData);
    void openArtistRec(MenuInputData menuInputData);
    void openSongRec(MenuInputData menuInputData);
    void openProfile(MenuInputData menuInputData);

    void logout();
}