package use_case.note;

import entity.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class SignupInteractorTest {

    @Test
    public void testExecuteRefreshSuccess() {

        NoteDataAccessInterface noteDAO = new NoteDataAccessInterface() {


            @Override
            public String saveNote(User user, String note) {
                return "";
            }


            @Override
            public String loadNote(User user) {
                return "test";
            }
        };

        SignupOutputBoundary noteOB = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(String message) {
                assertEquals("test", message);
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail(errorMessage);
            }
        };

        SignupInteractor signupInteractor = new SignupInteractor(noteDAO, noteOB);

        signupInteractor.executeRefresh();


    }
}