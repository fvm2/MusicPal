package application;

import entity.Recommendation;
import entity.UserPreferences;
import entity.ports.IOpenAIService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class RecommendationService {
    private final IOpenAIService openaiService;
    private final ObjectMapper objectMapper;

    public RecommendationService(IOpenAIService openaiService) {
        this.openaiService = openaiService;
        this.objectMapper = new ObjectMapper();
    }

    public List<Recommendation> getRecommendations(UserPreferences prefs, int count, String type) {
        // Remove disliked songs from the favoriteSongs list
        prefs.getDislikedSongs().forEach(song -> prefs.getFavoriteSongs().removeIf(fav -> fav.contains(song)));

        final String input = String.format("[%s] ; %d ; %s",
                String.join(", ", prefs.getFavoriteSongs()),
                count,
                type);

        final String jsonResponse = openaiService.getRecommendationsFromAI(input);
        return parseRecommendations(jsonResponse);
    }

    private List<Recommendation> parseRecommendations(String jsonResponse) {
        final List<Recommendation> recommendations = new ArrayList<>();
        try {
            final JsonNode rootNode = objectMapper.readTree(jsonResponse);
            if (rootNode.isArray()) {
                for (JsonNode node : rootNode) {
                    recommendations.add(createRecommendationFromNode(node));
                }
            }
            else {
                recommendations.add(createRecommendationFromNode(rootNode));
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error parsing recommendations: " + e.getMessage());
        }
        return recommendations;
    }

    private Recommendation createRecommendationFromNode(JsonNode node) {
        final int id = node.get("recommendation_id").asInt();
        final String type;
        final String content;
        final String artist;

        if (node.has("song")) {
            type = "song";
            content = node.get("song").asText();
        }
        else if (node.has("album")) {
            type = "album";
            content = node.get("album").asText();
        }
        else {
            type = "artist";
            content = node.get("artist").asText();
            return new Recommendation(id, content, "", type);
        }

        artist = node.get("artist").asText();
        return new Recommendation(id, content, artist, type);
    }
}
