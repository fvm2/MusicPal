package entity;

/**
 * Represents a music recommendation made by either the AI or a friend.
 */
public class Recommendation {
    private int recommendationId;
    private int userId;
    private String content;
    // "Song", "Album", or "Artist"
    private String type;
    // 0 for AI, user.id for friend recommendations
    private int by;
    // null = not rated, true = liked, false = disliked
    private Boolean liked;

    public Recommendation() { }

    public Recommendation(int userId, String content, String type, int by) {
        this.userId = userId;
        this.content = content;
        this.type = type;
        this.by = by;
        this.liked = null;
    }

    // Getters and setters
    public int getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(int recommendationId) {
        this.recommendationId = recommendationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (!"Song".equals(type) && !"Album".equals(type) && !"Artist".equals(type)) {
            throw new IllegalArgumentException("Type must be 'Song', 'Album', or 'Artist'");
        }
        this.type = type;
    }

    public int getBy() {
        return by;
    }

    public void setBy(int by) {
        this.by = by;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    /**
     * Rate the recommendation.
     * @param liked true if liked, false if disliked.
     */
    public void rate(boolean liked) {
        this.liked = liked;
    }
}
