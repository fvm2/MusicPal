package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;

public class User {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private final String userId;
    private String name;
    private String email;
    private String passwordHash;
    private String country;
    private UserPreferences preferences;
    private final List<Recommendation> recommendationHistory;

    // Constructor for new user registration
    public User(String userId, String name, String email, String rawPassword, String country) {
        validateParameters(userId, name, email, rawPassword, country);
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.passwordHash = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        this.country = country;
        this.preferences = new UserPreferences();
        this.recommendationHistory = new ArrayList<>();
    }

    // Static factory method for database reconstruction
    public static User createFromDatabase(String userId, String name, String email,
                                          String existingPasswordHash, String country) {
        return new User(userId, name, email, existingPasswordHash, country, true);
    }

    // Private constructor used by the factory method
    private User(String userId, String name, String email, String passwordHash,
                 String country, boolean isFromDatabase) {
        validateParameters(userId, name, email, passwordHash, country);
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.country = country;
        this.preferences = new UserPreferences();
        this.recommendationHistory = new ArrayList<>();
    }

    private void validateParameters(String userId, String name, String email, String password, String country) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getCountry() {
        return country;
    }

    public UserPreferences getPreferences() {
        return preferences;  // Consider returning a copy if UserPreferences is mutable
    }

    public List<Recommendation> getRecommendationHistory() {
        return Collections.unmodifiableList(recommendationHistory);
    }

    // Setters with validation
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public void setEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }

    public void setCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }
        this.country = country;
    }

    public void updatePreferences(UserPreferences preferences) {
        if (preferences == null) {
            throw new IllegalArgumentException("Preferences cannot be null");
        }
        this.preferences = preferences;
    }

    public void updatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verifyPassword(String password) {
        return password != null && BCrypt.checkpw(password, this.passwordHash);
    }

    public void addToRecommendationHistory(List<Recommendation> recommendations) {
        if (recommendations == null) {
            throw new IllegalArgumentException("Recommendations cannot be null");
        }
        this.recommendationHistory.addAll(recommendations);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}