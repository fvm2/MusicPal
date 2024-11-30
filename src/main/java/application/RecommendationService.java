package application;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Recommendation;
import entity.UserPreferences;
import entity.ports.IOpenAIService;

/**
 * Placeholder for Checkstyle.
 */
public class RecommendationService {
    private final IOpenAIService openAiService;
    private final ObjectMapper objectMapper;
    private final String song = "song";
    private final String album = "album";
    private final String artist = "artist";

    public RecommendationService(IOpenAIService openAiService) {
        this.openAiService = openAiService;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Placeholder for Checkstyle.
     * @param prefs preferences.
     * @param count number of recommendations.
     * @param type type of recommendation.
     * @return a List of Recommendations.
     */
    public List<Recommendation> getRecommendations(UserPreferences prefs, int count, String type) {
        final String input = String.format("[%s] ; %d ; %s",
                String.join(", ", prefs.getFavoriteSongs()),
                count,
                type);

        final String jsonResponse = openAiService.getRecommendationsFromAi(input);
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
        catch (Exception exception) {
            throw new RuntimeException("Error parsing recommendations: " + exception.getMessage());
        }
        return recommendations;
    }

    private Recommendation createRecommendationFromNode(JsonNode node) {
        final int id = node.get("recommendation_id").asInt();
        final String type;
        final String content;
        final String recommendedArtist;

        if (node.has(song)) {
            type = song;
            content = node.get(song).asText();
        }
        else if (node.has(album)) {
            type = album;
            content = node.get(album).asText();
        }
        else {
            type = artist;
            content = node.get(artist).asText();
            return new Recommendation(id, content, "", type);
        }

        recommendedArtist = node.get(artist).asText();
        return new Recommendation(id, content, recommendedArtist, type);
    }
}
