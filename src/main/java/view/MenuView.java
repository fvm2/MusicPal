package view;

import interface_adapter.menu.MenuController;
import interface_adapter.menu.MenuViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MenuView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "menu";

    private final MenuViewModel menuViewModel;
    private final JButton profile = new JButton("profile");
    private final JButton songRec = new JButton("song recommendation");
    private final JButton playlistRec  = new JButton("playlist recommendation");
    private final JButton artistRec = new JButton("artist recommendation");
    private MenuController menuController;

    public MenuView(MenuViewModel menuViewModel) {
        this.menuViewModel = menuViewModel;
        menuViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(viewName);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // buttons panel
        final JPanel buttons = new JPanel();
        buttons.add(profile);
        buttons.add(songRec);
        buttons.add(playlistRec);
        buttons.add(artistRec);

        // setting up funcitonality for the buttons
        profile.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        menuController.switchToProfileView();
                    }
                }
        );
        songRec.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        menuController.switchToSongRecView();
                    }
                }
        );
        playlistRec.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        menuController.switchToPlaylistRecView();
                    }
                }
        );
        artistRec.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        menuController.switchToArtistRecView();
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.add(title);
        this.add(buttons);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

    public String getViewName() {
        return viewName;
    }

    public void setController(MenuController menuController) {
        this.menuController = menuController;
    }
}