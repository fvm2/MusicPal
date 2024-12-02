package use_case.profile;

import entity.Preference;
import entity.User;
import service.PreferenceService;
import service.Result;
import service.UserService;

import java.util.ArrayList;
import java.util.List;

public class ProfileInteractor implements ProfileInputBoundary {
    final ProfileOutputBoundary profilePresenter;
    private final UserService userService;
    private final PreferenceService preferenceService;

    public ProfileInteractor(ProfileOutputBoundary profileOutputBoundary,
                             UserService userService,
                             PreferenceService preferenceService) {
        this.profilePresenter = profileOutputBoundary;
        this.userService = userService;
        this.preferenceService = preferenceService;
    }

    @Override
    public void showFavorites(ProfileInputData profileInputData) {
        String email = profileInputData.username(); // Assuming username is email

        // Retrieve user by email
        Result<User> userResult = userService.getUserByEmail(email);

        if (userResult.isSuccess()) {
            User user = userResult.getData();

            // Retrieve preferences
            Result<Preference> preferenceResult = preferenceService.getPreferences(user.getId());

            if (preferenceResult.isSuccess()) {
                Preference preference = preferenceResult.getData();

                // Combine all preferences into a single list of favorites
                List<String> favorites = new ArrayList<>();
                favorites.addAll(preference.getSongs());
                favorites.addAll(preference.getArtists());
                favorites.addAll(preference.getAlbums());
                favorites.addAll(preference.getGenres());

                ProfileOutputData outputData = new ProfileOutputData(user.getEmail(), favorites, null);
                profilePresenter.showFavorites(outputData);
            } else {
                profilePresenter.prepareFailView("Failed to retrieve preferences: " + preferenceResult.getError());
            }
        } else {
            profilePresenter.prepareFailView("User not found: " + userResult.getError());
        }
    }

    @Override
    public void showFriends(ProfileInputData profileInputData) {
        String email = profileInputData.username(); // Assuming username is email

        // Retrieve user by email
        Result<User> userResult = userService.getUserByEmail(email);

        if (userResult.isSuccess()) {
            User user = userResult.getData();

            // Get friends' IDs
            List<Integer> friendIds = user.getFriends();

            // Retrieve friend users
            List<String> friendEmails = userService.getUserEmailsByIds(friendIds);

            ProfileOutputData outputData = new ProfileOutputData(user.getEmail(), null, friendEmails);
            profilePresenter.showFriends(outputData);
        } else {
            profilePresenter.prepareFailView("User not found: " + userResult.getError());
        }
    }

    @Override
    public void logout() {
        profilePresenter.logout();
    }
}