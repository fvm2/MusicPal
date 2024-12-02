package use_case.recommend_song;

import java.util.List;

public class RecommendSongInputData {
    private List<String> songs;
    public RecommendSongInputData(List<String> songs) {
        this.songs = songs;
    }

    public List<String> getSongs() {
        return songs;
    }
}
