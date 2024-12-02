package main;

import presentation.MusicRecommendationGUI;
import service.OpenAIService;
import service.RecommendationService;

public class Application {
    public static void main(String[] args) {
        String apiKey = "sk-proj-1bu9R5k1BMUPd3B21IQm_DHRXssz4o11MbiXctiYmKIpgPp0TUcIp9KLMFmi0zISiRpl4CjKCZT3BlbkFJ" +
                "cWTWM5RUZdBCxWmQo2GVhMPRtqIR5kvu5-qSnlnwW8mcdvPyya9DLMwkZH-AqbNT4IpR-XQ0YA";

        // Setup dependencies
        OpenAIService openAIService = new OpenAIService(apiKey);
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
