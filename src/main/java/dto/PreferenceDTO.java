package dto;

import java.util.List;

public record PreferenceDTO(
        int userId,
        List<String> songs,
        List<String> genres,
        List<String> artists,
        List<String> albums
) {}
