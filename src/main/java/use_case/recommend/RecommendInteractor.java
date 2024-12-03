package use_case.recommend;
import entity.Recommendation;
import service.RecommendationService;
import service.PreferenceService;
import dto.PreferenceDTO;
import entity.User;
import service.Result;
import entity.Preference;
import use_case.recommend_playlist.RecommendPlaylistOutputBoundary;

import java.util.Collections;
import java.util.List;


public class RecommendInteractor implements RecommendInputBoundary  {
    private final RecommendOutputBoundary recommendPresenter;
    private final User currentUser;
    private final PreferenceService preferenceService;
    private final RecommendationService recommendationService;

    public RecommendInteractor(User currentUser, PreferenceService preferenceService,
                               RecommendOutputBoundary recommendPresenter, RecommendationService recommendationService) {
        this.currentUser = currentUser;
        this.preferenceService = preferenceService;
        this.recommendPresenter = recommendPresenter;
        this.recommendationService = recommendationService;
    }

    @Override
    public void execute(RecommendInputData input) {
        final List<String> data = input.getPreferences();
        final String dataType = input.getRequestedRecType();
        if (dataType.equals("Song")) {
            final PreferenceDTO newpref = new PreferenceDTO(currentUser.getId(), data,
                    Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
            preferenceService.updatePreferences(newpref);
        }
        else if (dataType.equals("Album")) {
            final PreferenceDTO newpref = new PreferenceDTO(currentUser.getId(), Collections.emptyList(),
                    Collections.emptyList(), Collections.emptyList(), data);
            preferenceService.updatePreferences(newpref);
        } else if (dataType.equals("Artist")) {
            final PreferenceDTO newpref = new PreferenceDTO(currentUser.getId(), Collections.emptyList(), data,
                    Collections.emptyList(), Collections.emptyList());
            preferenceService.updatePreferences(newpref);
        } else {
            final PreferenceDTO newpref = new PreferenceDTO(currentUser.getId(), Collections.emptyList(), data,
                    Collections.emptyList(), Collections.emptyList());
            preferenceService.updatePreferences(newpref);
        }
        final Result<Recommendation> newRec = recommendationService.getRecommendation(currentUser.getId(), dataType);
        if (newRec.isSuccess()) {
            final RecommendOutputData recommendOutputData = new RecommendOutputData(newRec.getData().getContent());
            recommendPresenter.popRecommendation(recommendOutputData);
        }
    }

    @Override
    public void switchToProfileView() {
        recommendPresenter.switchToProfileView();
    }
}
