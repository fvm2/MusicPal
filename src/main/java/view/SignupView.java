package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.*;

import interface_adapter.note.SignupController;
import interface_adapter.note.NoteState;
import interface_adapter.note.SignupViewModel;

/**
 * The View for when the user is viewing a note in the program.
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {

    private final SignupViewModel signupViewModel;

    private final JLabel titleLabel = new JLabel("Sign Up!");
    private final JLabel usernameLabel = new JLabel("Username: ");
    private final JLabel passwordLabel = new JLabel("Password: ");

    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);

    private final JButton signupButton = new JButton("Sign Up");
    private SignupController signupController;

    public SignupView(SignupViewModel signupViewModel) {

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.signupViewModel = signupViewModel;
        this.signupViewModel.addPropertyChangeListener(this);

        final JPanel buttons = new JPanel();
        buttons.add(signupButton);

        signupButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(signupButton)) {
                        final ArrayList<String> result = new ArrayList<String>();
                        result.add(usernameField.getText());
                        result.add(passwordField.getText());
                        signupController.execute(result);

                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(titleLabel);
        this.add(usernameLabel);
        this.add(usernameField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final NoteState state = (NoteState) evt.getNewValue();
        setFields(state);
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setFields(NoteState state) {
        titleLabel.setText(state.getNote());
    }

    public void setNoteController(SignupController controller) {
        this.signupController = controller;
    }
}

