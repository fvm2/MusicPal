package entity.ports;

/**
 * Interface for implementing the API calls to OpenAI.
 */
public interface IOpenAIService {
    /**
     * Makes an API call to Open AI Assistants API.
     * @param input prompt for the AI.
     * @return recommendations based on the input.
     */
    String getRecommendationsFromAi(String input);
}
