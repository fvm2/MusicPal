package main;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.DBDataAccessObject;
import data_access.DataAccessInterface;
import interface_adapter.ViewManagerModel;
import interface_adapter.artist_recommendation.ArtistController;
import interface_adapter.artist_recommendation.ArtistPresenter;
import interface_adapter.artist_recommendation.ArtistRecViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.menu.MenuController;
import interface_adapter.menu.MenuPresenter;
import interface_adapter.menu.MenuViewModel;
import interface_adapter.playlist.PlaylistController;
import interface_adapter.playlist.PlaylistPresenter;
import interface_adapter.playlist.PlaylistRecViewModel;
import interface_adapter.profile.ProfileController;
import interface_adapter.profile.ProfilePresenter;
import interface_adapter.profile.ProfileViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.song_recommendation.SongRecController;
import interface_adapter.song_recommendation.SongRecPresenter;
import interface_adapter.song_recommendation.SongRecViewModel;
import service.PreferenceService;
import service.RecommendationService;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import service.UserService;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.menu.MenuInputBoundary;
import use_case.menu.MenuInteractor;
import use_case.menu.MenuOutputBoundary;
import use_case.profile.ProfileInputBoundary;
import use_case.profile.ProfileInteractor;
import use_case.profile.ProfileOutputBoundary;
import use_case.recommend_artist.RecommendArtistInputBoundary;
import use_case.recommend_artist.RecommendArtistInteractor;
import use_case.recommend_artist.RecommendArtistOutputBoundary;
import use_case.recommend_playlist.RecommendPlaylistInputBoundary;
import use_case.recommend_playlist.RecommendPlaylistInteractor;
import use_case.recommend_playlist.RecommendPlaylistOutputBoundary;
import use_case.recommend_song.RecommendSongInputBoundary;
import use_case.recommend_song.RecommendSongInteractor;
import use_case.recommend_song.RecommendSongOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.*;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final DataAccessInterface dbDataAccessObject = new DBDataAccessObject();
  
    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoginView loginView;
    private ProfileViewModel profileViewModel;
    private ProfileView profileView;
    private MenuView menuView;
    private MenuViewModel menuViewModel;
    private ArtistRecView artistRecView;
    private ArtistRecViewModel artistRecViewModel;
    private PlaylistRecView playlistRecView;
    private PlaylistRecViewModel playlistRecViewModel;
    private SongRecView songRecView;
    private SongRecViewModel songRecViewModel;

    private final UserService userService;
    private final RecommendationService recommendationService;
    private final PreferenceService preferenceService;
  
    /**
    * Constructs an AppBuilder with the necessary services.
    *
    * @param userService            the UserService instance
    * @param preferenceService      the PreferenceService instance
    * @param recommendationService  the RecommendationService instance
    */
    public AppBuilder(UserService userService, PreferenceService preferenceService, RecommendationService recommendationService) {
        this.userService = userService;
        this.recommendationService = recommendationService;
        this.preferenceService = preferenceService;
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the Profile View to the application.
     * @return this builder
     */
    public AppBuilder addProfileView() {
        profileViewModel = new ProfileViewModel();
        profileView = new ProfileView(profileViewModel);
        cardPanel.add(profileView, profileView.getViewName());
        return this;
    }

    public AppBuilder addMenuView() {
        menuViewModel = new MenuViewModel();
        menuView = new MenuView(menuViewModel);
        cardPanel.add(menuView, menuView.getViewName());
        return this;
    }

    public AppBuilder addArtistRecView() {
        artistRecViewModel = new ArtistRecViewModel();
        artistRecView = new ArtistRecView(artistRecViewModel);
        cardPanel.add(artistRecView, artistRecView.getViewName());
        return this;
    }

    public AppBuilder addPlaylistRecView() {
        playlistRecViewModel = new PlaylistRecViewModel();
        playlistRecView = new PlaylistRecView(playlistRecViewModel);
        cardPanel.add(playlistRecView, playlistRecView.getViewName());
        return this;
    }

    public AppBuilder addSongRecView() {
        songRecViewModel = new SongRecViewModel();
        songRecView = new SongRecView(songRecViewModel);
        cardPanel.add(songRecView, songRecView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel, menuViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userService, signupOutputBoundary);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(loggedInViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userService, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addArtistRecUseCase() {
        final RecommendArtistOutputBoundary recommendArtistOutputBoundary = new ArtistPresenter(artistRecViewModel, viewManagerModel, menuViewModel);
        final RecommendArtistInputBoundary recommendArtistInteractor = new RecommendArtistInteractor(dbDataAccessObject, recommendArtistOutputBoundary);

        final ArtistController controller = new ArtistController(recommendArtistInteractor);
        artistRecView.setController(controller);
        return this;
    }

    public AppBuilder addPlaylistUseCase() {
        final RecommendPlaylistOutputBoundary recommendPlaylistOutputBoundary = new PlaylistPresenter(playlistRecViewModel, viewManagerModel, menuViewModel);
        final RecommendPlaylistInputBoundary recommendPlaylistInteractor = new RecommendPlaylistInteractor(dbDataAccessObject, recommendPlaylistOutputBoundary);

        final PlaylistController controller = new PlaylistController(recommendPlaylistInteractor);
        playlistRecView.setController(controller);
        return this;
    }

    public AppBuilder addSongUseCase() {
        final RecommendSongOutputBoundary recommendSongOutputBoundary = new SongRecPresenter(songRecViewModel, viewManagerModel, menuViewModel);
        final RecommendSongInputBoundary recommendSongInteractor = new RecommendSongInteractor(dbDataAccessObject, recommendSongOutputBoundary);

        final SongRecController controller = new SongRecController(recommendSongInteractor);
        songRecView.setController(controller);
        return this;
    }
  
    /**
    * Adds the Profile Use Case to the application.
    *
    * @return this builder
    */
    public AppBuilder addProfileUseCase() {
        ProfileOutputBoundary profileOutputBoundary = new ProfilePresenter(profileViewModel, viewManagerModel);
        ProfileInputBoundary profileInteractor =
                new ProfileInteractor(profileOutputBoundary, userService, preferenceService);

        ProfileController profileController = new ProfileController(profileInteractor, profileViewModel);
        profileView.setProfileController(profileController);
        return this;
    }

    public AppBuilder addMenuUseCase() {
        MenuOutputBoundary menuPresenter = new MenuPresenter(menuViewModel, viewManagerModel);
        MenuInputBoundary menuInteractor = new MenuInteractor(menuPresenter);

        MenuController menuController = new MenuController(menuInteractor);
        menuView.setController(menuController);
        return this;
    }


    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     *
     * @return the application JFrame
     */
    public JFrame buildApp() {
        final JFrame application = new JFrame("Music Recommendation App");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        // Add a listener to switch views based on the ViewManagerModel's state
        viewManagerModel.addPropertyChangeListener(evt -> {
            String viewName = viewManagerModel.getState();
            cardLayout.show(cardPanel, viewName);
        });

        // Set the initial view to Signup
        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
