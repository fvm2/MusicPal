package domain.ports;

import domain.Recommendation;
import domain.UserPreferences;

import java.util.List;

public interface IRecommendationStrategy {
    List<Recommendation> getRecommendations(UserPreferences prefs, int count, String type);
}
