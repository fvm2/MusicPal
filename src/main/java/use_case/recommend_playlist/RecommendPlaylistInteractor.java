package use_case.recommend_playlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import data_access.DataAccessInterface;
import io.github.sashirestela.openai.SimpleOpenAI;
import main.ApiKey;
import use_case.recommender.Recommender;

import java.util.List;

public class RecommendPlaylistInteractor extends Recommender implements RecommendPlaylistInputBoundary {
    private final SimpleOpenAI openAI;
    private String assistantId;
    private final ObjectMapper objectMapper;
    private DataAccessInterface dataAccessObject;
    private final RecommendPlaylistOutputBoundary recommendPlaylistPresenter;

    public RecommendPlaylistInteractor(DataAccessInterface dataAccessObject, RecommendPlaylistOutputBoundary recommendPlaylistPresenter) {
        final ApiKey apiKeyObject = new ApiKey();
        final String apiKey = apiKeyObject.getApi_key();
        this.openAI = SimpleOpenAI.builder()
                .apiKey(apiKey)
                .build();
        this.objectMapper = new ObjectMapper();
        this.dataAccessObject = dataAccessObject;
        this.recommendPlaylistPresenter = recommendPlaylistPresenter;
        this.createAssistant();
    }

    public void execute(RecommendPlaylistInputData recommendPlaylistInputData) {
        final List<String> songs = recommendPlaylistInputData.getSongs();
        final String input = "[" + String.join(", ", songs) + "] ; -1 ; Playlist";
        final String recommendations = getRecommendationsAsString(input);
        final RecommendPlaylistOutputData recommendPlaylistOutputData = new RecommendPlaylistOutputData(recommendations);
        recommendPlaylistPresenter.showRecommendations(recommendPlaylistOutputData);
    }

    public void switchToMenuView() {
        recommendPlaylistPresenter.switchToMenuView();
    }
}
