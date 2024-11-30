package app;

import data_access.DBDataAccessObject;
import data_access.DataAccessObject;
import entity.UserFactory;
import interface_adapter.artist_recommendation.ArtistController;
import interface_adapter.artist_recommendation.ArtistPresenter;
import interface_adapter.artist_recommendation.ArtistRecViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.menu.MenuViewModel;
import interface_adapter.playlist.PlaylistPresenter;
import interface_adapter.profile.ProfileViewModel;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.song_recommendation.SongRecViewModel;
import use_case.ViewManagerModel;
import use_case.recommend_artist.RecommendArtistInputBoundary;
import use_case.recommend_artist.RecommendArtistInteractor;
import use_case.recommend_artist.RecommendArtistOutputBoundary;
import use_case.recommend_playlist.PlaylistRecViewModel;
import use_case.recommend_playlist.RecommendPlaylistInteractor;
import use_case.recommend_playlist.RecommendPlaylistOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupOutputBoundary;
import view.*;
import view.LoginView;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel gridBagPanel = new JPanel();
    private final GridBagLayout gridBagLayout = new GridBagLayout();
    private final UserFactory userFactory = new UserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(gridBagPanel, gridBagLayout, viewManagerModel);

    private final DataAccessObject dbDataAccessObject = new DBDataAccessObject();

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private MenuView menuView;
    private MenuViewModel menuViewModel;
    private ArtistRecView artistRecView;
    private ArtistRecViewModel artistRecViewModel;
    private PlaylistRecView playlistRecView;
    private PlaylistRecViewModel playlistRecViewModel;
    private SongRecView songRecView;
    private SongRecViewModel songRecViewModel;
    private ProfileView profileView;
    private ProfileViewModel profileViewModel;

    public AppBuilder() {
        gridBagPanel.setLayout(gridBagLayout);
    }

    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        gridBagPanel.add(signupView, signupView.getViewName);
        return this;
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        gridBagPanel.add(loginView, loginView.getViewName);
        return this;
    }

    public AppBuilder addMenuView() {
        menuViewModel = new MenuViewModel();
        menuView = new MenuView(menuViewModel);
        gridBagPanel.add(menuView, menuView.getViewName);
        return this;
    }

    public AppBuilder addArtistRecView() {
        artistRecViewModel = new ArtistRecViewModel();
        artistRecView = new ArtistRecView(artistRecViewModel);
        gridBagPanel.add(artistRecView, artistRecView.getViewName);
        return this;
    }

    public AppBuilder addPlaylistRecView() {
        playlistRecViewModel = new PlaylistRecViewModel();
        playlistRecView = new PlaylistRecView(playlistRecViewModel);
        gridBagPanel.add(playlistRecView, playlistRecView.getViewName);
        return this;
    }

    public AppBuilder addSongRecView() {
        songRecViewModel = new SongRecViewModel();
        songRecView = new SongRecView(songRecViewModel);
        gridBagPanel.add(songRecView, songRecView.getViewName);
        return this;
    }

    public AppBuilder addProfileView() {
        profileViewModel = new ProfileViewModel();
        profileView = new ProfileView(profileViewModel);
        gridBagPanel.add(profileView, profileView.getViewName);
        return this;
    }

    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(dataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, loginViewModel, menuViewModel);
        final LoginInputBoundary userLoginInteractor = new LoginInteractor(dataAccessObject, loginOutputBoundary);

        final LoginController controller = new LoginController(userLoginInteractor);
        loginView.setLoginController(controller);
        return this;
    }

    public AppBuilder addArtistRecUseCase() {
        final RecommendArtistOutputBoundary recommendArtistOutputBoundary = new ArtistPresenter(viewManagerModel, artistRecViewModel, recommendationsViewModel);
        final RecommendArtistInputBoundary recommendArtistInteractor = new RecommendArtistInteractor(dataAccessObject, recommendArtistOutputBoundary);

        final ArtistController controller = new ArtistController(recommendArtistInteractor);
        artistRecView.setArtistController(controller);
        return this;
    }

    public AppBuilder addPlaylistRecUseCase() {
        final RecommendPlaylistOutputBoundary recommendPlaylistOutputBoundary = new PlaylistPresenter(viewManagerModel, playlistRecViewModel, recommendationsViewModel);
        final RecommendPlaylistInputBoundary recommendPlaylistInteractor = new RecommendPlaylistInteractor(dataAccessObject, recommendPlaylistOutputBoundary);

        final PlaylistController controller = new PlaylistController(recommendPlaylistInteractor);
        playlistRecView.setPlaylistController(controller);
        return this;
    }

    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(dataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this; // SONG REC
    }

    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(dataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this; // ADD FRIEND
    }

    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(dataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this; // SEND SUGGESTION
    }

    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(dataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this; // LIKE SONG
    }

    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(dataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;  // DISLIKE SONG
    }

}
