package use_case.login;

/**
 * Implementing the login output data class.
 */
public class LoginOutputData {

    private final String username;

    public LoginOutputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
