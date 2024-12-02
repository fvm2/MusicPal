package use_case.signup;

public class SignupInputData {

    final private String username;
    final private String password;
    final private String repeatPassword;
    final private String country;
    final private String surname;

    public SignupInputData(String username, String password, String repeatPassword, String country, String surname) {
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.country = country;
        this.surname = surname;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    String getCountry() { return country;}

    String getSurname() {
        return surname;
    }


    public String getRepeatPassword() {
        return repeatPassword;
    }

}