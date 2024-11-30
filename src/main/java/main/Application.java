package main;

import application.RecommendationService;
import entity.ports.IOpenAIService;
import infrastructure.api.OpenAIService;
import presentation.MusicRecommendationGUI;

public class Application {
    public static void main(String[] args) {
        ApiKey api_key = new ApiKey();
        String apiKey = api_key.getApi_key();

        // Setup dependencies
        IOpenAIService openAIService = new OpenAIService(apiKey);
        ((OpenAIService) openAIService).initialize();

        RecommendationService recommendationService = new RecommendationService(openAIService);

        // Start GUI
        MusicRecommendationGUI gui = new MusicRecommendationGUI(recommendationService);
        gui.start();

        // Add shutdown hook for cleanup
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (openAIService instanceof OpenAIService) {
                ((OpenAIService) openAIService).cleanup();
            }
        }));
    }
}
