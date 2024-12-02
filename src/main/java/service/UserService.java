package service;

import entity.User;
import infrastructure.database.UserRepository;
import dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import service.OpenAIService;
import service.Result;
import java.util.Optional;

/**
 * UserService
 * Handles user-related business operations including registration, authentication, and friend management.
 * <p>
 * Key responsibilities:
 * - User registration with OpenAI thread creation
 * - User authentication
 * - Friend request management
 * <p>
 * Dependencies:
 * - UserRepository for data persistence
 * - OpenAIService for AI thread management
 * <p>
 * Usage example:
 * UserService = new UserService(userRepository, openAIService);
 * Result<User> result = userService.register(new UserDTO("John", "Doe", "john@email.com", "USA", "password"));
 */
public class UserService {
    private final UserRepository userRepository;
    private final OpenAIService openAIService;

    public UserService(UserRepository userRepository, OpenAIService openAIService) {
        this.userRepository = userRepository;
        this.openAIService = openAIService;
    }

    public Result<User> register(UserDTO userDTO) {
        try {
            if (userRepository.findByEmail(userDTO.email()).isPresent()) {
                return service.Result.failure("Email already exists");
            }

            User user = new User(
                    userDTO.name(),
                    userDTO.surname(),
                    userDTO.email(),
                    userDTO.country(),
                    userDTO.password()
            );

            // Create OpenAI thread for user
            openAIService.initialize();
            user.setThread(openAIService.createThread());

            userRepository.save(user);
            return Result.success(user);
        } catch (Exception e) {
            return Result.failure("Registration failed: " + e.getMessage());
        }
    }

    public Result<User> login(String email, String password) {
        try {
            if (!userRepository.verifyPassword(email, password)) {
                return Result.failure("Invalid credentials");
            }
            return Result.success(userRepository.findByEmail(email).orElseThrow());
        } catch (Exception e) {
            return Result.failure("Login failed: " + e.getMessage());
        }
    }

    public Result<User> getUserByEmail(String email) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            return userOptional.map(Result::success).orElseGet(() -> Result.failure("User not found"));
        } catch (Exception e) {
            return Result.failure("Failed to retrieve user: " + e.getMessage());
        }
    }

    public List<String> getUserEmailsByIds(List<Integer> userIds) {
        List<String> emails = new ArrayList<>();
        for (Integer id : userIds) {
            Optional<User> userOptional = userRepository.findById(id);
            userOptional.ifPresent(user -> emails.add(user.getEmail()));
        }
        return emails;
    }
}