package interface_adapter.profile;

import interface_adapter.ViewManagerModel;
import use_case.profile.ProfileOutputBoundary;
import use_case.profile.ProfileOutputData;

public class ProfilePresenter implements ProfileOutputBoundary {

    private final ProfileViewModel profileViewModel;
    private final ViewManagerModel viewManagerModel;

    public ProfilePresenter(ProfileViewModel profileViewModel, ViewManagerModel viewManagerModel) {
        this.profileViewModel = profileViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void showFavorites(ProfileOutputData profileOutputData) {
        ProfileState state = profileViewModel.getState();
        state.setUsername(profileOutputData.username());
        state.setFavorites(profileOutputData.favorites());
        profileViewModel.setState(state);
        profileViewModel.firePropertyChanged("favorites");
    }

    @Override
    public void showFriends(ProfileOutputData profileOutputData) {
        ProfileState state = profileViewModel.getState();
        state.setUsername(profileOutputData.username());
        state.setFriends(profileOutputData.friends());
        profileViewModel.setState(state);
        profileViewModel.firePropertyChanged("friends");
    }

    @Override
    public void logout() {
        viewManagerModel.setState("log in");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        ProfileState state = profileViewModel.getState();
        state.setError(error);
        profileViewModel.setState(state);
        profileViewModel.firePropertyChanged("error");
    }
}
