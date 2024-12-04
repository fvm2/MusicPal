package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.recommendation.RecommendationController;
import interface_adapter.recommendation.RecommendationViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

public class RecommendationView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "rec view";
    private final RecommendationViewModel recommendationViewModel;
    private RecommendationController recommendationController;

    private final JButton profileButton;
    private final JButton generateButton;

    public RecommendationView(RecommendationViewModel recommendationViewModel) {
        this.recommendationViewModel = recommendationViewModel;
        recommendationViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Get Recommendation");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // user preference dropdown.
        String[] choices = {"Song", "Album", "Artist"};
        final JComboBox<String> dataTypePref = new JComboBox<>(choices);
        dataTypePref.setAlignmentX(Component.CENTER_ALIGNMENT);
        dataTypePref.setSelectedIndex(0);

        final JTextArea userDataInput = new JTextArea(20, 20);

        final JPanel buttons = new JPanel();
        profileButton = new JButton("Profile");
        generateButton = new JButton("Get Recommendation");
        buttons.add(profileButton);
        buttons.add(generateButton);

        profileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                recommendationController.switchToProfileView();
            }
        });

        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String dataType = dataTypePref.getSelectedItem().toString();
                String userData = userDataInput.getText();
                ArrayList<String> userDataFormatted = new ArrayList<>(Arrays.asList(userData.split("\\r?\\n")));
                System.out.println(userDataFormatted);
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(dataTypePref);
        this.add(userDataInput);
        this.add(buttons);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "hi");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public String getViewName() {
        return viewName;
    }

    public void setRecommendationController(RecommendationController recommendationController) {
        this.recommendationController = recommendationController;
    }
}
