package interface_adapter.note;

import use_case.note.SignupOutputBoundary;

/**
 * The presenter for our Note viewing and editing program.
 */
public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;

    public SignupPresenter(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
    }

    /**
     * Prepares the success view for the Note related Use Cases.
     *
     * @param note the output data
     */
    @Override
    public void prepareSuccessView(String note) {
        signupViewModel.getState().setNote(note);
        signupViewModel.getState().setError(null);
        signupViewModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view for the Note related Use Cases.
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        signupViewModel.getState().setError(errorMessage);
        signupViewModel.firePropertyChanged();
    }
}
