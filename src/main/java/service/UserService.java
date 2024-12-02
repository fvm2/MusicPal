package service;

import entity.User;
import infrastructure.database.UserRepository;
import dto.UserDTO;
import infrastructure.OpenAIService;
import java.util.Optional;

/**
 * UserService
 * Handles user-related business operations including registration, authentication, and friend management.
 *
 * Key responsibilities:
 * - User registration with OpenAI thread creation
 * - User authentication
 * - Friend request management
 *
 * Dependencies:
 * - UserRepository for data persistence
 * - OpenAIService for AI thread management
 *
 * Usage example:
 * UserService userService = new UserService(userRepository, openAIService);
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
                return Result.failure("Email already exists");
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

    public Result<Void> sendFriendRequest(int fromId, int toId) {
        try {
            Optional<User> toUser = userRepository.findById(toId);
            if (toUser.isEmpty()) {
                return Result.failure("User not found");
            }

            User user = toUser.get();
            if (!user.getFriends().contains(fromId)) {
                user.getFriends().add(fromId);
                userRepository.update(user);
            }
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure("Failed to send friend request: " + e.getMessage());
        }
    }
}
