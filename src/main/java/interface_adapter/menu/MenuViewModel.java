package interface_adapter.menu;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MenuViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String screen;
    private String username;

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        String oldScreen = this.screen;
        this.screen = screen;
        support.firePropertyChange("screen", oldScreen, screen);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        String oldUsername = this.username;
        this.username = username;
        support.firePropertyChange("username", oldUsername, username);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
