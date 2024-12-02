package interface_adapter.song_recommendation;

import interface_adapter.ViewModel;

public class SongRecViewModel extends ViewModel<SongState> {
    public static final String TITLE_LABEL = "Song Recommendation";
    public static final int GET_RECOMMENDATION_WINDOW_WIDTH = 10;
    public static final int GET_RECOMMENDATION_WINDOW_HEIGHT = 30;
    public static final String NUMBER_LABEL = "Please enter the number of songs you want to provide: ";
    public static final int MAX_SONGS = 20;
    public static final String NEXT_BUTTON_LABEL = "Let me provide you the songs!";

    public SongRecViewModel() {
        super("song recommendation");
        setState(new SongState());
    }

}
