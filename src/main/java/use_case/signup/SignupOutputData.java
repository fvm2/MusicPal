package use_case.signup;

/**
 * Signup output data class.
 */
public class SignupOutputData {

    private final String name;
    private final String surname;
    private final String email;
    private final String country;

    public SignupOutputData(String name, String surname, String email, String country) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }
}
