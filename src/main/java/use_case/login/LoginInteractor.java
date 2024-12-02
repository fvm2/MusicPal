package use_case.login;

import entity.User;
import service.Result;
import service.UserService;

/**
 * The Login Interactor.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final UserService userService;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(UserService userService,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userService = userService;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String email = loginInputData.getEmail();
        final String password = loginInputData.getPassword();

        final Result<User> result = userService.login(email, password);

        if (result.isSuccess()) {
            final LoginOutputData loginOutputData = new LoginOutputData(result.getData().getName());
            loginPresenter.prepareSuccessView(loginOutputData);
        }
        else {
            loginPresenter.prepareFailView(result.getError());
        }
    }
}
