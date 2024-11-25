package main;

import application.RecommendationService;
import application.UserService;
import domain.ports.IOpenAIService;
import infrastructure.OpenAIService;
import infrastructure.config.DatabaseConfig;
import infrastructure.persistence.PostgresUserRepository;
import presentation.MusicRecommendationGUI;
import io.github.cdimascio.dotenv.Dotenv;

import javax.sql.DataSource;

public class Application {
    public static void main(String[] args) {
        try {
            // Load environment variables
            final Dotenv dotenv = Dotenv.load();

            // Initialize database
            System.out.println("Initializing database connection...");
            DatabaseConfig.initialize(
                    dotenv.get("DATABASE_URL"),  // jdbc:postgresql://host/database
                    dotenv.get("DATABASE_USER"),
                    dotenv.get("DATABASE_PASSWORD"),
                    dotenv.get("POSTGRES_SSL_MODE")
            );

            // Get the initialized DataSource
            DataSource dataSource = DatabaseConfig.getDataSource();

            // Create repository
            PostgresUserRepository userRepository = new PostgresUserRepository(dataSource);

            // Initialize database schema
            System.out.println("Creating database tables...");
            userRepository.initializeDatabase();
            System.out.println("Database tables created successfully.");

            // Initialize OpenAI service
            System.out.println("Initializing OpenAI service...");
            IOpenAIService openAIService = new OpenAIService(dotenv.get("OPENAI_API_KEY"));
            ((OpenAIService) openAIService).initialize();
            System.out.println("OpenAI service initialized successfully.");

            // Create services
            UserService userService = new UserService(userRepository);
            RecommendationService recommendationService = new RecommendationService(openAIService);

            // Start GUI
            System.out.println("Starting application GUI...");
            MusicRecommendationGUI gui = new MusicRecommendationGUI(userService, recommendationService);
            gui.start();

            // Add shutdown hook for cleanup
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Shutting down application...");
                DatabaseConfig.closeDataSource();
                ((OpenAIService) openAIService).cleanup();
                System.out.println("Application shutdown complete.");
            }));
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}