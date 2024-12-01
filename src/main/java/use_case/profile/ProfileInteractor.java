package use_case.profile;

import entity.Preference;
import entity.User;
import service.PreferenceService;
import service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Interactor for the Profile feature.
 */
public class ProfileInteractor implements ProfileInputBoundary {
    private final ProfileOutputBoundary profilePresenter;
    private final UserService userService;
    private final PreferenceService preferenceService;

    public ProfileInteractor(ProfileOutputBoundary profilePresenter,
                             UserService userService,
                             PreferenceService preferenceService) {
        this.profilePresenter = profilePresenter;
        this.userService = userService;
        this.preferenceService = preferenceService;
    }

    @Override
    public void showProfile(ProfileInputData profileInputData) {
        String username = profileInputData.getUsername();

        // Fetch user
        User user = userService.findByEmail(username).getData(); // Assuming username is email
        if (user == null) {
            // Handle user not found
            return;
        }

        // Fetch user preferences
        Preference preference = preferenceService.getPreferences(user.getId()).getData();

        // Create OutputData
        ProfileOutputData outputData = new ProfileOutputData();
        outputData.setUsername(user.getName());
        outputData.setFavorites(preference.getSongs()); // Assuming favorites are songs

        // Convert friend IDs to usernames or names
        List<String> friendNames = new ArrayList<>();
        for (Integer friendId : user.getFriends()) {
            User friend = userService.findById(friendId).getData();
            if (friend != null) {
                friendNames.add(friend.getName());
            }
        }
        outputData.setFriends(friendNames);

        // Pass data to presenter
        profilePresenter.presentProfile(outputData);
    }

    @Override
    public void logout() {
        profilePresenter.logout();
    }
}