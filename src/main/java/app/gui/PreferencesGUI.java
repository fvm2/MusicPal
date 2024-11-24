package app.gui;

import app.AppController;

import javax.swing.*;
import java.awt.*;

public class PreferencesGUI {
    private final AppController controller;

    public PreferencesGUI(AppController controller) {
        this.controller = controller;
    }

    public void display() {
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
            String[] songs = new String[3];
            String[] artists = new String[3];
            boolean validInput = true;

            for (int i = 0; i < 3; i++) {
                songs[i] = songFields[i].getText();
                artists[i] = artistFields[i].getText();
                if (songs[i].isEmpty() || artists[i].isEmpty()) {
                    validInput = false;
                }
            }

            if (validInput) {
                frame.dispose();
                controller.submitPreferences(songs, artists);
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter all songs and artists");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

