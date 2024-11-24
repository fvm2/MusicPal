package application;

import entity.Recommendation;
import entity.UserPreferences;
import entity.ports.IOpenAIService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class RecommendationService {
    private final IOpenAIService openAIService;
    private final ObjectMapper objectMapper;

    public RecommendationService(IOpenAIService openAIService) {
        this.openAIService = openAIService;
        this.objectMapper = new ObjectMapper();
    }

    public List<Recommendation> getRecommendations(UserPreferences prefs, int count, String type) {
        String input = String.format("[%s] ; %d ; %s",
                String.join(", ", prefs.getFavoriteSongs()),
                count,
                type);

        String jsonResponse = openAIService.getRecommendationsFromAI(input);
        return parseRecommendations(jsonResponse);
    }

    private List<Recommendation> parseRecommendations(String jsonResponse) {
        List<Recommendation> recommendations = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            if (rootNode.isArray()) {
                for (JsonNode node : rootNode) {
                    recommendations.add(createRecommendationFromNode(node));
                }
            } else {
                recommendations.add(createRecommendationFromNode(rootNode));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error parsing recommendations: " + e.getMessage());
        }
        return recommendations;
    }

    private Recommendation createRecommendationFromNode(JsonNode node) {
        int id = node.get("recommendation_id").asInt();
        String type, content, artist;

        if (node.has("song")) {
            type = "song";
            content = node.get("song").asText();
        } else if (node.has("album")) {
            type = "album";
            content = node.get("album").asText();
        } else {
            type = "artist";
            content = node.get("artist").asText();
            return new Recommendation(id, content, "", type);
        }

        artist = node.get("artist").asText();
        return new Recommendation(id, content, artist, type);
    }
}
