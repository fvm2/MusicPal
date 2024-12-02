package service;

import entity.Preference;
import entity.Recommendation;
import entity.User;
import service.OpenAIService;
import infrastructure.database.PreferenceRepository;
import infrastructure.database.RecommendationRepository;
import infrastructure.database.UserRepository;
import java.util.List;

/**
 * RecommendationService
 * Core service for generating and managing music recommendations.
 *
 * Key responsibilities:
 * - Generating AI-based recommendations
 * - Managing friend recommendations
 * - Handling recommendation ratings
 * - Building AI prompts based on user preferences
 *
 * Dependencies:
 * - RecommendationRepository
 * - PreferenceRepository
 * - UserRepository
 * - OpenAIService
 *
 * Key methods:
 * - getRecommendation: Generates new recommendation based on user preferences
 * - rateRecommendation: Updates user's rating for a recommendation
 *
 * Usage example:
 * RecommendationService recService = new RecommendationService(recRepo, prefRepo, userRepo, openAIService);
 * Result<Recommendation> result = recService.getRecommendation(userId, "Song");
 */
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;
    private final PreferenceRepository preferenceRepository;
    private final UserRepository userRepository;
    private final OpenAIService openAIService;

    public RecommendationService(
            RecommendationRepository recommendationRepository,
            PreferenceRepository preferenceRepository,
            UserRepository userRepository,
            OpenAIService openAIService) {
        this.recommendationRepository = recommendationRepository;
        this.preferenceRepository = preferenceRepository;
        this.userRepository = userRepository;
        this.openAIService = openAIService;
    }

    public Result<Recommendation> getRecommendation(int userId, String type) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Preference pref = preferenceRepository.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("No preferences found"));

            String prompt = buildPrompt(pref, type);
            String aiResponse = openAIService.getRecommendationsFromAI(prompt, user.getThread());

            Recommendation recommendation = parseAIResponse(aiResponse, userId, type);
            recommendationRepository.save(recommendation);

            return Result.success(recommendation);
        } catch (Exception e) {
            return Result.failure("Failed to get recommendation: " + e.getMessage());
        }
    }

    private String buildPrompt(Preference pref, String type) {
        StringBuilder prompt = new StringBuilder();
        if (type.equals("Song")) {
            prompt.append(String.join(",", pref.getSongs()));
        } else if (type.equals("Artist")) {
            prompt.append(String.join(",", pref.getArtists()));
        } else {
            prompt.append(String.join(",", pref.getAlbums()));
        }
        prompt.append(" ; 1 ; ").append(type).append("s");
        return prompt.toString();
    }

    private Recommendation parseAIResponse(String aiResponse, int userId, String type) {
        // Parse JSON response and create Recommendation object
        // Implementation depends on OpenAI response format
        // This is a simplified example
        return new Recommendation(userId, aiResponse, type, 0);
    }

    public Result<Void> rateRecommendation(int recommendationId, boolean liked) {
        try {
            Recommendation recommendation = recommendationRepository
                    .findById(recommendationId)
                    .orElseThrow(() -> new RuntimeException("Recommendation not found"));

            recommendation.setLiked(liked);
            recommendationRepository.update(recommendation);

            return Result.success(null);
        } catch (Exception e) {
            return Result.failure("Failed to rate recommendation: " + e.getMessage());
        }
    }
}
