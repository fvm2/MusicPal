package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a user in the music recommendation system.
 * This class contains all user-related information and friend relationships.
 */
public class User {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String country;
    private String password;
    private String thread;
    private List<Integer> friends;

    public User() {
        this.friends = new ArrayList<>();
    }

    public User(String name, String surname, String email, String country, String password) {
        this();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.country = country;
        this.password = password;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public List<Integer> getFriends() {
        return new ArrayList<>(friends);
    }

    public void setFriends(List<Integer> friends) {
        this.friends = new ArrayList<>(friends);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
