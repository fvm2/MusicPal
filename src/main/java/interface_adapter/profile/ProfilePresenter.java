package interface_adapter.profile;

import interface_adapter.ViewManagerModel;
import use_case.profile.ProfileOutputBoundary;
import use_case.profile.ProfileOutputData;

/**
 * Presenter for the Profile feature.
 */
public class ProfilePresenter implements ProfileOutputBoundary {
    private final ProfileViewModel profileViewModel;
    private final ViewManagerModel viewManagerModel;

    public ProfilePresenter(ProfileViewModel profileViewModel, ViewManagerModel viewManagerModel) {
        this.profileViewModel = profileViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void presentProfile(ProfileOutputData outputData) {
        ProfileState state = profileViewModel.getState();
        state.setUsername(outputData.getUsername());
        state.setFavorites(outputData.getFavorites());
        state.setFriends(outputData.getFriends());
        profileViewModel.setState(state);
        profileViewModel.firePropertyChanged();
    }

    @Override
    public void logout() {
        viewManagerModel.setState("log in");
        viewManagerModel.firePropertyChanged();
    }
}
