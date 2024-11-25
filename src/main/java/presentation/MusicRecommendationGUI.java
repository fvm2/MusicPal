package presentation;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import domain.User;
import domain.UserPreferences;
import domain.Recommendation;
import application.UserService;
import application.RecommendationService;

public class MusicRecommendationGUI {
    private final UserService userService;
    private final RecommendationService recommendationService;

    public MusicRecommendationGUI(UserService userService, RecommendationService recommendationService) {
        this.userService = userService;
        this.recommendationService = recommendationService;
    }

    public void start() {
        SwingUtilities.invokeLater(() -> new LoginWindow(userService, this).show());
    }

    public void showSignUpGUI() {
        JFrame frame = new JFrame("Music Recommendation System - Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Create form components
        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JTextField countryField = new JTextField(20);
        JButton signUpButton = new JButton("Sign Up");
        JButton backButton = new JButton("Back to Login");

        // Add components to frame
        frame.add(createLabel("Name:"), gbc);
        frame.add(nameField, gbc);
        frame.add(createLabel("Email:"), gbc);
        frame.add(emailField, gbc);
        frame.add(createLabel("Password:"), gbc);
        frame.add(passwordField, gbc);
        frame.add(createLabel("Country:"), gbc);
        frame.add(countryField, gbc);
        frame.add(signUpButton, gbc);
        frame.add(backButton, gbc);

        // Add action listeners
        signUpButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            String country = countryField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || country.isEmpty()) {
                showError(frame, "Please fill in all fields");
                return;
            }

            if (userService.registerUser(name, email, password, country)) {
                frame.dispose();
                showPreferencesGUI();
            } else {
                showError(frame, "Email already registered");
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            new LoginWindow(userService, this).show();
        });

        centerAndShow(frame);
    }

    public void showPreferencesGUI() {
        JFrame frame = new JFrame("Enter Your Music Preferences");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Create arrays to hold input fields
        JTextField[] songFields = new JTextField[3];
        JTextField[] artistFields = new JTextField[3];

        // Add input fields for songs and artists
        for (int i = 0; i < 3; i++) {
            frame.add(createLabel("Favorite Song " + (i + 1) + ":"), gbc);
            songFields[i] = new JTextField(20);
            frame.add(songFields[i], gbc);

            frame.add(createLabel("Artist " + (i + 1) + ":"), gbc);
            artistFields[i] = new JTextField(20);
            frame.add(artistFields[i], gbc);
        }

        JButton submitButton = new JButton("Submit Preferences");
        frame.add(submitButton, gbc);

        // Add action listener
        submitButton.addActionListener(e -> {
            if (validateAndSavePreferences(songFields, artistFields)) {
                frame.dispose();
                new RecommendationWindow(userService, recommendationService).show();
            } else {
                showError(frame, "Please enter all songs and artists");
            }
        });

        centerAndShow(frame);
    }

    public void showPreferencesOrRecommendationGUI() {
        User currentUser = userService.getCurrentUser();
        if (currentUser.getPreferences().getFavoriteSongs().isEmpty()) {
            showPreferencesGUI();
        } else {
            new RecommendationWindow(userService, recommendationService).show();
        }
    }

    // Helper methods
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        return label;
    }

    private void centerAndShow(JFrame frame) {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showError(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private boolean validateAndSavePreferences(JTextField[] songFields, JTextField[] artistFields) {
        boolean allFieldsFilled = true;
        UserPreferences preferences = new UserPreferences();

        for (int i = 0; i < 3; i++) {
            String song = songFields[i].getText().trim();
            String artist = artistFields[i].getText().trim();

            if (song.isEmpty() || artist.isEmpty()) {
                allFieldsFilled = false;
                break;
            }
            preferences.addSongWithArtist(song, artist);
        }

        if (allFieldsFilled) {
            User currentUser = userService.getCurrentUser();
            currentUser.updatePreferences(preferences);
            userService.updateUserPreferences(currentUser);
        }

        return allFieldsFilled;
    }

    // Inner class for Recommendation Window
    public static class RecommendationWindow {
        private final UserService userService;
        private final RecommendationService recommendationService;

        public RecommendationWindow(UserService userService, RecommendationService recommendationService) {
            this.userService = userService;
            this.recommendationService = recommendationService;
        }

        public void show() {
            JFrame frame = new JFrame("Get Music Recommendations");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(5, 5, 5, 5);

            // Create components
            JTextArea recommendationArea = new JTextArea(10, 30);
            recommendationArea.setEditable(false);
            recommendationArea.setLineWrap(true);
            recommendationArea.setWrapStyleWord(true);

            JScrollPane scrollPane = new JScrollPane(recommendationArea);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            JButton getRecommendationButton = new JButton("Get Recommendation");
            JButton profileButton = new JButton("View Profile");
            JButton logoutButton = new JButton("Logout");

            // Create button panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(getRecommendationButton);
            buttonPanel.add(profileButton);
            buttonPanel.add(logoutButton);

            // Add components
            frame.add(scrollPane, gbc);
            frame.add(buttonPanel, gbc);

            // Add action listeners
            getRecommendationButton.addActionListener(e -> {
                getRecommendationButton.setEnabled(false);
                recommendationArea.setText("Getting recommendations...");

                new SwingWorker<List<Recommendation>, Void>() {
                    @Override
                    protected List<Recommendation> doInBackground() {
                        return recommendationService.getRecommendations(
                                userService.getCurrentUser().getPreferences(),
                                1,
                                "Songs"
                        );
                    }

                    @Override
                    protected void done() {
                        try {
                            List<Recommendation> recommendations = get();
                            userService.saveRecommendationHistory(recommendations);
                            recommendationArea.setText(formatRecommendations(recommendations));
                        } catch (InterruptedException | ExecutionException ex) {
                            recommendationArea.setText("Error getting recommendations: " + ex.getMessage());
                        } finally {
                            getRecommendationButton.setEnabled(true);
                        }
                    }
                }.execute();
            });

            profileButton.addActionListener(e -> {
                frame.dispose();
                new UserProfileWindow(userService, recommendationService).show();
            });

            logoutButton.addActionListener(e -> {
                userService.logout();
                frame.dispose();
                new LoginWindow(userService, new MusicRecommendationGUI(userService, recommendationService)).show();
            });

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

        private String formatRecommendations(List<Recommendation> recommendations) {
            if (recommendations == null || recommendations.isEmpty()) {
                return "No recommendations found.";
            }

            return recommendations.stream()
                    .map(Recommendation::toString)
                    .collect(Collectors.joining("\n\n"));
        }
    }
}