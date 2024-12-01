package view;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyChangeListener;
//
//import java.awt.Component;
//import java.util.ArrayList;
//import java.util.List;
//import javax.swing.JButton;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//import javax.swing.*;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
//
//import interface_adapter.artist_recommendation.ArtistController;
//import interface_adapter.artist_recommendation.ArtistRecViewModel;
//import interface_adapter.artist_recommendation.ArtistState;
//import interface_adapter.playlist.PlaylistController;
//import use_case.recommend_playlist.PlaylistRecViewModel;
//
//public class PlaylistRecView extends JPanel implements ActionListener, PropertyChangeListener {
//    private final String viewName = "playlist recommendation";
//
//    private final PlaylistRecViewModel playlistRecViewModel;
//    private PlaylistController playlistController;
//
//    private final JButton nextButton;
//    private final JTextField songCountField = new JTextField(5);
//
//    public PlaylistRecView(PlaylistRecViewModel playlistRecViewModel) {
//        this.playlistRecViewModel = playlistRecViewModel;
//        playlistRecViewModel.addPropertyChangeListener(this);
//
//        final JLabel title = new JLabel(PlaylistRecViewModel.TITLE_LABEL);
//        title.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        final JPanel playlistInfo = new JPanel();
//        final JLabel playlistNumberLabel = new JLabel(PlaylistRecViewModel.NUMBER_LABEL);
//        playlistInfo.add(playlistNumberLabel);
//        playlistInfo.add(songCountField);
//
//        final JPanel buttons = new JPanel();
//        final JPanel inputPanel = new JPanel();
//        nextButton = new JButton(ArtistRecViewModel.NEXT_BUTTON_LABEL);
//        buttons.add(nextButton);
//
//        final JTextArea resultArea = new JTextArea(ArtistRecViewModel.GET_RECOMMENDATION_WINDOW_WIDTH, ArtistRecViewModel.GET_RECOMMENDATION_WINDOW_HEIGHT);
//        resultArea.setEditable(false);
//
//        playlistInfo.add(resultArea);
//        final JButton backToMenu = new JButton(ArtistRecViewModel.MENU_LABEL);
//        buttons.add(backToMenu);
//        backToMenu.addActionListener(
//                new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {
//                        if (evt.getSource().equals(backToMenu)) {
//                            playlistController.switchToMenuView();
//                        }
//                    }
//                }
//        );
//
//        nextButton.addActionListener(
//                new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {
//                        if (evt.getSource().equals(nextButton)) {
//                            try {
//                                final int n = Integer.parseInt(songCountField.getText());
//                                if (n > 0 && n < 21) {
//                                    List<JTextField> songFields = new ArrayList<>();
//                                    for (int i = 0; i < n; i++) {
//                                        JTextField songField = new JTextField(20);
//                                        songFields.add(songField);
//
//                                        inputPanel.add(new JLabel("Song " + (i + 1) + ":"));
//                                        inputPanel.add(songField);
//                                    }
//
//                                    JButton generateButton = new JButton("Generate Playlist");
//                                    inputPanel.add(generateButton);
//
//                                    generateButton.addActionListener(e -> {
//                                        List<String> songs = new ArrayList<>();
//                                        for (JTextField songField : songFields) {
//                                            String song = songField.getText();
//                                            if (!song.isEmpty()) {
//                                                songs.add(song);
//                                            }
//                                        }
//                                        playlistRecViewModel.setRequestedSongs(songs);
//
//                                        if (songs.size() == n) {
//                                            final PlaylistState currentState = playlistRecViewModel.getState();
//
//                                            playlistController.execute(currentState.getRequestedSongs());
//                                            final String recommendations = playlistRecViewModel.getState().getRecommendation();
//                                            resultArea.setText(recommendations);
//                                        } else {
//                                            JOptionPane.showMessageDialog(inputPanel, "Please fill in all song fields.");
//                                        }
//                                    });
//                                } else {
//                                    JOptionPane.showMessageDialog(playlistInfo, "Please enter a valid number (0-20).");
//                                }
//                            } catch (NumberFormatException ex) {
//                                JOptionPane.showMessageDialog(playlistInfo, "Please enter a valid number.");
//                            }
//                        }
//                    }
//                }
//        );
//
//        addNumberListener();
//
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//
//        this.add(title);
//        this.add(playlistInfo);
//        this.add(buttons);
//        this.add(inputPanel);
//    }
//
//    private void addNumberListener() {
//        songCountField.getDocument().addDocumentListener(new DocumentListener() {
//            private void documentListenerHelper() {
//                final PlaylistState currentState = playlistRecViewModel.getState();
//                currentState.setSongCount(songCountField.getText());
//                playlistRecViewModel.setState(currentState);
//            }
//
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//        });
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent evt) {
//        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
//    }
//
//    @Override
//    public void propertyChange(PropertyChangeEvent evt) {
//        final PlaylistState state = (PlaylistState) evt.getNewValue();
//        if (state.getSongCountError() != null) {
//            JOptionPane.showMessageDialog(this, state.getSongCountError());
//        }
//    }
//
//    public String getViewName() {
//        return viewName;
//    }
//
//    public void setController(PlaylistController controller) {
//        this.playlistController = controller;
//    }
//
//}
//
