package presentation;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import domain.User;
import domain.Recommendation;
import application.UserService;
import application.RecommendationService;

public class UserProfileWindow {
    private final UserService userService;
    private final RecommendationService recommendationService;
    private final JFrame mainFrame;

    public UserProfileWindow(UserService userService, RecommendationService recommendationService) {
        this.userService = userService;
        this.recommendationService = recommendationService;
        this.mainFrame = new JFrame("User Profile");
        setupMainFrame();
    }

    private void setupMainFrame() {
        mainFrame.setSize(600, 500);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout(10, 10));

        // Get current user and recommendation history
        User currentUser = userService.getCurrentUser();
        List<Recommendation> history = userService.getRecommendationHistory();

        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // User Info Section
        JPanel userInfoPanel = createUserInfoPanel(currentUser);
        mainPanel.add(userInfoPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Preferences Section
        JPanel preferencesPanel = createPreferencesPanel(currentUser);
        mainPanel.add(preferencesPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Recommendation History Section
        JPanel historyPanel = createHistoryPanel(history);
        mainPanel.add(historyPanel);

        // Add scroll capability
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainFrame.add(scrollPane, BorderLayout.CENTER);

        // Navigation buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton recommendationsButton = new JButton("Get New Recommendations");
        JButton logoutButton = new JButton("Logout");

        recommendationsButton.addActionListener(e -> {
            mainFrame.dispose();
            // Navigate to recommendations window
            new MusicRecommendationGUI.RecommendationWindow(userService, recommendationService).show();
        });

        logoutButton.addActionListener(e -> {
            userService.logout();
            mainFrame.dispose();
            new LoginWindow(userService, new MusicRecommendationGUI(userService, recommendationService)).show();
        });

        buttonPanel.add(recommendationsButton);
        buttonPanel.add(logoutButton);
        mainFrame.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createUserInfoPanel(User user) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("User Information"));

        panel.add(new JLabel("Name: " + user.getName()));
        panel.add(new JLabel("Email: " + user.getEmail()));
        panel.add(new JLabel("Country: " + user.getCountry()));

        return panel;
    }

    private JPanel createPreferencesPanel(User user) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Music Preferences"));

        JTextArea preferencesArea = new JTextArea();
        preferencesArea.setEditable(false);
        preferencesArea.setLineWrap(true);
        preferencesArea.setWrapStyleWord(true);

        for (String preference : user.getPreferences().getFavoriteSongs()) {
            preferencesArea.append(preference + "\n");
        }

        panel.add(new JScrollPane(preferencesArea));
        return panel;
    }

    private JPanel createHistoryPanel(List<Recommendation> history) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Recommendation History"));

        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setLineWrap(true);
        historyArea.setWrapStyleWord(true);

        for (Recommendation rec : history) {
            historyArea.append(rec.toString() + "\n");
        }

        panel.add(new JScrollPane(historyArea));
        return panel;
    }

    public void show() {
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}