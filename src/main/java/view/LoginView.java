import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame implements IObserver {
    private JTextField nameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private IUserRepository userRepository;

    public LoginView(IUserRepository userRepository) {
        this.userRepository = userRepository;

        // Basic frame setup
        setTitle("Music Recommendation System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        // Create main panel with some padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Setup constraints for GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add name field
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        nameField = new JTextField(15);
        mainPanel.add(nameField, gbc);

        // Add password field
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        passwordField = new JPasswordField(15);
        mainPanel.add(passwordField, gbc);

        // Add login button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton = new JButton("Login");
        mainPanel.add(loginButton, gbc);

        // Add action listener for login button
        loginButton.addActionListener(e -> handleLogin());

        // Add panel to frame
        add(mainPanel);
    }

    private void handleLogin() {
        String name = nameField.getText();
        String password = new String(passwordField.getPassword());

        if (name.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            User user = userRepository.findById(name);
            if (user != null) {
                // In a real application, you would verify the password here
                // For now, we're just checking if user exists
                openMainView(user);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid credentials",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "An error occurred during login",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openMainView(User user) {
        // Here you would initialize and show your main view/menu
        // For example:
        // MenuView menuView = new MenuView(user);
        // menuView.setVisible(true);
        dispose(); // Close login window
    }

    @Override
    public void update(String message) {
        // Handle any updates from the subject
        // You might want to show status messages or handle errors
        JOptionPane.showMessageDialog(this, message);
    }

    // Example of how to launch the login view
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IUserRepository userRepository = new PostgresUserRepository();
            LoginView loginView = new LoginView(userRepository);
            loginView.setVisible(true);
        });
    }
}