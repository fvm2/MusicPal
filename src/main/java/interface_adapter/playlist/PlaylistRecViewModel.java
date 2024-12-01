package interface_adapter.playlist;

import interface_adapter.ViewModel;

public class PlaylistRecViewModel extends ViewModel<PlaylistState> {
    public static final String TITLE_LABEL = "Playlist Recommendation";
    public static final int GET_RECOMMENDATION_WINDOW_WIDTH = 10;
    public static final int GET_RECOMMENDATION_WINDOW_HEIGHT = 30;
    public static final String NUMBER_LABEL = "Please enter the number of songs you want to provide: ";
    public static final int MAX_SONGS = 20;
    public static final String NEXT_BUTTON_LABEL = "Provide Songs";

    public PlaylistRecViewModel() {
        super("playlist recommendation");
        setState(new PlaylistState());
    }

}
