package presentation;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class MusicRecommendationGUI {
    private final RecommendationService recommendationService;
    private UserPreferences userPreferences;

    public MusicRecommendationGUI(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
        this.userPreferences = new UserPreferences();
    }

    public void start() {
        SwingUtilities.invokeLater(this::createAndShowSignUpGUI);
    }

    private void createAndShowSignUpGUI() {
        JFrame frame = new JFrame("Music Recommendation System - Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Create form components
        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton signUpButton = new JButton("Sign Up");

        // Add components to frame
        frame.add(createLabel("Name:"), gbc);
        frame.add(nameField, gbc);
        frame.add(createLabel("Email:"), gbc);
        frame.add(emailField, gbc);
        frame.add(createLabel("Password:"), gbc);
        frame.add(passwordField, gbc);
        frame.add(signUpButton, gbc);

        // Add action listener
        signUpButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (validateSignUpInput(name, email, password)) {
                frame.dispose();
                showPreferencesGUI();
            } else {
                showError(frame, "Please fill in all fields");
            }
        });

        centerAndShow(frame);
    }

    private void showPreferencesGUI() {
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
            frame.add(createLabel("Song " + (i + 1) + ":"), gbc);
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
                showRecommendationGUI();
            } else {
                showError(frame, "Please enter all songs and artists");
            }
        });

        centerAndShow(frame);
    }

    private void showRecommendationGUI() {
        JFrame frame = new JFrame("Get Music Recommendations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
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

        // Add components
        frame.add(scrollPane, gbc);
        frame.add(getRecommendationButton, gbc);

        // Add action listener
        getRecommendationButton.addActionListener(e -> {
            getRecommendationButton.setEnabled(false);
            recommendationArea.setText("Getting recommendations...");

            // Use SwingWorker for asynchronous API call
            new SwingWorker<List<Recommendation>, Void>() {
                @Override
                protected List<Recommendation> doInBackground() {
                    return recommendationService.getRecommendations(userPreferences, 1, "Songs");
                }

                @Override
                protected void done() {
                    try {
                        List<Recommendation> recommendations = get();
                        recommendationArea.setText(formatRecommendations(recommendations));
                    } catch (InterruptedException | ExecutionException ex) {
                        recommendationArea.setText("Error getting recommendations: " + ex.getMessage());
                    } finally {
                        getRecommendationButton.setEnabled(true);
                    }
                }
            }.execute();
        });

        centerAndShow(frame);
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

    private boolean validateSignUpInput(String name, String email, String password) {
        return !name.isEmpty() && !email.isEmpty() && !password.isEmpty();
    }

    private boolean validateAndSavePreferences(JTextField[] songFields, JTextField[] artistFields) {
        boolean allFieldsFilled = true;
        userPreferences = new UserPreferences(); // Reset preferences

        for (int i = 0; i < 3; i++) {
            String song = songFields[i].getText().trim();
            String artist = artistFields[i].getText().trim();

            if (song.isEmpty() || artist.isEmpty()) {
                allFieldsFilled = false;
            } else {
                userPreferences.addSongWithArtist(song, artist);
            }
        }

        return allFieldsFilled;
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