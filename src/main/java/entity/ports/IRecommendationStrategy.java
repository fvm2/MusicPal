package entity.ports;

import java.util.List;

import entity.Recommendation;
import entity.UserPreferences;

/**
 * Placeholder for Checkstyle.
 */
public interface IRecommendationStrategy {
    /**
     * Placeholder for Checkstyle.
     * @param prefs preferences input.
     * @param count number of recommendation input.
     * @param type type of recommendation input.
     * @return List of Recommendations.
     */
    List<Recommendation> getRecommendations(UserPreferences prefs, int count, String type);
}
