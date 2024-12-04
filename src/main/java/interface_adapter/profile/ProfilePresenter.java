package interface_adapter.profile;

import use_case.profile.ProfileOutputBoundary;
import use_case.profile.ProfileOutputData;
import interface_adapter.ViewManagerModel;

public class ProfilePresenter implements ProfileOutputBoundary {
    private final ProfileViewModel profileViewModel;
    private final ViewManagerModel viewManagerModel;

    public ProfilePresenter(ProfileViewModel profileViewModel, ViewManagerModel viewManagerModel) {
        this.profileViewModel = profileViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void presentProfile(ProfileOutputData data) {
        ProfileState currentState = profileViewModel.getState();
        currentState.setEmail(data.email());
        currentState.setName(data.name());
        currentState.setSurname(data.surname());
        currentState.setCountry(data.country());
        currentState.setRecommendationHistory(data.recommendationHistory());
        currentState.setError(null);
        profileViewModel.setState(currentState);
        profileViewModel.firePropertyChanged();
    }

    @Override
    public void presentError(String error) {
        ProfileState currentState = profileViewModel.getState();
        currentState.setError(error);
        profileViewModel.setState(currentState);
        profileViewModel.firePropertyChanged();
    }

    @Override
    public void presentLogout() {
        viewManagerModel.setState("log in");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void presentBackToMenu() {
        viewManagerModel.setState("menu");
        viewManagerModel.firePropertyChanged();
    }
}