package interface_adapter.recommendation;

import interface_adapter.ViewManagerModel;
import interface_adapter.profile.ProfileViewModel;
import use_case.recommend.RecommendOutputBoundary;
import use_case.recommend.RecommendOutputData;

public class RecommendationPresenter implements RecommendOutputBoundary {
    private final RecommendationViewModel recommendationViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ProfileViewModel profileViewModel;

    public RecommendationPresenter(RecommendationViewModel viewModel, ViewManagerModel viewManagerModel, ProfileViewModel profileViewModel) {
        this.recommendationViewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.profileViewModel = profileViewModel;
    }

    @Override
    public void switchToProfileView() {
        viewManagerModel.setState(profileViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void popRecommendation(RecommendOutputData outputData) {
        final RecommendationState recommendationState = recommendationViewModel.getState();
        recommendationState.setRecommendationName(outputData.getRecommendation());
        this.recommendationViewModel.setState(recommendationState);
        recommendationViewModel.firePropertyChanged();

        viewManagerModel.setState(recommendationViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

}
