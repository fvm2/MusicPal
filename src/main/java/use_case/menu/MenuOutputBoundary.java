package use_case.menu;

public interface MenuOutputBoundary {
    void openProfile(MenuOutputData menuOutputData);
    void openArtistRec(MenuOutputData menuOutputData);
    void openPlayListRec(MenuOutputData menuOutputData);
    void openSongRec(MenuOutputData menuOutputData);
    void logout();
}