package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.artist_recommendation.ArtistRecViewModel;
import interface_adapter.song_recommendation.SongRecController;
import interface_adapter.song_recommendation.SongRecViewModel;
import interface_adapter.song_recommendation.SongState;

public class SongRecView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "song recommendation";

    private final SongRecViewModel songRecViewModel;
    private SongRecController songController;

    private final JButton nextButton;
    private final JTextField songCountField = new JTextField(5);

    public SongRecView(SongRecViewModel songRecViewModel) {
        this.songRecViewModel = songRecViewModel;
        songRecViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(SongRecViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel songInfo = new JPanel();
        final JLabel songNumberLabel = new JLabel(SongRecViewModel.NUMBER_LABEL);
        songInfo.add(songNumberLabel);
        songInfo.add(songCountField);

        final JPanel buttons = new JPanel();
        final JPanel inputPanel = new JPanel();
        nextButton = new JButton(SongRecViewModel.NEXT_BUTTON_LABEL);

        final JTextArea resultArea = new JTextArea(SongRecViewModel.GET_RECOMMENDATION_WINDOW_WIDTH, SongRecViewModel.GET_RECOMMENDATION_WINDOW_HEIGHT);
        resultArea.setEditable(false);

        songInfo.add(resultArea);
        final JButton backToMenu = new JButton(ArtistRecViewModel.MENU_LABEL);
        buttons.add(nextButton);
        backToMenu.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(backToMenu)) {
                            songController.switchToMenuView();
                        }
                    }
                }
        );

        nextButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(nextButton)) {
                            try {
                                final int n = Integer.parseInt(songCountField.getText());
                                if (n > 0 && n < SongRecViewModel.MAX_SONGS + 1) {
                                    inputPanel.removeAll();
                                    final List<JTextField> songFields = new ArrayList<>();
                                    final List<JTextField> artistFields = new ArrayList<>();
                                    for (int i = 0; i < n; i++) {
                                        final JPanel songPanel = new JPanel();
                                        final JTextField songField = new JTextField(20);
                                        final JTextField artistField = new JTextField(20);
                                        songFields.add(songField);
                                        artistFields.add(artistField);

                                        songPanel.add(new JLabel("Song " + (i + 1) + ":"));
                                        songPanel.add(songField);
                                        songPanel.add(new JLabel("Artist " + (i + 1) + ":"));
                                        songPanel.add(artistField);
                                        songPanel.setLayout(new BoxLayout(songPanel, BoxLayout.Y_AXIS));
                                        inputPanel.add(songPanel);
                                    }

                                    final JButton generateButton = new JButton("Generate Songs");
                                    inputPanel.add(generateButton);

                                    generateButton.addActionListener(e -> {
                                        final List<String> songs = new ArrayList<>();

                                        for (int i = 0; i < n; i++) {
                                            final String song = songFields.get(i).getText();
                                            final String artist = artistFields.get(i).getText();
                                            final String fullSongName = song + " - " + artist;
                                            if (!song.isEmpty() && !artist.isEmpty()) {
                                                songs.add(fullSongName);
                                            }
                                        }

                                        if (songs.size() == n) {
                                            songController.execute(songs);
                                            final String recommendations = songRecViewModel.getState().getRecommendation();
                                            resultArea.setText(recommendations);
                                        } else {
                                            JOptionPane.showMessageDialog(inputPanel, "Please fill in all song fields.");
                                        }
                                    });
                                } else {
                                    JOptionPane.showMessageDialog(songInfo, "Please enter a valid number (0-20).");
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(songInfo, "Please enter a valid number.");
                            }
                        }
                    }
                }
        );

        addNumberListener();

        final JPanel menuPanel = new JPanel();
        menuPanel.add(backToMenu);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(songInfo);
        this.add(inputPanel);
        this.add(buttons);
        this.add(menuPanel);
    }

    private void addNumberListener() {
        songCountField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SongState currentState = songRecViewModel.getState();
                currentState.setNumSongs(Integer.parseInt(songCountField.getText()));
                songRecViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SongState state = (SongState) evt.getNewValue();
        if (state.getNumSongsError() != null) {
            JOptionPane.showMessageDialog(this, state.getNumSongsError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setController(SongRecController controller) {
        this.songController = controller;
    }

}
