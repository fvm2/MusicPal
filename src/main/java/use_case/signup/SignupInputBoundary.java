package use_case.signup;

/**
 * Signup Input Boundary interface.
 */
public interface SignupInputBoundary {

    /**
     * Signup input data class.
     * @param signupInputData the sign up input data.
     */
    void execute(SignupInputData signupInputData);

    /**
     * Switch to login view class.
     */
    void switchToLoginView();

    void switchMenuView();
}
