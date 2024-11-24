package app.gui;

import app.engine.MusicRecommendationEngine;

import javax.swing.*;
import java.awt.*;

public class SimilarArtistsGUI {
    private final MusicRecommendationEngine engine;

    public SimilarArtistsGUI(MusicRecommendationEngine engine) {
        this.engine = engine;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Find Similar Artists");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField artistField = new JTextField(20);
        JButton searchButton = new JButton("Find Similar Artists");
        JTextArea resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        frame.add(new JLabel("Enter Artist Name:"), gbc);
        frame.add(artistField, gbc);
        frame.add(searchButton, gbc);
        frame.add(new JScrollPane(resultArea), gbc);

        searchButton.addActionListener(e -> {
            String artist = artistField.getText();
            if (!artist.isEmpty()) {
                String similarArtists = engine.getSimilarArtists(artist);
                resultArea.setText(similarArtists);
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter an artist name.");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}