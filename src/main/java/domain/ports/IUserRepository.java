package domain.ports;

import java.util.List;
import java.util.Optional;

import domain.Recommendation;
import domain.User;

/**
 * This is temporary, so checkstyle stops bugging me.
 */
public interface IUserRepository {
    void save(User user);
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
    void update(User user);
    void delete(String id);
    void saveRecommendationHistory(String userId, List<Recommendation> recommendations);
    List<Recommendation> getRecommendationHistory(String userId);
}
