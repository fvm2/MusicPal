package interface_adapter.note;

import use_case.note.SignupInputBoundary;

import java.util.ArrayList;

/**
 * Controller for our Signup related Use Cases.
 */
public class SignupController {

    private final SignupInputBoundary noteInteractor;

    public SignupController(SignupInputBoundary noteInteractor) {
        this.noteInteractor = noteInteractor;
    }

    /**
     * Executes the Sign Up related Use Cases.
     * @param signupinfo the username and password to be recorded
     */
    public void execute(ArrayList<String> signupinfo) {
        if (signupinfo != null) {
            noteInteractor.executeSave(signupinfo);
        }
    }
}
