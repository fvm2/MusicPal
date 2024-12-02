package use_case.recommend_artist;

public class RecommendArtistInputData {
    private String artistName;

    public RecommendArtistInputData(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistName() {
        return artistName;
    }
}
