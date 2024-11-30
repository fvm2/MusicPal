package main;

import presentation.MusicRecommendationGUI;

public class Application {
    public static void main(String[] args) {
        String apiKey = "sk-proj-AxsKDGcfbndAYhlTpJEVHAvJQ_QlltHl5h_kL_iqsAyW7_ZHZZKjIBFcOsHD-JZ0VkB4Ay0fkQT3BlbkF" +
                "JnrWpzxioacC5bc_ks49T7l2Vr-0tEhJoKa97vmzc-Vy3bTJI4F0FDb9M3uttBlwOY3I3hrEXMA";

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
