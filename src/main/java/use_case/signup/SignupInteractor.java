package use_case.signup;

import dto.UserDTO;
import entity.User;
import service.Result;
import service.UserService;

/**
 * The Signup Interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final UserService userService;
    private final SignupOutputBoundary userPresenter;

    public SignupInteractor(UserService userService, SignupOutputBoundary userPresenter) {
        this.userService = userService;
        this.userPresenter = userPresenter;
    }

    @Override
    public void execute(SignupInputData signupInputData) {

        final UserDTO newUser = new UserDTO(signupInputData.getName(), signupInputData.getSurname(),
                signupInputData.getEmail(), signupInputData.getCountry(), signupInputData.getPassword());

        final Result<User> result = userService.register(newUser);

        if (result.isSuccess()) {
            final User user = result.getData();
            final SignupOutputData signupOutputData = new SignupOutputData(user.getName(),
                    user.getSurname(),
                    user.getEmail(),
                    user.getCountry());
            userPresenter.prepareSuccessView(signupOutputData);
        }
        else {
            userPresenter.prepareFailView(result.getError());
        }

    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }

    @Override
    public void switchMenuView() {
        userPresenter.switchToMenuView();
    }
}
