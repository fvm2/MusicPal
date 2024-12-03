package use_case.profile;

public interface ProfileOutputBoundary {
    void presentProfile(ProfileOutputData profileOutputData);
    void presentError(String error);
    void presentLogout();
    void presentBackToMenu();
}