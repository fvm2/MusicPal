package use_case.recommend_playlist;

import java.util.List;

public class RecommendPlaylistInputData {
    private List<String> songs;

    public RecommendPlaylistInputData(List<String> songs) {
        this.songs = songs;
    }

    public List<String> getSongs() {
        return songs;
    }
}