package use_case.note;

import java.util.ArrayList;

/**
 * The Input Boundary for our note-related use cases. Since they are closely related,
 * we have included them both in the same interface for simplicity.
 */
public interface SignupInputBoundary {

    /**
     * Executes the refresh note use case.
     */
    // void executeRefresh();

    /**
     * Executes the save note use case.
     * @param signupinfo the username and password.
     */
    void executeSave(ArrayList<String> signupinfo);
}
