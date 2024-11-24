package app;

import app.engine.MusicRecommendationEngine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AppController {
    private final MusicRecommendationEngine engine;
    private List<String> userPreferences = new ArrayList<>();

    public AppController(MusicRecommendationEngine engine) {
        this.engine = engine;
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
            if (!nameField.getText().isEmpty()) {
                frame.dispose();
                showMenuGUI();
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showMenuGUI() {
        JFrame frame = new JFrame("Music Recommendation System - Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JButton preferencesButton = new JButton("Enter Preferences");
        JButton similarArtistsButton = new JButton("Find Similar Artists");
        JButton playlistButton = new JButton("Generate Playlist");

        frame.add(preferencesButton, gbc);
        frame.add(similarArtistsButton, gbc);
        frame.add(playlistButton, gbc);

        preferencesButton.addActionListener(e -> {
            frame.dispose();
            showPreferencesGUI();
        });

        similarArtistsButton.addActionListener(e -> {
            frame.dispose();
            showSimilarArtistsGUI();
        });

        playlistButton.addActionListener(e -> {
            frame.dispose();
            showPlaylistGUI();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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

    private void showRecommendationGUI() {
        JFrame frame = new JFrame("Your Music Recommendations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        String preferencesInput = "[" + String.join(", ", userPreferences) + "] ; 3 ; Songs";
        String recommendations = engine.getRecommendationsAsString(preferencesInput);

        JTextArea recommendationsArea = new JTextArea();
        recommendationsArea.setEditable(false);
        recommendationsArea.setText(recommendations);

        JScrollPane scrollPane = new JScrollPane(recommendationsArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            engine.cleanup();
            System.exit(0);
        });
        frame.add(exitButton, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showSimilarArtistsGUI() {
        JFrame frame = new JFrame("Find Similar Artists");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel artistLabel = new JLabel("Enter an artist:");
        JTextField artistField = new JTextField(20);
        JButton findButton = new JButton("Find Similar Artists");
        JTextArea resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        frame.add(artistLabel, gbc);
        frame.add(artistField, gbc);
        frame.add(findButton, gbc);
        frame.add(new JScrollPane(resultArea), gbc);

        findButton.addActionListener(e -> {
            String artist = artistField.getText().trim();
            if (artist.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter an artist name.");
            } else {
                String similarArtists = engine.getSimilarArtists(artist);
                resultArea.setText("Artists similar to " + artist + ":\n\n" + similarArtists);
            }
        });

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> {
            frame.dispose();
            showMenuGUI();
        });

        frame.add(backButton, gbc);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private void showPlaylistGUI() {
        JFrame frame = new JFrame("Generate Playlist");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel songCountLabel = new JLabel("Enter the number of songs:");
        JTextField songCountField = new JTextField(5);
        JButton nextButton = new JButton("Next");

        frame.add(songCountLabel, gbc);
        frame.add(songCountField, gbc);
        frame.add(nextButton, gbc);

        nextButton.addActionListener(e -> {
            String input = songCountField.getText().trim();
            try {
                int songCount = Integer.parseInt(input);
                if (songCount <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter a positive number.");
                } else {
                    frame.dispose();
                    promptSongsForPlaylist(songCount);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid number. Please enter an integer.");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void promptSongsForPlaylist(int songCount) {
        JFrame frame = new JFrame("Enter Songs for Playlist");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        List<JTextField> songFields = new ArrayList<>();
        List<JTextField> artistFields = new ArrayList<>();

        for (int i = 0; i < songCount; i++) {
            frame.add(new JLabel("Song " + (i + 1) + ":"), gbc);
            JTextField songField = new JTextField(20);
            songFields.add(songField);
            frame.add(songField, gbc);

            frame.add(new JLabel("Artist " + (i + 1) + ":"), gbc);
            JTextField artistField = new JTextField(20);
            artistFields.add(artistField);
            frame.add(artistField, gbc);
        }

        JButton generateButton = new JButton("Generate Playlist");
        frame.add(generateButton, gbc);

        generateButton.addActionListener(e -> {
            List<String> songsInput = new ArrayList<>();
            for (int i = 0; i < songCount; i++) {
                String song = songFields.get(i).getText().trim();
                String artist = artistFields.get(i).getText().trim();
                if (!song.isEmpty() && !artist.isEmpty()) {
                    songsInput.add(song + " - " + artist);
                }
            }

            if (songsInput.size() == songCount) {
                frame.dispose();
                displayGeneratedPlaylist(songsInput);
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill out all fields.");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void displayGeneratedPlaylist(List<String> songsInput) {
        JFrame frame = new JFrame("Generated Playlist");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        String generatedPlaylist = engine.generateSmoothPlaylist(songsInput);

        JTextArea playlistArea = new JTextArea();
        playlistArea.setEditable(false);
        playlistArea.setText("Generated Playlist:\n\n" + generatedPlaylist);

        JScrollPane scrollPane = new JScrollPane(playlistArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> {
            frame.dispose();
            showMenuGUI();
        });

        frame.add(backButton, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}