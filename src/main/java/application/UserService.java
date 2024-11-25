package application;

import domain.User;
import domain.Recommendation;
import domain.ports.IUserRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public class UserService {
    private final IUserRepository userRepository;
    private User currentUser;  // Store current logged-in user

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registerUser(String name, String email, String password, String country) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            return false;
        }

        User newUser = new User(UUID.randomUUID().toString(), name, email, password, country);
        userRepository.save(newUser);
        currentUser = newUser;
        return true;
    }

    public boolean login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().verifyPassword(password)) {
            currentUser = user.get();
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void updateUserPreferences(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        userRepository.update(user);
        // Update current user if it's the same user
        if (currentUser != null && currentUser.getUserId().equals(user.getUserId())) {
            currentUser = user;
        }
    }

    public void saveRecommendationHistory(List<Recommendation> recommendations) {
        if (currentUser != null) {
            userRepository.saveRecommendationHistory(currentUser.getUserId(), recommendations);
            currentUser.addToRecommendationHistory(recommendations);
        }
    }

    public List<Recommendation> getRecommendationHistory() {
        if (currentUser != null) {
            return userRepository.getRecommendationHistory(currentUser.getUserId());
        }
        return List.of();
    }
}
