package view;

import javax.swing.*;
import java.awt.*;

public class MenuView {
    public MenuView() {
        showMenuGUI();
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
            final SongRecView songRecView = new SongRecView();
        });

        similarArtistsButton.addActionListener(e -> {
            frame.dispose();
            final ArtistRecView artistRecView = new ArtistRecView();
        });

        playlistButton.addActionListener(e -> {
            frame.dispose();
            final PlaylistRecView playlistRecView = new PlaylistRecView();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
