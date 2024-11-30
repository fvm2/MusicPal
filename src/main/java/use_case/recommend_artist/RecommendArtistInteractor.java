package use_case.recommend_artist;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.sashirestela.openai.SimpleOpenAI;
import main.ApiKey;
import use_case.recommender.Recommender;

public class RecommendArtistInteractor extends Recommender implements RecommendArtistInputBoundary {
    private final SimpleOpenAI openAI;
    private String assistantId;
    private final ObjectMapper objectMapper;
    private RecommendArtistDataAccessInterface recommendArtistDataAccessObject;
    private final RecommendArtistOutputBoundary recommendArtistPresenter;

    public RecommendArtistInteractor(RecommendArtistDataAccessInterface recommendArtistDataAccessObject, RecommendArtistOutputBoundary recommendArtistPresenter) {
        final ApiKey apiKeyObject = new ApiKey();
        final String apiKey = apiKeyObject.getApi_key();
        this.openAI = SimpleOpenAI.builder()
                .apiKey(apiKey)
                .build();
        this.objectMapper = new ObjectMapper();
        this.recommendArtistDataAccessObject = recommendArtistDataAccessObject;
        this.recommendArtistPresenter = recommendArtistPresenter;
    }

    public void execute(RecommendArtistInputData recommendArtistInputData) {
        final String artist = recommendArtistInputData.getArtistName();
        final String input = "[" + artist + "] ; 5 ; Artists";
        final String recommendations = getRecommendationsAsString(input);
        final RecommendArtistOutputData recommendArtistOutputData = new RecommendArtistOutputData(recommendations);
        recommendArtistPresenter.showRecommendations(recommendArtistOutputData);
    }
}
