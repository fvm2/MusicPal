package use_case.menu;

public class MenuInputData {
    private final String username;

    public MenuInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}