package gui;

import dto.*;
import entity.*;
import service.*;
import infrastructure.database.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import io.github.cdimascio.dotenv.Dotenv;

public class MusicPalApp extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    // Services
    private final UserService userService;
    private final PreferenceService preferenceService;
    private final RecommendationService recommendationService;

    // Current user session
    private User currentUser;

    public MusicPalApp() {
        // Initialize services
        Dotenv dotenv = Dotenv.load();
        UserRepository userRepo = new UserRepository();
        PreferenceRepository prefRepo = new PreferenceRepository();
        RecommendationRepository recRepo = new RecommendationRepository();
        OpenAIService openAIService = new OpenAIService(dotenv.get("OPENAI_API_KEY"));
        openAIService.initialize();

        userService = new UserService(userRepo, openAIService);
        preferenceService = new PreferenceService(prefRepo);
        recommendationService = new RecommendationService(recRepo, prefRepo, userRepo, openAIService);

        // Setup frame
        setTitle("MusicPal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add panels
        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createSignupPanel(), "SIGNUP");
        mainPanel.add(createMainPanel(), "MAIN");
        mainPanel.add(createPreferencesPanel(), "PREFERENCES");
        mainPanel.add(createHistoryPanel(), "HISTORY");

        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField emailField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton goToSignupButton = new JButton("New User? Sign Up");

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            Result<User> result = userService.login(email, password);
            if (result.isSuccess()) {
                currentUser = result.getData();
                cardLayout.show(mainPanel, "MAIN");
            } else {
                JOptionPane.showMessageDialog(this, result.getError());
            }
        });

        goToSignupButton.addActionListener(e -> cardLayout.show(mainPanel, "SIGNUP"));

        // Layout components
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(loginButton, gbc);
        gbc.gridy = 3;
        panel.add(goToSignupButton, gbc);

        return panel;
    }

    private JPanel createSignupPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField nameField = new JTextField(20);
        JTextField surnameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField countryField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton signupButton = new JButton("Sign Up");
        JButton backButton = new JButton("Back to Login");

        signupButton.addActionListener(e -> {
            UserDTO userDTO = new UserDTO(
                    nameField.getText(),
                    surnameField.getText(),
                    emailField.getText(),
                    countryField.getText(),
                    new String(passwordField.getPassword())
            );

            Result<User> result = userService.register(userDTO);
            if (result.isSuccess()) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                cardLayout.show(mainPanel, "LOGIN");
            } else {
                JOptionPane.showMessageDialog(this, result.getError());
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN"));

        // Layout components
        int y = 0;
        gbc.gridx = 0; gbc.gridy = y++;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        // Add other fields similarly...

        gbc.gridx = 1; gbc.gridy = y++;
        panel.add(signupButton, gbc);
        gbc.gridy = y;
        panel.add(backButton, gbc);

        return panel;
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        JButton profileButton = new JButton("My Profile");
        JButton preferencesButton = new JButton("Set Preferences");
        JButton recommendationButton = new JButton("Get Recommendation");
        JButton historyButton = new JButton("View History");
        JButton logoutButton = new JButton("Logout");

        profileButton.addActionListener(e -> {
            String profileInfo = String.format("""
                Name: %s %s
                Email: %s
                Country: %s
                """,
                    currentUser.getName(),
                    currentUser.getSurname(),
                    currentUser.getEmail(),
                    currentUser.getCountry()
            );
            JOptionPane.showMessageDialog(this, profileInfo, "Profile", JOptionPane.INFORMATION_MESSAGE);
        });

        preferencesButton.addActionListener(e -> cardLayout.show(mainPanel, "PREFERENCES"));

        recommendationButton.addActionListener(e -> {
            Result<Recommendation> result = recommendationService.getRecommendation(currentUser.getId(), "Song");
            if (result.isSuccess()) {
                Recommendation rec = result.getData();
                Object[] options = {"Like", "Dislike", "Skip"};
                int choice = JOptionPane.showOptionDialog(this,
                        "Recommendation: " + rec.getContent(),
                        "New Recommendation",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]);

                if (choice != 2) { // If not skipped
                    recommendationService.rateRecommendation(rec.getRecommendationId(), choice == 0);
                }
            }
            else {
                JOptionPane.showMessageDialog(this, result.getError());
            }
        });

        historyButton.addActionListener(e -> cardLayout.show(mainPanel, "HISTORY"));

        logoutButton.addActionListener(e -> {
            currentUser = null;
            cardLayout.show(mainPanel, "LOGIN");
        });

        buttonPanel.add(profileButton);
        buttonPanel.add(preferencesButton);
        buttonPanel.add(recommendationButton);
        buttonPanel.add(historyButton);
        buttonPanel.add(logoutButton);

        panel.add(buttonPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPreferencesPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea songsArea = new JTextArea(5, 30);
        JTextArea genresArea = new JTextArea(5, 30);
        JTextArea artistsArea = new JTextArea(5, 30);
        JTextArea albumsArea = new JTextArea(5, 30);

        JButton saveButton = new JButton("Save Preferences");
        JButton backButton = new JButton("Back");

        saveButton.addActionListener(e -> {
            PreferenceDTO prefDTO = new PreferenceDTO(
                    currentUser.getId(),
                    List.of(songsArea.getText().split("\n")),
                    List.of(genresArea.getText().split("\n")),
                    List.of(artistsArea.getText().split("\n")),
                    List.of(albumsArea.getText().split("\n"))
            );

            Result<Preference> result = preferenceService.updatePreferences(prefDTO);
            if (result.isSuccess()) {
                JOptionPane.showMessageDialog(this, "Preferences updated!");
                cardLayout.show(mainPanel, "MAIN");
            } else {
                JOptionPane.showMessageDialog(this, result.getError());
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MAIN"));

        // Layout components
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.add(new JLabel("Songs (one per line):"));
        inputPanel.add(new JScrollPane(songsArea));
        inputPanel.add(new JLabel("Genres (one per line):"));
        inputPanel.add(new JScrollPane(genresArea));
        inputPanel.add(new JLabel("Artists (one per line):"));
        inputPanel.add(new JScrollPane(artistsArea));
        inputPanel.add(new JLabel("Albums (one per line):"));
        inputPanel.add(new JScrollPane(albumsArea));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> historyList = new JList<>(listModel);
        JButton refreshButton = new JButton("Refresh");
        JButton backButton = new JButton("Back");

        refreshButton.addActionListener(e -> {
            listModel.clear();
            // Get recommendations from repository
            RecommendationRepository recRepo = new RecommendationRepository();
            List<Recommendation> recommendations = recRepo.findByUserId(currentUser.getId());

            for (Recommendation rec : recommendations) {
                String likedStatus = rec.getLiked() == null ? "Not Rated" :
                        rec.getLiked() ? "Liked" : "Disliked";
                listModel.addElement(String.format("%s - %s (%s)",
                        rec.getContent(), rec.getType(), likedStatus));
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MAIN"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        panel.add(new JScrollPane(historyList), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MusicPalApp().setVisible(true);
        });
    }
}
