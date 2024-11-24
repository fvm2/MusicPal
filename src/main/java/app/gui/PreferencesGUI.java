package app.gui;

import app.engine.MusicRecommendationEngine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PreferencesGUI {
    private final MusicRecommendationEngine engine;

    public PreferencesGUI(MusicRecommendationEngine engine) {
        this.engine = engine;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Enter Your Music Preferences");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField[] songFields = new JTextField[3];
        JTextField[] artistFields = new JTextField[3];
        List<String> preferences = new ArrayList<>();

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
            preferences.clear();
            for (int i = 0; i < 3; i++) {
                String song = songFields[i].getText();
                String artist = artistFields[i].getText();
                if (!song.isEmpty() && !artist.isEmpty()) {
                    preferences.add(song + " - " + artist);
                }
            }

            if (preferences.size() == 3) {
                JOptionPane.showMessageDialog(frame, "Preferences saved successfully!");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}