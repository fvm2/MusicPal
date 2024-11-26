package entity;

public class Recommendation {
    private int id;
    private String content;
    private String artist;
    private String type;

    public Recommendation(int id, String content, String artist, String type) {
        this.id = id;
        this.content = content;
        this.artist = artist;
        this.type = type;
    }

    @Override
    public String toString() {
        return content + " by " + artist;
    }
}
