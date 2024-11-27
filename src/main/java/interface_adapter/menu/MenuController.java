package interface_adapter.menu;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import interface_adapter.profile.ProfileController;
import interface_adapter.profile.ProfilePresenter;
import interface_adapter.profile.ProfileViewModel;
import use_case.menu.MenuInputBoundary;
import use_case.menu.MenuInputData;
import use_case.profile.ProfileInteractor;
import view.ProfileView;

public class MenuController {
    private final MenuInputBoundary menuInteractor;
    private final MenuViewModel menuViewModel;

    public MenuController(MenuInputBoundary menuInteractor, MenuViewModel menuViewModel) {
        this.menuInteractor = menuInteractor;
        this.menuViewModel = menuViewModel;
    }

    public void openProfile() {
        ProfileViewModel profileViewModel = new ProfileViewModel();
        ProfilePresenter profilePresenter = new ProfilePresenter(profileViewModel);
        ProfileInteractor profileInteractor = new ProfileInteractor(profilePresenter);
        ProfileController profileController = new ProfileController(profileInteractor);

        ProfileView profileView = new ProfileView(profileViewModel);
        profileView.setProfileController(profileController);

        // Display ProfileView
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(profileView);
        if (frame != null) {
            frame.getContentPane().removeAll();
            frame.add(profileView);
            frame.revalidate();
            frame.repaint();
        } else {
            System.err.println("Failed to retrieve the current frame.");
        }
    }

    public void openArtistRecommendations() {
        menuInteractor.openArtistRec(new MenuInputData(menuViewModel.getUsername()));
    }

    public void openSongRecommendations() {
        menuInteractor.openSongRec(new MenuInputData(menuViewModel.getUsername()));
    }

    public void openPlaylistRecommendations() {
        menuInteractor.openPlayListRec(new MenuInputData(menuViewModel.getUsername()));
    }

    public void logout() {
        menuInteractor.logout();
    }
}
