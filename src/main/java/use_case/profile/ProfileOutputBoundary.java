package use_case.profile;

public interface ProfileOutputBoundary {

    void showFavorites(ProfileInputData profileInputData);
    void showFriends(ProfileInputData profileInputData);

    void logout();
}
