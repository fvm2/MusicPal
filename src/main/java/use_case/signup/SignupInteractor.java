package use_case.signup;

/**
 * The Signup Interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupOutputBoundary userPresenter;

    public SignupInteractor(SignupOutputBoundary signupOutputBoundary) {
        this.userPresenter = signupOutputBoundary;
    }

    @Override
    public void execute(SignupInputData signupInputData) {

        // TODO: Check to see if the user is already registered. In that case, send the user to login page.
        if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Passwords don't match.");
        }
        else {
            final User user = new User(signupInputData.getUsername(), signupInputData.getSurname(),
                    signupInputData.getCountry(), signupInputData.getPassword());

            final SignupOutputData signupOutputData = new SignupOutputData(user.getName());
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}
