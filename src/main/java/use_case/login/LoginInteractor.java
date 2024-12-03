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
        final Result<User> result = userService.login(loginInputData.getEmail(), loginInputData.getPassword());

        if (result.isSuccess()) {
            final User user = result.getData();
            final LoginOutputData loginOutputData = new LoginOutputData(
                    user.getName(),
                    user.getSurname(),
                    user.getEmail(),
                    user.getCountry()
            );
            loginPresenter.prepareSuccessView(loginOutputData);
        }
        else {
            loginPresenter.prepareFailView(result.getError());
        }
    }
}
