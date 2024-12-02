package main;

import infrastructure.database.PreferenceRepository;
import infrastructure.database.RecommendationRepository;
import infrastructure.database.UserRepository;
import org.jetbrains.annotations.NotNull;
import service.OpenAIService;
import service.PreferenceService;
import service.RecommendationService;
import service.UserService;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        // Initialize the API key for OpenAIService
        String apiKey = "YOUR_API_KEY";

        // Initialize the OpenAIService
        OpenAIService openAIService = new OpenAIService(apiKey);
        openAIService.initialize();
        
        // Instantiate repositories
        UserRepository userRepository = new UserRepository();
        PreferenceRepository preferenceRepository = new PreferenceRepository();
        RecommendationRepository recommendationRepository = new RecommendationRepository();
      
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
        AppBuilder appBuilder = getAppBuilder(openAIService);
        appBuilder.addSignupView()
                .addLoginView()
                .addProfileView()
                .addSignupUseCase()
                .addLoginUseCase()
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