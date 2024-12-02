package use_case.profile;

import java.util.List;

public record ProfileOutputData(String username, List<String> favorites, List<String> friends) {
}