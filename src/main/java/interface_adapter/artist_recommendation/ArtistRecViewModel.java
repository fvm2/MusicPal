package interface_adapter.artist_recommendation;

import interface_adapter.ViewModel;

public class ArtistRecViewModel extends ViewModel<ArtistState> {
    public static final String TITLE_LABEL = "Artist Recommendation";
    public static final String ARTIST_LABEL = "Please enter an artist:";
    public static final String GET_RECOMMENDATION_BUTTON_LABEL = "Get Recommendation";
    public static final String MENU_LABEL = "Go Back to the Menu";
    public static final int GET_RECOMMENDATION_WINDOW_WIDTH = 10;
    public static final int GET_RECOMMENDATION_WINDOW_HEIGHT = 30;

    public ArtistRecViewModel() {
        super("artist recommendation");
        setState(new ArtistState());
    }

}
