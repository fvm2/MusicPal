package app;

import app.AppController;
import app.engine.MusicRecommendationEngine;

public class MainApp {
    public static void main(String[] args) {
        String apiKey = "api-key"; // Replace with your API key

        // Initialize the MusicRecommendationEngine
        MusicRecommendationEngine engine = new MusicRecommendationEngine(apiKey);

        // Pass the engine to the AppController
        AppController controller = new AppController(engine);

        // Start the application
        controller.start();
    }
}