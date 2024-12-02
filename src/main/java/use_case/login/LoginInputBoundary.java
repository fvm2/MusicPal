package use_case.login;

/**
 * Login Input Boundary Implementation.
 */
public interface LoginInputBoundary {

    /**
     * Executing.
     * @param loginInputData the login input data.
     */
    void execute(LoginInputData loginInputData);

}
