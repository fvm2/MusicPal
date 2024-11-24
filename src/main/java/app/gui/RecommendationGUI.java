package app.gui;

import app.AppController;

import javax.swing.*;
import java.awt.*;

public class RecommendationGUI {
    private final AppController controller;

    public RecommendationGUI(AppController controller) {
        this.controller = controller;
    }

    public void display(String recommendations) {
        JFrame frame = new JFrame("Your Music Recommendations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JTextArea recommendationsArea = new JTextArea();
        recommendationsArea.setEditable(false);
        recommendationsArea.setText(recommendations);

        JScrollPane scrollPane = new JScrollPane(recommendationsArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        frame.add(exitButton, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

