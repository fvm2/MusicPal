package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.sashirestela.openai.SimpleOpenAI;
import io.github.sashirestela.openai.domain.assistant.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenAIService {
    private final SimpleOpenAI openAI;
    private String assistantId;
    private final ObjectMapper objectMapper;

    public OpenAIService(String apiKey) {
        this.openAI = SimpleOpenAI.builder()
                .apiKey(apiKey)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public void initialize() {
        createAssistant();
    }

    private void createAssistant() {
        try {
            System.out.println("Creating Assistant");
            var assistant = openAI.assistants()
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
            System.out.println("Successfully created assistant " + assistantId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create assistant: " + e.getMessage());
        }
    }

    public String createThread() {
        try {
            var thread = openAI.threads()
                    .create(ThreadRequest.builder().build())
                    .join();
            return thread.getId();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create thread: " + e.getMessage());
        }
    }

    public String getRecommendationsFromAI(String input, String threadId) {
        try {
            openAI.threadMessages()
                    .create(threadId, ThreadMessageRequest.builder()
                            .role(ThreadMessageRole.USER)
                            .content(input)
                            .build())
                    .join();

            var runStream = openAI.threadRuns()
                    .createStream(threadId, ThreadRunRequest.builder()
                            .assistantId(assistantId)
                            .build())
                    .join();

            StringBuilder response = new StringBuilder();
            runStream.forEach(event -> {
                if (event.getData() != null) {
                    response.append(event.getData().toString());
                }
            });

            return extractJsonFromResponse(response.toString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get recommendations: " + e.getMessage());
        }
    }

    private String extractJsonFromResponse(String response) {
        Pattern pattern = Pattern.compile("value=\\[\\s*\\{.*?\\}\\s*\\]", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(response);

        if (matcher.find()) {
            return matcher.group().substring(6);
        }
        return null;
    }

    public void deleteThread(String threadId) {
        try {
            openAI.threads().delete(threadId).join();
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete thread: " + e.getMessage());
        }
    }

    public void cleanup() {
        if (assistantId != null) {
            openAI.assistants().delete(assistantId).join();
        }
    }
}
