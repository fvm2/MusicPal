package view;

import interface_adapter.profile.ProfileController;
import interface_adapter.profile.ProfileViewModel;
import interface_adapter.profile.ProfileState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class ProfileView extends JPanel implements PropertyChangeListener {
    private final String viewName = "profile";
    private final ProfileViewModel profileViewModel;

    // User info components
    private final JLabel titleLabel = new JLabel(ProfileViewModel.TITLE_LABEL);
    private final JLabel emailLabel = new JLabel();
    private final JLabel nameLabel = new JLabel();
    private final JLabel surnameLabel = new JLabel();
    private final JLabel countryLabel = new JLabel();

    // Profile picture placeholder
    private final JLabel profilePicture = new JLabel();

    private final JTextArea recommendationHistoryArea = new JTextArea(10, 30);
    private final JLabel errorLabel = new JLabel();
    private final JButton logoutButton;
    private final JButton backToMenuButton;
    private ProfileController profileController;

    public ProfileView(ProfileViewModel profileViewModel) {
        this.profileViewModel = profileViewModel;
        profileViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // Title at top
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Profile Section (Left Side)
        JPanel profileSection = new JPanel(new BorderLayout());

        // Profile Picture (placeholder)
        profilePicture.setPreferredSize(new Dimension(150, 150));
        profilePicture.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        profilePicture.setHorizontalAlignment(SwingConstants.CENTER);
        profilePicture.setText("Profile Picture");

        // User Info Panel
        JPanel userInfoPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        userInfoPanel.add(emailLabel);
        userInfoPanel.add(nameLabel);
        userInfoPanel.add(surnameLabel);
        userInfoPanel.add(countryLabel);

        profileSection.add(profilePicture, BorderLayout.NORTH);
        profileSection.add(userInfoPanel, BorderLayout.CENTER);

        // History Panel (Right Side)
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder(ProfileViewModel.HISTORY_LABEL));
        recommendationHistoryArea.setEditable(false);
        historyPanel.add(new JScrollPane(recommendationHistoryArea), BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        logoutButton = new JButton(ProfileViewModel.LOGOUT_BUTTON_LABEL);
        backToMenuButton = new JButton(ProfileViewModel.BACK_MENU_BUTTON_LABEL);
        buttonPanel.add(logoutButton);
        buttonPanel.add(backToMenuButton);

        logoutButton.addActionListener(e -> profileController.executeLogout());
        backToMenuButton.addActionListener(e -> profileController.executeBackToMenu());

        // Error Label
        errorLabel.setForeground(Color.RED);

        // Main Layout Assembly
        add(titleLabel, BorderLayout.NORTH);
        add(profileSection, BorderLayout.WEST);
        add(historyPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(errorLabel, BorderLayout.NORTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ProfileState state = profileViewModel.getState();

        emailLabel.setText(ProfileViewModel.EMAIL_LABEL + state.getEmail());
        nameLabel.setText(ProfileViewModel.NAME_LABEL + state.getName());
        surnameLabel.setText(ProfileViewModel.SURNAME_LABEL + state.getSurname());
        countryLabel.setText(ProfileViewModel.COUNTRY_LABEL + state.getCountry());

        if (state.getError() != null) {
            errorLabel.setText(state.getError());
        } else {
            errorLabel.setText("");
            updateRecommendationHistory(state.getRecommendationHistory());
        }
    }

    private void updateRecommendationHistory(List<String> history) {
        if (history == null) return;
        StringBuilder sb = new StringBuilder();
        for (String recommendation : history) {
            sb.append(recommendation).append("\n");
        }
        recommendationHistoryArea.setText(sb.toString());
    }

    public String getViewName() {
        return viewName;
    }

    public void setProfileController(ProfileController profileController) {
        this.profileController = profileController;
    }
}