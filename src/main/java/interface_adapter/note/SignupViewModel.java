package interface_adapter.note;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the NoteView.
 */
public class SignupViewModel extends ViewModel<NoteState> {
    public SignupViewModel() {
        super("note");
        setState(new NoteState());
    }
}
