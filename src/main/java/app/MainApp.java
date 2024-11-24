package app;

import javax.swing.SwingUtilities;

import app.engine.MusicRecommendationEngine;
import app.gui.SignUpGUI;

/**
 * The Main Class.
 */
public class MainApp {

    /**
     * The Main Method.
     * @param args -
     */
    public static void main(String[] args) {
        String apiKey = "api-key"; // Replace with your API key
        MusicRecommendationEngine recommendationEngine = new MusicRecommendationEngine(apiKey);;
        var appController = new AppController(recommendationEngine);

        recommendationEngine.createAssistant();

        SwingUtilities.invokeLater(() -> {
            var signUpGUI = new SignUpGUI(appController);
            signUpGUI.display();
        });
    }
}

