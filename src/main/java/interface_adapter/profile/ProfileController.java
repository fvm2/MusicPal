package interface_adapter.profile;

import use_case.profile.ProfileInputBoundary;
import use_case.profile.ProfileInputData;

/**
 * Controller for the Profile feature.
 */
public class ProfileController {
    private final ProfileInputBoundary profileInteractor;

    public ProfileController(ProfileInputBoundary profileInteractor) {
        this.profileInteractor = profileInteractor;
    }

    public void showProfile(String username) {
        ProfileInputData inputData = new ProfileInputData(username);
        profileInteractor.showProfile(inputData);
    }

    public void logout() {
        profileInteractor.logout();
    }
}
