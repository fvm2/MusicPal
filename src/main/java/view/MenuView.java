package view;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import interface_adapter.menu.MenuController;
import interface_adapter.menu.MenuViewModel;

public class MenuView extends JPanel implements PropertyChangeListener {
    private MenuController menuController;

    public MenuView(MenuViewModel menuViewModel) {
        menuViewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JLabel title = new JLabel("Menu");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Profile button
        JButton profileButton = new JButton("Open Profile");
        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileButton.addActionListener(e -> {
            if (menuController != null) {
                menuController.openProfile();
            } else {
                System.err.println("MenuController is not set. Cannot open Profile.");
            }
        });

        // Artist Recommendations button
        JButton artistRecButton = new JButton("Artist Recommendations");
        artistRecButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        artistRecButton.addActionListener(e -> {
            if (menuController != null) {
                menuController.openArtistRecommendations();
            } else {
                System.err.println("MenuController is not set. Cannot open Artist Recommendations.");
            }
        });

        // Song Recommendations button
        JButton songRecButton = new JButton("Song Recommendations");
        songRecButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        songRecButton.addActionListener(e -> {
            if (menuController != null) {
                menuController.openSongRecommendations();
            } else {
                System.err.println("MenuController is not set. Cannot open Song Recommendations.");
            }
        });

        // Playlist Recommendations button
        JButton playlistRecButton = new JButton("Playlist Recommendations");
        playlistRecButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playlistRecButton.addActionListener(e -> {
            if (menuController != null) {
                menuController.openPlaylistRecommendations();
            } else {
                System.err.println("MenuController is not set. Cannot open Playlist Recommendations.");
            }
        });

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.addActionListener(e -> {
            if (menuController != null) {
                menuController.logout();
            } else {
                System.err.println("MenuController is not set. Cannot log out.");
            }
        });

        add(title);
        add(profileButton);
        add(artistRecButton);
        add(songRecButton);
        add(playlistRecButton);
        add(logoutButton);
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("screen".equals(evt.getPropertyName())) {
            System.out.println("Navigated to: " + evt.getNewValue());
        } else if ("username".equals(evt.getPropertyName())) {
            System.out.println("Username updated to: " + evt.getNewValue());
        }
    }
}
