package use_case.profile;

public class ProfileInteractor implements ProfileInputBoundary {
    final ProfileOutputBoundary profilePresenter;
    public ProfileInteractor(ProfileOutputBoundary profileOutputBoundary) {
        this.profilePresenter = profileOutputBoundary;
    }

    @Override
    public void logout() {
        profilePresenter.logout();
    }

    @Override
    public void showFavorites(ProfileInputData profileInputData) {
        profilePresenter.showFavorites(profileInputData);
    }

    @Override
    public void showFriends(ProfileInputData profileInputData) {
        profilePresenter.showFriends(profileInputData);
    }
}