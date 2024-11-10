package app;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import interface_adapter.note.SignupController;
import interface_adapter.note.SignupPresenter;
import interface_adapter.note.SignupViewModel;
import use_case.note.NoteDataAccessInterface;
import use_case.note.SignupInteractor;
import use_case.note.SignupOutputBoundary;
import view.SignupView;

/**
 * Builder for the Note Application.
 */
public class NoteAppBuilder {
    public static final int HEIGHT = 300;
    public static final int WIDTH = 400;
    private NoteDataAccessInterface noteDAO;
    private SignupViewModel signupViewModel = new SignupViewModel();
    private SignupView signupView;
    private SignupInteractor signupInteractor;

    /**
     * Sets the NoteDAO to be used in this application.
     * @param noteDataAccess the DAO to use
     * @return this builder
     */
    public NoteAppBuilder addNoteDAO(NoteDataAccessInterface noteDataAccess) {
        noteDAO = noteDataAccess;
        return this;
    }

    /**
     * Creates the objects for the Note Use Case and connects the NoteView to its
     * controller.
     * <p>This method must be called after addNoteView!</p>
     * @return this builder
     * @throws RuntimeException if this method is called before addNoteView
     */
    public NoteAppBuilder addNoteUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(signupViewModel);
        signupInteractor = new SignupInteractor(
                noteDAO, signupOutputBoundary);

        final SignupController controller = new SignupController(signupInteractor);
        if (signupView == null) {
            throw new RuntimeException("addNoteView must be called before addNoteUseCase");
        }
        signupView.setNoteController(controller);
        return this;
    }

    /**
     * Creates the NoteView and underlying NoteViewModel.
     * @return this builder
     */
    public NoteAppBuilder addNoteView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        return this;
    }

    /**
     * Builds the application.
     * @return the JFrame for the application
     */
    public JFrame build() {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("MusicPal");
        frame.setSize(WIDTH, HEIGHT);

        frame.add(signupView);

        // refresh so that the note will be visible when we start the program
        // noteInteractor.executeRefresh();

        return frame;

    }
}
