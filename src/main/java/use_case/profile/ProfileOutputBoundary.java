package use_case.profile;

/**
 * Output boundary for the Profile feature.
 */
public interface ProfileOutputBoundary {
    void presentProfile(ProfileOutputData outputData);
    void logout();
}