package use_case.login;

import entity.User;

/**
 * The Login Interactor.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginOutputBoundary loginOutputBoundary) {
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();
        // TODO: Check if user is registered.
        if (1 == 1) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
        }
        else {
            final String pwd = "user password fetched from the db.";
            if (!password.equals(pwd)) {
                loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            }
            else {

                // TODO: Get the user from the database.
                final User user = User();

                final LoginOutputData loginOutputData = new LoginOutputData(user.getName());
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }
}
