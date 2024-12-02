package interface_adapter.signup;

/**
 * The state for the Signup View Model.
 */
public class SignupState {

    private String name = "";
    private String surname = "";
    private String email = "";
    private String country = "";
    private String password = "";

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignupState{"
                + ", password='" + password + '\''
                + '}';
    }
}
