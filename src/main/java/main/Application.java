package main;

import application.RecommendationService;
import domain.ports.IOpenAIService;
import infrastructure.OpenAIService;
import presentation.MusicRecommendationGUI;

public class Application {
    public static void main(String[] args) {
        String apiKey = "API-KEY";

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
