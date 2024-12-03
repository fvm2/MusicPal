package interface_adapter.profile;

import java.util.ArrayList;
import java.util.List;

public class ProfileState {
    private String email = "";
    private String name = "";
    private String surname = "";
    private String country = "";
    private String error;
    private List<String> recommendationHistory = new ArrayList<>();

    // Getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getRecommendationHistory() { return new ArrayList<>(recommendationHistory); }
    public void setRecommendationHistory(List<String> history) {
        this.recommendationHistory = new ArrayList<>(history);
    }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}