package view;

import javax.swing.*;
import java.awt.*;

public class SignupView {
    private String name;
    private String email;
    private String password;

    public SignupView() {
        this.name = "";
        this.email = "";
        this.password = "";
        createAndShowSignUpGUI();
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
        JButton logInButton = new JButton("Have an Account? Log In");

        frame.add(logInButton, gbc);
        frame.add(new JLabel("Name:"), gbc);
        frame.add(nameField, gbc);
        frame.add(new JLabel("Email:"), gbc);
        frame.add(emailField, gbc);
        frame.add(new JLabel("Password:"), gbc);
        frame.add(passwordField, gbc);
        frame.add(signUpButton, gbc);

        signUpButton.addActionListener(e -> {
            if (!nameField.getText().isEmpty()) {
                this.name = nameField.getText();
                this.email = emailField.getText();
                this.password = passwordField.getText();
                frame.dispose();
                // final MenuView menuView = new MenuView();
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
