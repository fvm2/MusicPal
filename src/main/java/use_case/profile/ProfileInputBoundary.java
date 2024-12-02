package use_case.profile;

public interface ProfileInputBoundary {
    void showFavorites(ProfileInputData profileInputData);
    void showFriends(ProfileInputData profileInputData);
    void logout();
}
