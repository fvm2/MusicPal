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
    private final JButton profile;
    private final JButton songRec;
    private final JButton playlistRec;
    private final JButton artistRec;
    private MenuController menuController;

    public MenuView(MenuViewModel menuViewModel) {
        this.menuViewModel = menuViewModel;
        menuViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(MenuViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttons = new JPanel();
        profile = new JButton(MenuViewModel.PROFILE_LABEL);
        buttons.add(profile);
        songRec = new JButton(MenuViewModel.SONG_REC_LABEL);
        buttons.add(songRec);
        playlistRec = new JButton(MenuViewModel.PLAYLIST_REC_LABEL);
        buttons.add(playlistRec);
        artistRec = new JButton(MenuViewModel.ARTIST_REC_LABEL);
        buttons.add(artistRec);

        profile.addActionListener(this);
        songRec.addActionListener(this);
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
