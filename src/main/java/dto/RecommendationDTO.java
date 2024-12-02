package dto;

public record RecommendationDTO(
        int userId,
        String content,
        String type,
        int by
) {}
