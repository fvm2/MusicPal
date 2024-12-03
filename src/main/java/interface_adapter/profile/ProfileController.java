package interface_adapter.profile;

import use_case.profile.ProfileInputBoundary;
import use_case.profile.ProfileInputData;

public class ProfileController {
    private final ProfileInputBoundary profileInteractor;

    public ProfileController(ProfileInputBoundary profileInteractor) {
        this.profileInteractor = profileInteractor;
    }

    public void executeLoadProfile(String email) {
        ProfileInputData profileInputData = new ProfileInputData(email);
        profileInteractor.loadProfile(profileInputData);
    }

    public void executeLogout() {
        profileInteractor.logout();
    }

    public void executeBackToMenu() {
        profileInteractor.backToMenu();
    }
}