package app;

import data_access.DBDataAccessObject;
import data_access.DataAccessInterface;
import interface_adapter.ViewManagerModel;
import interface_adapter.artist_recommendation.ArtistController;
import interface_adapter.artist_recommendation.ArtistPresenter;
import interface_adapter.artist_recommendation.ArtistRecViewModel;
import interface_adapter.menu.MenuController;
import interface_adapter.menu.MenuPresenter;
import interface_adapter.menu.MenuViewModel;
import use_case.menu.MenuInputBoundary;
import use_case.menu.MenuInteractor;
import use_case.menu.MenuOutputBoundary;
import use_case.recommend_artist.RecommendArtistInputBoundary;
import use_case.recommend_artist.RecommendArtistInteractor;
import use_case.recommend_artist.RecommendArtistOutputBoundary;
import view.*;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private final DataAccessInterface dbDataAccessObject = new DBDataAccessObject();

    private ArtistRecView artistRecView;
    private ArtistRecViewModel artistRecViewModel;
    private MenuView menuView;
    private MenuViewModel menuViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addArtistRecView() {
        artistRecViewModel = new ArtistRecViewModel();
        artistRecView = new ArtistRecView(artistRecViewModel);
        cardPanel.add(artistRecView, artistRecView.getViewName());
        return this;
    }

    public AppBuilder addMenuView() {
        menuViewModel = new MenuViewModel();
        menuView = new MenuView(menuViewModel);
        cardPanel.add(menuView, menuView.getViewName());
        return this;
    }

    public AppBuilder addArtistRecUseCase() {
        final RecommendArtistOutputBoundary recommendArtistOutputBoundary = new ArtistPresenter(artistRecViewModel, viewManagerModel, menuViewModel);
        final RecommendArtistInputBoundary recommendArtistInteractor = new RecommendArtistInteractor(dbDataAccessObject, recommendArtistOutputBoundary);

        final ArtistController controller = new ArtistController(recommendArtistInteractor);
        artistRecView.setController(controller);
        return this;
    }

    public AppBuilder addMenuUseCase() {
        final MenuOutputBoundary menuOutputBoundary = new MenuPresenter(menuViewModel, viewManagerModel);
        final MenuInputBoundary menuInteractor = new MenuInteractor(menuOutputBoundary);

        final MenuController controller = new MenuController(menuInteractor);
        menuView.setController(controller);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("MusicPal");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(menuView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }

}
