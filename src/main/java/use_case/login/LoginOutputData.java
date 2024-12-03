package use_case.login;

/**
 * Implementing the login output data class.
 */
public class LoginOutputData {

    private final String name;
    private final String surname;
    private final String email;
    private final String country;

    public LoginOutputData(String name, String surname, String email, String country) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSurname() {
        return surname;
    }

    public String getCountry() {
        return country;
    }
}
