package app.gui;

import app.engine.MusicRecommendationEngine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistGeneratorGUI {
    private final MusicRecommendationEngine engine;

    public PlaylistGeneratorGUI(MusicRecommendationEngine engine) {
        this.engine = engine;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Playlist Generator");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField songCountField = new JTextField(20);
        JButton nextButton = new JButton("Next");

        frame.add(new JLabel("How many songs do you want to input?"), gbc);
        frame.add(songCountField, gbc);
        frame.add(nextButton, gbc);

        nextButton.addActionListener(e -> {
            try {
                int n = Integer.parseInt(songCountField.getText());
                if (n > 0) {
                    frame.dispose();
                    promptForSongs(n);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void promptForSongs(int n) {
        JFrame frame = new JFrame("Input Songs");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        List<JTextField> songFields = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            JTextField songField = new JTextField(20);
            songFields.add(songField);

            frame.add(new JLabel("Song " + (i + 1) + ":"), gbc);
            frame.add(songField, gbc);
        }

        JButton generateButton = new JButton("Generate Playlist");
        frame.add(generateButton, gbc);

        generateButton.addActionListener(e -> {
            List<String> songs = new ArrayList<>();
            for (JTextField songField : songFields) {
                String song = songField.getText();
                if (!song.isEmpty()) {
                    songs.add(song);
                }
            }

            if (songs.size() == n) {
                frame.dispose();
                showPlaylist(songs);
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill in all song fields.");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showPlaylist(List<String> songs) {
        JFrame frame = new JFrame("Generated Playlist");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        String playlist = engine.generateSmoothPlaylist(songs);

        JTextArea playlistArea = new JTextArea();
        playlistArea.setEditable(false);
        playlistArea.setText(playlist);

        JScrollPane scrollPane = new JScrollPane(playlistArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> frame.dispose());
        frame.add(closeButton, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
