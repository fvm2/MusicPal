package use_case.recommender;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.sashirestela.openai.SimpleOpenAI;
import io.github.sashirestela.openai.domain.assistant.*;
import main.ApiKey;

public abstract class Recommender {
    private final SimpleOpenAI openAI;
    private String assistantId;
    private final ObjectMapper objectMapper;

    public Recommender() {
        final ApiKey apiKeyObject = new ApiKey();
        final String apiKey = apiKeyObject.getApi_key();
        this.openAI = SimpleOpenAI.builder()
                .apiKey(apiKey)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public void createAssistant() {
        try {
            System.out.println("Creating Music Recommendation Engine...");
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
            System.out.println("Recommendation Engine created with ID: " + assistantId);
        } catch (Exception e) {
            System.err.println("Error creating recommendation engine: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void getRecommendations(String input) {
        try {
            // Create a thread
            System.out.println("Creating thread...");
            var thread = openAI.threads()
                    .create(ThreadRequest.builder().build())
                    .join();
            String threadId = thread.getId();

            // Add message to thread
            System.out.println("Processing input: " + input);
            openAI.threadMessages()
                    .create(threadId, ThreadMessageRequest.builder()
                            .role(ThreadMessageRole.USER)
                            .content(input)
                            .build())
                    .join();

            // Run the assistant
            System.out.println("Generating recommendations...");
            var runStream = openAI.threadRuns()
                    .createStream(threadId, ThreadRunRequest.builder()
                            .assistantId(assistantId)
                            .build())
                    .join();

            // Print the response
            System.out.println("\nRecommendations:");
            runStream.forEach(event -> {
                if (event.getData() != null) {
                    System.out.println(event.getData().toString());
                }
            });

            // Clean up
            openAI.threads().delete(threadId).join();

        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getRecommendationsAsString(String input) {
        try {
            // Create a thread
            var thread = openAI.threads()
                    .create(ThreadRequest.builder().build())
                    .join();
            String threadId = thread.getId();

            // Add message to thread
            openAI.threadMessages()
                    .create(threadId, ThreadMessageRequest.builder()
                            .role(ThreadMessageRole.USER)
                            .content(input)
                            .build())
                    .join();

            // Run the assistant
            var runStream = openAI.threadRuns()
                    .createStream(threadId, ThreadRunRequest.builder()
                            .assistantId(assistantId)
                            .build())
                    .join();

            // Get the response and format it
            StringBuilder rawResponse = new StringBuilder();
            runStream.forEach(event -> {
                if (event.getData() != null) {
                    rawResponse.append(event.getData().toString());
                }
            });

            // Clean up
            openAI.threads().delete(threadId).join();

            // Extract JSON from the response
            String jsonResponse = extractJsonFromResponse(rawResponse.toString());
            if (jsonResponse == null || jsonResponse.isEmpty()) {
                return "No recommendations found";
            }

            try {
                JsonNode rootNode = objectMapper.readTree(jsonResponse);
                List<String> formattedRecommendations = new ArrayList<>();

                // Handle both single object and array responses
                if (rootNode.isArray()) {
                    for (JsonNode node : rootNode) {
                        formattedRecommendations.add(formatRecommendation(node));
                    }
                } else {
                    formattedRecommendations.add(formatRecommendation(rootNode));
                }

                return String.join("\n", formattedRecommendations);
            } catch (Exception e) {
                return "Error parsing recommendation: " + e.getMessage();
            }

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private String extractJsonFromResponse(String response) {
        // Look for content within the last "value=" in the response
        Pattern pattern = Pattern.compile("value=\\[\\s*\\{.*?\\}\\s*\\]", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(response);

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

    private String formatRecommendation(JsonNode node) {
        if (node.has("song") && node.has("artist")) {
            return node.get("song").asText() + " by " + node.get("artist").asText();
        } else if (node.has("album") && node.has("artist")) {
            return node.get("album").asText() + " by " + node.get("artist").asText();
        } else if (node.has("artist")) {
            return node.get("artist").asText();
        }
        return "Invalid recommendation format";
    }

    public void cleanup() {
        if (assistantId != null) {
            try {
                System.out.println("Cleaning up...");
                openAI.assistants().delete(assistantId).join();
                System.out.println("Recommendation Engine deleted successfully");
            } catch (Exception e) {
                System.err.println("Error during cleanup: " + e.getMessage());
            }
        }
    }
}
