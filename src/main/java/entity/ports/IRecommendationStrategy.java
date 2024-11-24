package entity.ports;

import entity.Recommendation;
import entity.UserPreferences;

import java.util.List;

public interface IRecommendationStrategy {
    List<Recommendation> getRecommendations(UserPreferences prefs, int count, String type);
}
