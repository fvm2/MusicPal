package use_case.recommend;

import java.util.List;

/**
 * In the RecommendView, the drop down menu will let the user select Song, Playlist, or Album.
 * This will be the requrestedRecType. Then, the user will provide a list of songs/artists/albums. This will be
 * the preferences.
 */
public class RecommendInputData {
    private String requestedRecType;
    private List<String> preferences;

    public RecommendInputData(String requestedRecType, List<String> preferences) {
        this.requestedRecType = requestedRecType;
        this.preferences = preferences;
    }

    public String getRequestedRecType() {
        return requestedRecType;
    }
    public void setRequestedRecType(String requestedRecType) {
        this.requestedRecType = requestedRecType;
    }
    public List<String> getPreferences() {
        return preferences;
    }
    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }

}
