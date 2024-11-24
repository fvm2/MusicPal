package app.gui;

import app.AppController;

import javax.swing.*;
import java.awt.*;

public class SignUpGUI {
    private final AppController controller;

    public SignUpGUI(AppController controller) {
        this.controller = controller;
    }

    public void display() {
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
                controller.launchPreferencesGui();
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
