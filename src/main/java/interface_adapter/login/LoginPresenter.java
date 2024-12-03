package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.menu.MenuState;
import interface_adapter.menu.MenuViewModel;
import interface_adapter.profile.ProfileState;
import interface_adapter.profile.ProfileViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final MenuViewModel menuViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ProfileViewModel profileViewModel;

    public LoginPresenter(LoginViewModel loginViewModel, MenuViewModel menuViewModel,
                          ViewManagerModel viewManagerModel, ProfileViewModel profileViewModel) {
        this.loginViewModel = loginViewModel;
        this.menuViewModel = menuViewModel;
        this.viewManagerModel = viewManagerModel;
        this.profileViewModel = profileViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        final ProfileState profileState = profileViewModel.getState();
        profileState.setName(response.getName());
        profileState.setEmail(response.getEmail());
        profileState.setSurname(response.getSurname());
        profileState.setCountry(response.getCountry());
        profileViewModel.setState(profileState);
        profileViewModel.firePropertyChanged();

        // On success, switch to the menu view.
        final MenuState menuState = menuViewModel.getState();
        this.menuViewModel.setState(menuState);
        this.menuViewModel.firePropertyChanged();
        this.viewManagerModel.setState(menuViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        loginViewModel.firePropertyChanged();
    }
}
