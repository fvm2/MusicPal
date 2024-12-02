package view;

import interface_adapter.profile.ProfileController;
import interface_adapter.profile.ProfileState;
import interface_adapter.profile.ProfileViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ProfileView extends JPanel implements PropertyChangeListener {

    private final String viewName = "profile";
    private final ProfileViewModel profileViewModel;
    private final JLabel usernameLabel = new JLabel();
    private final JTextArea favoritesArea = new JTextArea(10, 30);
    private final JTextArea friendsArea = new JTextArea(10, 30);
    private final JButton showFavoritesButton = new JButton("Show Favorites");
    private final JButton showFriendsButton = new JButton("Show Friends");
    private final JButton logoutButton = new JButton("Logout");
    private final JLabel errorLabel = new JLabel();

    private ProfileController profileController;

    public ProfileView(ProfileViewModel profileViewModel) {
        this.profileViewModel = profileViewModel;

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Profile for user: "));
        topPanel.add(usernameLabel);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        favoritesArea.setEditable(false);
        friendsArea.setEditable(false);
        centerPanel.add(new JScrollPane(favoritesArea));
        centerPanel.add(new JScrollPane(friendsArea));

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(showFavoritesButton);
        bottomPanel.add(showFriendsButton);
        bottomPanel.add(logoutButton);

        add(errorLabel, BorderLayout.NORTH);
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        showFavoritesButton.addActionListener(e -> profileController.showFavorites());
        showFriendsButton.addActionListener(e -> profileController.showFriends());
        logoutButton.addActionListener(e -> profileController.logout());

        profileViewModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ProfileState state = profileViewModel.getState();
        usernameLabel.setText(state.getUsername());

        if ("favorites".equals(evt.getPropertyName())) {
            favoritesArea.setText(String.join("\n", state.getFavorites()));
            errorLabel.setText("");
        } else if ("friends".equals(evt.getPropertyName())) {
            friendsArea.setText(String.join("\n", state.getFriends()));
            errorLabel.setText("");
        } else if ("error".equals(evt.getPropertyName())) {
            errorLabel.setText("Error: " + state.getError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setProfileController(ProfileController profileController) {
        this.profileController = profileController;
    }
}