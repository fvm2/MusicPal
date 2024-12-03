package use_case.profile;

public interface ProfileInputBoundary {
    void loadProfile(ProfileInputData profileInputData);
    void logout();
    void backToMenu();
}
