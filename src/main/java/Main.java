import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static MusicRecommendationEngine engine;
    private static List<String> userPreferences = new ArrayList<>();

    public static void main(String[] args) {
        String apiKey = "API-KEY"; // Replace with your API key
        engine = new MusicRecommendationEngine(apiKey);
        engine.createAssistant();

        SwingUtilities.invokeLater(() -> createAndShowSignUpGUI());
    }

    private static void createAndShowSignUpGUI() {
        JFrame frame = new JFrame("Music Recommendation System - Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton signUpButton = new JButton("Sign Up");

        frame.add(new JLabel("Name:"), gbc);
        frame.add(nameField, gbc);
        frame.add(new JLabel("Email:"), gbc);
        frame.add(emailField, gbc);
        frame.add(new JLabel("Password:"), gbc);
        frame.add(passwordField, gbc);
        frame.add(signUpButton, gbc);

        signUpButton.addActionListener(e -> {
            String name = nameField.getText();
            if (!name.isEmpty()) {
                frame.dispose();
                showPreferencesGUI();
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
      
        private static void showPreferencesGUI() {
        JFrame frame = new JFrame("Enter Your Music Preferences");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField[] songFields = new JTextField[3];
        JTextField[] artistFields = new JTextField[3];

        for (int i = 0; i < 3; i++) {
            frame.add(new JLabel("Song " + (i + 1) + ":"), gbc);
            songFields[i] = new JTextField(20);
            frame.add(songFields[i], gbc);

            frame.add(new JLabel("Artist " + (i + 1) + ":"), gbc);
            artistFields[i] = new JTextField(20);
            frame.add(artistFields[i], gbc);
        }

        JButton submitButton = new JButton("Submit Preferences");
        frame.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            userPreferences.clear();
            for (int i = 0; i < 3; i++) {
                String song = songFields[i].getText();
                String artist = artistFields[i].getText();
                if (!song.isEmpty() && !artist.isEmpty()) {
                    userPreferences.add(song + " - " + artist);
                }
            }

            if (userPreferences.size() == 3) {
                frame.dispose();
                showRecommendationGUI();
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter all songs and artists");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    }
