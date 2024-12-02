package interface_adapter.profile;

import use_case.profile.ProfileInputBoundary;
import use_case.profile.ProfileInputData;

public class ProfileController {

    private final ProfileInputBoundary profileInputBoundary;
    private final ProfileViewModel profileViewModel;

    public ProfileController(ProfileInputBoundary profileInputBoundary, ProfileViewModel profileViewModel) {
        this.profileInputBoundary = profileInputBoundary;
        this.profileViewModel = profileViewModel;
    }

    public void showFavorites() {
        String username = profileViewModel.getState().getUsername();
        ProfileInputData inputData = new ProfileInputData(username);
        profileInputBoundary.showFavorites(inputData);
    }

    public void showFriends() {
        String username = profileViewModel.getState().getUsername();
        ProfileInputData inputData = new ProfileInputData(username);
        profileInputBoundary.showFriends(inputData);
    }

    public void logout() {
        profileInputBoundary.logout();
    }
}