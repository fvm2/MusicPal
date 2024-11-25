package domain;

/**
 * This is temporary. Stops checkstyle from bugging me.
 */
public class Recommendation {
    private final int id;
    private final String content;
    private final String artist;
    private final String type;

    public Recommendation(int id, String content, String artist, String type) {
        this.id = id;
        this.content = content;
        this.artist = artist;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getArtist() {
        return artist;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return content + " by " + artist;
    }
}
