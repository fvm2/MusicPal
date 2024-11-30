package view;

import interface_adapter.artist_recommendation.ArtistController;
import interface_adapter.artist_recommendation.ArtistRecViewModel;

import javax.swing.*;
import java.awt.*;

public class ArtistRecView {
    private final ArtistController artistController;
    private final ArtistRecViewModel artistRecViewModel;

    public ArtistRecView(String recommendations) {
        
    }

    private void showSimilarArtistsGUI(String recommendations) {
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
                final ArtistController artistController = new ArtistController(artist);

                String similarArtists = engine.getSimilarArtists(artist);
                resultArea.setText("Artists similar to " + artist + ":\n\n" + similarArtists);
            }
        });

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> {
            frame.dispose();
            final MenuView menuView = new MenuView();
        });

        frame.add(backButton, gbc);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
