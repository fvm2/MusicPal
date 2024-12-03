package use_case.profile;

import java.util.List;

public record ProfileOutputData(
        String email,
        String name,
        String surname,
        String country,
        List<String> recommendationHistory
) {}