package interface_adapter.menu;

import interface_adapter.ViewModel;

public class MenuViewModel extends ViewModel<String> {
    private String username;

    public MenuViewModel() {
        super("Menu");
        this.setState("Menu");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        this.firePropertyChanged();
    }

    public String getScreen() {
        return this.getState();
    }

    public void setScreen(String screen) {
        this.setState(screen);
        this.firePropertyChanged();
    }
}
