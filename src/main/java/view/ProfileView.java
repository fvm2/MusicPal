package view;

import interface_adapter.profile.ProfileController;
import interface_adapter.profile.ProfileState;
import interface_adapter.profile.ProfileViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * View for the Profile feature.
 */
public class ProfileView extends JPanel implements PropertyChangeListener {
    private final ProfileController profileController;

    private JLabel usernameLabel;
    private JTextArea favoritesArea;
    private JTextArea friendsArea;

    public ProfileView(ProfileViewModel profileViewModel, ProfileController profileController) {
        this.profileController = profileController;

        profileViewModel.addPropertyChangeListener(this);

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        usernameLabel = new JLabel();
        favoritesArea = new JTextArea();
        favoritesArea.setEditable(false);
        friendsArea = new JTextArea();
        friendsArea.setEditable(false);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> profileController.logout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(usernameLabel, BorderLayout.WEST);
        topPanel.add(logoutButton, BorderLayout.EAST);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));

        JPanel favoritesPanel = new JPanel(new BorderLayout());
        favoritesPanel.add(new JLabel("Favorites:"), BorderLayout.NORTH);
        favoritesPanel.add(new JScrollPane(favoritesArea), BorderLayout.CENTER);

        JPanel friendsPanel = new JPanel(new BorderLayout());
        friendsPanel.add(new JLabel("Friends:"), BorderLayout.NORTH);
        friendsPanel.add(new JScrollPane(friendsArea), BorderLayout.CENTER);

        centerPanel.add(favoritesPanel);
        centerPanel.add(friendsPanel);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ProfileState state = (ProfileState) evt.getNewValue();
        updateView(state);
    }

    private void updateView(ProfileState state) {
        usernameLabel.setText("Welcome, " + state.getUsername());

        StringBuilder favoritesText = new StringBuilder();
        if (state.getFavorites() != null) {
            for (String favorite : state.getFavorites()) {
                favoritesText.append(favorite).append("\n");
            }
        }
        favoritesArea.setText(favoritesText.toString());

        StringBuilder friendsText = new StringBuilder();
        if (state.getFriends() != null) {
            for (String friend : state.getFriends()) {
                friendsText.append(friend).append("\n");
            }
        }
        friendsArea.setText(friendsText.toString());
    }
}
