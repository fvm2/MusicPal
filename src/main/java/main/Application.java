package main;

import infrastructure.database.PreferenceRepository;
import infrastructure.database.RecommendationRepository;
import infrastructure.database.UserRepository;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.jetbrains.annotations.NotNull;
import service.OpenAIService;
import service.PreferenceService;
import service.RecommendationService;
import service.UserService;
import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        // Initialize the API key for OpenAIService
        final Dotenv dotenv = Dotenv.load();
        final String apiKey = dotenv.get("OPENAI_API_KEY");

        // Initialize the OpenAIService
        final OpenAIService openAIService = new OpenAIService(apiKey);
        openAIService.initialize();

        // Instantiate repositories
        final UserRepository userRepository = new UserRepository();
        final PreferenceRepository preferenceRepository = new PreferenceRepository();
        final RecommendationRepository recommendationRepository = new RecommendationRepository();

        // Instantiate services
        UserService userService = new UserService(userRepository, openAIService);
        PreferenceService preferenceService = new PreferenceService(preferenceRepository);
        RecommendationService recommendationService = new RecommendationService(
                recommendationRepository,
                preferenceRepository,
                userRepository,
                openAIService
        );

        // Build the application using AppBuilder
        AppBuilder appBuilder = getAppBuilder(openAIService, userService, preferenceService, recommendationService);
        appBuilder.addSignupView()
                .addLoginView()
                .addProfileView()
                .addMenuView()
                .addArtistRecView()
                .addPlaylistRecView()
                .addSongRecView()
                .addSignupUseCase()
                .addLoginUseCase()
                .addMenuUseCase()
                .addArtistRecUseCase()
                .addPlaylistUseCase()
                .addSongUseCase()
                .addProfileUseCase();

        // Create and display the application window
        JFrame app = appBuilder.buildApp();
        app.setSize(800, 600);
        app.setVisible(true);

        // Add shutdown hook for cleanup
        Runtime.getRuntime().addShutdownHook(new Thread(openAIService::cleanup));
    }

    @NotNull
    private static AppBuilder getAppBuilder(OpenAIService openAIService, UserService userService,
                                            PreferenceService preferenceService, RecommendationService recommendationService) {
        // Build the application with all services
        return new AppBuilder(userService, preferenceService, recommendationService);
    }
}
