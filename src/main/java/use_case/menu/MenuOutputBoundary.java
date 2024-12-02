package use_case.menu;

public interface MenuOutputBoundary {
    void switchToArtistRecView();

    void switchToPlaylistRecView();

    void switchToSongRecView();

    void openProfile(MenuOutputData menuOutputData);

    void openArtistRec(MenuOutputData menuOutputData);

    void openPlayListRec(MenuOutputData menuOutputData);
    void openSongRec(MenuOutputData menuOutputData);

    void logout();
}