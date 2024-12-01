package interface_adapter.menu;

import interface_adapter.ViewModel;

public class MenuViewModel extends ViewModel<MenuState> {
    public static final String TITLE_LABEL = "";
    public static final String PROFILE_LABEL = "View Profile";
    public static final String SONG_REC_LABEL = "Get Song Recommendations";
    public static final String PLAYLIST_REC_LABEL = "Get Playlist Recommendations";
    public static final String ARTIST_REC_LABEL = "Get Artist Recommendations";

    public MenuViewModel() {
        super("menu");
        setState(new MenuState());
    }

}
