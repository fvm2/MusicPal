package use_case.menu;

public class MenuOutputData {

    private final String username;
    public MenuOutputData(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }
}