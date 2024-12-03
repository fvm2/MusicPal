package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

/**
 * The view for our sign-up page.
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewMame = "sign up";

    private final SignupViewModel signupViewModel;
    private final JTextField nameInputField = new JTextField(20);
    private final JTextField surnameInputField = new JTextField(20);
    private final JTextField emailInputField = new JTextField(20);
    private final JTextField countryInputField = new JTextField(20);
    private final JPasswordField passwordInputField = new JPasswordField(20);
    private SignupController signupController;

    private final JButton signUp;
    private final JButton toLogin;

    public SignupView(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // setting up name, surname area, email, country, password panels
        final JPanel namePanel = new JPanel();
        final JLabel nameLabel = new JLabel(SignupViewModel.NAME_LABEL);
        namePanel.add(nameLabel);
        namePanel.add(nameInputField);

        final JPanel surnamePanel = new JPanel();
        final JLabel surnameLabel = new JLabel(SignupViewModel.SURNAME_LABEL);
        surnamePanel.add(surnameLabel);
        surnamePanel.add(surnameInputField);

        final JPanel emailPanel = new JPanel();
        final JLabel emailLabel = new JLabel(SignupViewModel.EMAIL_LABEL);
        emailPanel.add(emailLabel);
        emailPanel.add(emailInputField);

        final JPanel countryPanel = new JPanel();
        final JLabel countryLabel = new JLabel(SignupViewModel.COUNTRY_LABEL);
        countryPanel.add(countryLabel);
        countryPanel.add(countryInputField);

        final JPanel passwordPanel = new JPanel();
        final JLabel passwordLabel = new JLabel(SignupViewModel.PASSWORD_LABEL);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordInputField);

        // adding buttons
        final JPanel buttons = new JPanel();
        toLogin = new JButton(SignupViewModel.TO_LOGIN_BUTTON_LABEL);
        buttons.add(toLogin);
        signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        buttons.add(signUp);

        signUp.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(signUp)) {
                            final SignupState currentState = signupViewModel.getState();

                            signupController.execute(
                                    currentState.getName(),
                                    currentState.getSurname(),
                                    currentState.getEmail(),
                                    currentState.getCountry(),
                                    currentState.getPassword()
                            );

                            signupController.switchToMenuView();
                        }
                    }
                }
        );

        toLogin.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        signupController.switchToLoginView();
                    }
                }
        );

        // add name, surname, email, country, password listeners.
        addNameListener();
        addSurnameListener();
        addEmailListener();
        addCountryListener();
        addPasswordListener();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(namePanel);
        this.add(surnamePanel);
        this.add(emailPanel);
        this.add(countryPanel);
        this.add(passwordPanel);
        this.add(buttons);
    }

    private void addNameListener() {
        nameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setName(nameInputField.getText());
                signupViewModel.setState(currentState);
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

    private void addSurnameListener() {
        surnameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setSurname(surnameInputField.getText());
                signupViewModel.setState(currentState);
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

    private void addEmailListener() {
        emailInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setEmail(emailInputField.getText());
                signupViewModel.setState(currentState);
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

    private void addCountryListener() {
        countryInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setCountry(countryInputField.getText());
                signupViewModel.setState(currentState);
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

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setPassword(passwordInputField.getText());
                signupViewModel.setState(currentState);
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
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Some message.");
    }

    public void propertyChange(PropertyChangeEvent evt) {}

    public String getViewName() {
        return viewMame;
    }

    public void setSignupController(SignupController signupController) {
        this.signupController = signupController;
    }

}
