package use_case.signup;

/**
 * Signup output data class.
 */
public class SignupOutputData {

    private final String name;

    public SignupOutputData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
