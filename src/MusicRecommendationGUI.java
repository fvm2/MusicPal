package presentation;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import entity.UserPreferences;
import entity.Recommendation;
import application.RecommendationService;

public class MusicRecommendationGUI {
    private final RecommendationService recommendationService;
    private UserPreferences userPreferences;

    public MusicRecommendationGUI(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
        this.userPreferences = new UserPreferences();
    }

    private void showRecommendationGUI() {
        final JFrame frame = new JFrame("Get Music Recommendations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridBagLayout());

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Create components
        final JTextArea recommendationArea = new JTextArea(10, 30);
        recommendationArea.setEditable(false);
        recommendationArea.setLineWrap(true);
        recommendationArea.setWrapStyleWord(true);

        final JScrollPane scrollPane = new JScrollPane(recommendationArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        final JButton getRecommendationButton = new JButton("Get Recommendation");
        final JButton dislikeButton = new JButton("Dislike");

        // Add components
        frame.add(scrollPane, gbc);
        frame.add(getRecommendationButton, gbc);
        frame.add(dislikeButton, gbc);

        // Action listener for "Get Recommendation"
        getRecommendationButton.addActionListener(e -> {
            getRecommendationButton.setEnabled(false);
            recommendationArea.setText("Getting recommendations...");

            // Use SwingWorker for asynchronous API call
            new SwingWorker<List<Recommendation>, Void>() {
                @Override
                protected List<Recommendation> doInBackground() {
                    return recommendationService.getRecommendations(userPreferences, 1, "Songs");
                }

                @Override
                protected void done() {
                    try {
                        final List<Recommendation> recommendations = get();
                        recommendationArea.setText(formatRecommendations(recommendations));
                    }
                    catch (InterruptedException | ExecutionException ex) {
                        recommendationArea.setText("Error getting recommendations: " + ex.getMessage());
                    }
                    finally {
                        getRecommendationButton.setEnabled(true);
                    }
                }
            }.execute();
        });

        // Action listener for "Dislike"
        dislikeButton.addActionListener(e -> {
            final String selectedSong = recommendationArea.getSelectedText();
            if (selectedSong != null && !selectedSong.isEmpty()) {
                userPreferences.addDislikedSong(selectedSong);
                JOptionPane.showMessageDialog(frame, "Marked as disliked: " + selectedSong);
            }
            else {
                JOptionPane.showMessageDialog(frame, "No song selected to dislike.");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private String formatRecommendations(List<Recommendation> recommendations) {
        if (recommendations == null || recommendations.isEmpty()) {
            return "No recommendations found.";
        }

        return recommendations.stream()
                .map(Recommendation::toString)
                .collect(Collectors.joining("\n\n"));
    }
}
