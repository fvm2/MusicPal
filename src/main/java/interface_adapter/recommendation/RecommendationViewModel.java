package interface_adapter.recommendation;

import interface_adapter.ViewModel;

public class RecommendationViewModel extends ViewModel<RecommendationState> {

    public RecommendationViewModel() {
        super("rec view");
        setState(new RecommendationState());
    }
}
