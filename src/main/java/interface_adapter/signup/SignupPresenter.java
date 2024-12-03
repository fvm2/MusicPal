package interface_adapter.signup;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.menu.MenuViewModel;
import interface_adapter.profile.ProfileState;
import interface_adapter.profile.ProfileViewModel;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;

/**
 * The Presenter for the Signup Use Case.
 */
public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;
    private final MenuViewModel menuViewModel;
    private final ProfileViewModel profileViewModel;

    public SignupPresenter(ViewManagerModel viewManagerModel,
                           SignupViewModel signupViewModel,
                           LoginViewModel loginViewModel, MenuViewModel menuViewModel,
                           ProfileViewModel profileViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
        this.menuViewModel = menuViewModel;
        this.profileViewModel = profileViewModel;
    }

    @Override
    public void prepareSuccessView(SignupOutputData response) {
        // On success, switch to the login view.
        final ProfileState profileState = profileViewModel.getState();
        profileState.setName(response.getName());
        profileState.setSurname(response.getSurname());
        profileState.setEmail(response.getEmail());
        profileState.setCountry(response.getCountry());
        profileViewModel.setState(profileState);
        profileViewModel.firePropertyChanged();

        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        signupViewModel.firePropertyChanged();
    }

    @Override
    public void switchToLoginView() {
        System.out.println("trying to implement the switchToLoginView method");
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToMenuView() {
        viewManagerModel.setState(menuViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
