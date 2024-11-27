package entity;

import java.util.List;

public class User {
    private int id;
    private String name;
    private String surname;
    private String country;
    private String password;
    private List<User> friends;
    private UserPreferences userPreferences;

    public User(String name, String surname, String country, String password) {
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.password = password;
    }

    public int getUserId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCountry() {
        return country;
    }
}
