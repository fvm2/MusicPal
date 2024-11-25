package presentation;

import javax.swing.*;
import java.awt.*;
import application.UserService;

public class LoginWindow {
    private final UserService userService;
    private final MusicRecommendationGUI mainGUI;

    public LoginWindow(UserService userService, MusicRecommendationGUI mainGUI) {
        this.userService = userService;
        this.mainGUI = mainGUI;
    }

    public void show() {
        JFrame frame = new JFrame("Music Recommendation System - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField emailField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register New User");

        frame.add(new JLabel("Email:"), gbc);
        frame.add(emailField, gbc);
        frame.add(new JLabel("Password:"), gbc);
        frame.add(passwordField, gbc);
        frame.add(loginButton, gbc);
        frame.add(registerButton, gbc);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (userService.login(email, password)) {
                frame.dispose();
                mainGUI.showPreferencesOrRecommendationGUI();
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Invalid email or password",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            frame.dispose();
            mainGUI.showSignUpGUI();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
