package use_case.signup;

/**
 * Signup Input Data.
 */
public class SignupInputData {

    private final String name;
    private final String surname;
    private final String email;
    private final String country;
    private final String password;

    public SignupInputData(String username, String surname, String email, String country, String password) {
        this.name = username;
        this.surname = surname;
        this.email = email;
        this.country = country;
        this.password = password;
    }

    String getName() {
        return name;
    }

    String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    String getCountry() {
        return country;
    }

    String getPassword() {
        return password;
    }

}
