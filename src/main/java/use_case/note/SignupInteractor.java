package use_case.note;

import entity.User;

import java.util.ArrayList;

/**
 * The "Use Case Interactor" for registering the new user.
 */
public class SignupInteractor implements SignupInputBoundary {

    private final NoteDataAccessInterface noteDataAccessInterface;
    private final SignupOutputBoundary signupOutputBoundary;
    // Note: this program has it hardcoded which user object it is getting data for;
    // you could change this if you wanted to generalize the code. For example,
    // you might allow a user of the program to create a new note, which you
    // could store as a "user" through the API OR you might maintain all notes
    // in a JSON object stored in one common "user" stored through the API.

    public SignupInteractor(NoteDataAccessInterface noteDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary) {
        this.noteDataAccessInterface = noteDataAccessInterface;
        this.signupOutputBoundary = signupOutputBoundary;
    }

    /**
     * Executes the register new user use case.
     *
     * @param signupinfo the input data
     */
    @Override
    public void executeSave(ArrayList<String> signupinfo) {

        final User user = new User(signupinfo.get(0), signupinfo.get(1));
        // what am I supposed to do after creating the user?

        // final String updatedNote = noteDataAccessInterface.saveNote(user, signupinfo);
        signupOutputBoundary.prepareSuccessView("Welcome " + user.getName());

    }
}
