package app;

import java.util.ArrayList;
import java.util.List;

import app.engine.MusicRecommendationEngine;
import app.gui.PreferencesGUI;
import app.gui.RecommendationGUI;

/**
 * App Controller (Interactor).
 */

public class AppController {
    private final MusicRecommendationEngine recommendationEngine;
    private final List<String> userPreferences = new ArrayList<>();

    public AppController(MusicRecommendationEngine recommendationEngine) {
        this.recommendationEngine = recommendationEngine;
    }

    /**
     * Launches the Preferences GUI.
     */
    public void launchPreferencesGui() {
        new PreferencesGUI(this).display();
    }

    /**
     * Submits song preferences to the recommendation engine.
     * @param songs - Songs.
     * @param artists - Artists.
     */
    public void submitPreferences(String[] songs, String[] artists) {
        userPreferences.clear();
        for (int i = 0; i < songs.length; i++) {
            userPreferences.add(songs[i] + " - " + artists[i]);
        }

        final String input = "[" + String.join(", ", userPreferences) + "] ; 3 ; Songs";
        final String recommendations = recommendationEngine.getRecommendationsAsString(input);

        new RecommendationGUI(this).display(recommendations);
    }

    /**
     * Cleans the recommendation engine up after a successful run.
     */
    public void cleanup() {
        recommendationEngine.cleanup();
    }
}

