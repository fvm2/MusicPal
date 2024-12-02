package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.artist_recommendation.ArtistController;
import interface_adapter.artist_recommendation.ArtistRecViewModel;
import interface_adapter.artist_recommendation.ArtistState;

public class ArtistRecView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "artist recommendation";

    private final ArtistRecViewModel artistRecViewModel;
    private ArtistController artistController;

    private final JButton getRecommendation;
    private final JTextField artistInputField = new JTextField(20);

    public ArtistRecView(ArtistRecViewModel artistRecViewModel) {
        this.artistRecViewModel = artistRecViewModel;
        artistRecViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(ArtistRecViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel artistInfo = new JPanel();
        final JLabel artistLabel = new JLabel(ArtistRecViewModel.ARTIST_LABEL);
        artistInfo.add(artistLabel);
        artistInfo.add(artistInputField);

        final JPanel buttons = new JPanel();
        getRecommendation = new JButton(ArtistRecViewModel.GET_RECOMMENDATION_BUTTON_LABEL);
        buttons.add(getRecommendation);

        final JTextArea resultArea = new JTextArea(ArtistRecViewModel.GET_RECOMMENDATION_WINDOW_WIDTH, ArtistRecViewModel.GET_RECOMMENDATION_WINDOW_HEIGHT);
        resultArea.setEditable(false);

        artistInfo.add(resultArea);
        final JButton backToMenu = new JButton(ArtistRecViewModel.MENU_LABEL);
        buttons.add(backToMenu);
        backToMenu.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(backToMenu)) {
                            artistController.switchToMenuView();
                        }
                    }
                }
        );

        getRecommendation.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(getRecommendation)) {
                            final ArtistState currentState = artistRecViewModel.getState();

                            artistController.execute(currentState.getArtistName());
                            final String recommendations = artistRecViewModel.getState().getRecommendation();
                            resultArea.setText(recommendations);
                        }
                    }
                }
        );

        addArtistListener();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(artistInfo);
        this.add(buttons);
    }

    private void addArtistListener() {
        artistInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final ArtistState currentState = artistRecViewModel.getState();
                currentState.setArtistName(artistInputField.getText());
                artistRecViewModel.setState(currentState);
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
        final ArtistState state = (ArtistState) evt.getNewValue();
        if (state.getArtistNameError() != null) {
            JOptionPane.showMessageDialog(this, state.getArtistNameError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setController(ArtistController controller) {
        this.artistController = controller;
    }

}
