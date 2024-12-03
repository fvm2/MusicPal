package use_case.recommend_artist;

import com.fasterxml.jackson.databind.ObjectMapper;
import data_access.DataAccessInterface;
import io.github.sashirestela.openai.SimpleOpenAI;
import io.github.cdimascio.dotenv.Dotenv;
import use_case.recommender.Recommender;

import service.RecommendationService;
import service.Result;

public class RecommendArtistInteractor extends Recommender implements RecommendArtistInputBoundary {
    private final SimpleOpenAI openAI;
    private String assistantId;
    private final ObjectMapper objectMapper;
    private DataAccessInterface dataAccessObject;
    private final RecommendArtistOutputBoundary recommendArtistPresenter;

    public RecommendArtistInteractor(DataAccessInterface dataAccessObject, RecommendArtistOutputBoundary recommendArtistPresenter) {
        final Dotenv dotenv = Dotenv.load();

        final String apiKey = dotenv.get("OPENAI_API_KEY");
        this.openAI = SimpleOpenAI.builder()
                .apiKey(apiKey)
                .build();
        this.objectMapper = new ObjectMapper();
        this.dataAccessObject = dataAccessObject;
        this.recommendArtistPresenter = recommendArtistPresenter;
        this.createAssistant();
    }

    public void execute(RecommendArtistInputData recommendArtistInputData) {
        final String artist = recommendArtistInputData.getArtistName();
        final String input = "[" + artist + "] ; -1 ; Artists";
        final String recommendations = getRecommendationsAsString(input);
        final RecommendArtistOutputData recommendArtistOutputData = new RecommendArtistOutputData(recommendations);
        recommendArtistPresenter.showRecommendations(recommendArtistOutputData);
    }

    public void switchToRecView() {
        recommendArtistPresenter.switchToRecView();
    }

    public void switchToMenuView() {
        recommendArtistPresenter.switchToMenuView();
    }
}
