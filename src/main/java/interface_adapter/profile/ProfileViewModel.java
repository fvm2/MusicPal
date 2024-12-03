package interface_adapter.profile;

import interface_adapter.ViewModel;

public class ProfileViewModel extends ViewModel<ProfileState> {
    public static final String TITLE_LABEL = "User Profile";
    public static final String EMAIL_LABEL = "Email: ";
    public static final String NAME_LABEL = "Name: ";
    public static final String SURNAME_LABEL = "Surname: ";
    public static final String COUNTRY_LABEL = "Country: ";
    public static final String HISTORY_LABEL = "Recommendation History";
    public static final String LOGOUT_BUTTON_LABEL = "Logout";
    public static final String BACK_MENU_BUTTON_LABEL = "Back to Menu";

    public ProfileViewModel() {
        super("profile");
        setState(new ProfileState());
    }
}