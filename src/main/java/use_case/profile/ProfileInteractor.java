package use_case.profile;

import entity.User;
import entity.Recommendation;
import infrastructure.database.RecommendationRepository;
import service.Result;
import service.UserService;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileInteractor implements ProfileInputBoundary {
    private final UserService userService;
    private final ProfileOutputBoundary profilePresenter;

    public ProfileInteractor(UserService userService, ProfileOutputBoundary profilePresenter) {
        this.userService = userService;
        this.profilePresenter = profilePresenter;
    }

    @Override
    public void loadProfile(ProfileInputData profileInputData) {
        Result<User> result = userService.getUserByEmail(profileInputData.email());

        if (result.isSuccess()) {
            User user = result.getData();
            ProfileOutputData outputData = new ProfileOutputData(
                    user.getEmail(),
                    user.getName(),
                    user.getSurname(),
                    user.getCountry(),
                    getRecommendationHistory(user.getId())
            );
            profilePresenter.presentProfile(outputData);
        }
        else {
            profilePresenter.presentError(result.getError());
        }
    }

    private List<String> getRecommendationHistory(int userId) {
        // Get recommendations from repository
        RecommendationRepository recRepo = new RecommendationRepository();
        List<Recommendation> recommendations = recRepo.findByUserId(userId);

        return recommendations.stream()
                .map(this::formatRecommendation)
                .collect(Collectors.toList());
    }

    private String formatRecommendation(Recommendation rec) {
        return String.format("%s (%s) - %s",
                rec.getContent(),
                rec.getType(),
                rec.getLiked() == null ? "Not rated" :
                        rec.getLiked() ? "Liked" : "Disliked"
        );
    }

    @Override
    public void logout() {
        profilePresenter.presentLogout();
    }

    @Override
    public void backToMenu() {
        profilePresenter.presentBackToMenu();
    }
}