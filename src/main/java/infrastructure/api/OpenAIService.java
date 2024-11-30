package infrastructure.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.ports.IOpenAIService;
import io.github.sashirestela.openai.SimpleOpenAI;
import io.github.sashirestela.openai.domain.assistant.*;

/**
 * Placeholder for Checkstyle.
 */
public class OpenAIService implements IOpenAIService {
    private final SimpleOpenAI openAi;
    private String assistantId;
    private final ObjectMapper objectMapper;

    public OpenAIService(String apiKey) {
        this.openAi = SimpleOpenAI.builder()
                .apiKey(apiKey)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Runs the assistant, via calling the API.
     */
    public void initialize() {
        createAssistant();
    }

    private void createAssistant() {
        try {
            final var assistant = openAi.assistants()
                    .create(AssistantRequest.builder()
                            .name("Music Recommendation Engine")
                            .model("gpt-4o-mini")
                            .instructions("""
                        You are an Machine Learning Music Recommendation Engine, for each prompt in the form:
                        [Song - Artist] ; Quantity (int of recommendations you should return) ; Type (Albums, Songs, Artists)
                        You return a JSON: {
                            recommendation_id: 1 for the first recommendation, 2 for the second, etc,
                            song || album || artist: "",
                            (if type song or album) artist: ""
                        }
                        Try to be creative and don't suggest songs, albums or artists that the user already told you they like. Only respond in JSON format.
                        Example input:
                        [My Sweet Lord - George Harrison, Layla - Eric Clapton, Panama - Van Halen] ; 3 ; Albums
                        Expected output:
                        [
                            {
                                "recommendation_id": 1,
                                "album": "The Concert for Bangladesh",
                                "artist": "Various Artists"
                            },
                            {
                                "recommendation_id": 2,
                                "album": "Disraeli Gears",
                                "artist": "Cream"
                            },
                            {
                                "recommendation_id": 3,
                                "album": "Skyscraper",
                                "artist": "David Lee Roth"
                            }
                        ]
                        """)
                            .build())
                    .join();

            this.assistantId = assistant.getId();
        }
        catch (Exception exception) {
            throw new RuntimeException("Error creating assistant: " + exception.getMessage());
        }
    }

    @Override
    public String getRecommendationsFromAi(String input) {
        try {
            final var thread = openAi.threads()
                    .create(ThreadRequest.builder().build())
                    .join();
            final String threadId = thread.getId();

            openAi.threadMessages()
                    .create(threadId, ThreadMessageRequest.builder()
                            .role(ThreadMessageRole.USER)
                            .content(input)
                            .build())
                    .join();

            final var runStream = openAi.threadRuns()
                    .createStream(threadId, ThreadRunRequest.builder()
                            .assistantId(assistantId)
                            .build())
                    .join();

            final StringBuilder response = new StringBuilder();
            runStream.forEach(event -> {
                if (event.getData() != null) {
                    response.append(event.getData().toString());
                }
            });

            openAi.threads().delete(threadId).join();
            return extractJsonFromResponse(response.toString());
        }
        catch (Exception exception) {
            throw new RuntimeException("Error getting recommendations: " + exception.getMessage());
        }
    }

    private String extractJsonFromResponse(String response) {
        // Look for content within the last "value=" in the response
        final Pattern pattern = Pattern.compile("value=\\[\\s*\\{.*?\\}\\s*\\]", Pattern.DOTALL);
        final Matcher matcher = pattern.matcher(response);

        String jsonStr = null;
        while (matcher.find()) {
            jsonStr = matcher.group();
        }

        if (jsonStr != null) {
            // Remove the "value=" prefix
            jsonStr = jsonStr.substring(6);
            return jsonStr;
        }

        return null;
    }

    /**
     * Finalizes connection with the API.
     */
    public void cleanup() {
        if (assistantId != null) {
            openAi.assistants().delete(assistantId).join();
        }
    }
}
