package use_case.recommend_song;

import com.fasterxml.jackson.databind.ObjectMapper;
import data_access.DataAccessInterface;
import io.github.sashirestela.openai.SimpleOpenAI;
import main.ApiKey;
import use_case.recommender.Recommender;

import java.util.List;

public class RecommendSongInteractor extends Recommender implements RecommendSongInputBoundary {
    private final SimpleOpenAI openAI;
    private String assistantId;
    private final ObjectMapper objectMapper;
    private DataAccessInterface dataAccessObject;
    private final RecommendSongOutputBoundary recommendSongPresenter;

    public RecommendSongInteractor(DataAccessInterface dataAccessObject, RecommendSongOutputBoundary recommendSongPresenter) {
        final ApiKey apiKeyObject = new ApiKey();
        final String apiKey = apiKeyObject.getApi_key();
        this.openAI = SimpleOpenAI.builder()
                .apiKey(apiKey)
                .build();
        this.objectMapper = new ObjectMapper();
        this.dataAccessObject = dataAccessObject;
        this.recommendSongPresenter = recommendSongPresenter;
        this.createAssistant();
    }

    public void execute(RecommendSongInputData recommendSongInputData) {
        final List<String> songs = recommendSongInputData.getSongs();
        final String input = "[" + String.join(", ", songs) + "] ; 5 ; Songs";
        final String recommendations = getRecommendationsAsString(input);
        final RecommendSongOutputData recommendSongOutputData = new RecommendSongOutputData(recommendations);
        recommendSongPresenter.showRecommendations(recommendSongOutputData);
    }

    public void switchToMenuView() {
        recommendSongPresenter.switchToMenuView();
    }
}
