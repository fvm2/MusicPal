package view;

import interface_adapter.menu.MenuController;
import interface_adapter.menu.MenuViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MenuView extends JPanel implements PropertyChangeListener {
    private MenuController menuController;

    public MenuView(MenuViewModel menuViewModel) {
        menuViewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Menu");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton profileButton = new JButton("Open Profile");
        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileButton.addActionListener(e -> {
            if (menuController != null) {
                String username = menuViewModel.getUsername();
                if (username != null && !username.isEmpty()) {
                    menuController.openProfile(username);
                } else {
                    System.err.println("Username is not set. Cannot open Profile.");
                }
            } else {
                System.err.println("MenuController is not set. Cannot open Profile.");
            }
        });

        add(title);
        add(profileButton);
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("screen".equals(evt.getPropertyName())) {
            System.out.println("Navigated to: " + evt.getNewValue());
        }
    }
}
