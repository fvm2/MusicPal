package entity.ports;

import java.util.List;
import java.util.Optional;

import entity.Recommendation;
import entity.User;

/**
 * This is temporary, so checkstyle stops bugging me.
 */
public interface IUserRepository {
    /**
     * Saves the user data.
     * @param user
     */
    void save(User user);

    /**
     * Finds a user by user_id.
     * @param id user_id
     * @return User or null.
     */
    Optional<User> findById(String id);


    Optional<User> findByEmail(String email);
    void update(User user);
    void delete(String id);
    void saveRecommendationHistory(String userId, List<Recommendation> recommendations);
    List<Recommendation> getRecommendationHistory(String userId);
}
