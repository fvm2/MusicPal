package use_case.profile;

public interface ProfileOutputBoundary {
    void showFavorites(ProfileOutputData profileOutputData);
    void showFriends(ProfileOutputData profileOutputData);
    void logout();
    void prepareFailView(String error);
}