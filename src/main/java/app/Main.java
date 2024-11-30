package app;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                .addLoginView()
                .addSignupView()
                .addMenuView()
                .addArtistRecView()
                .addPlaylistRecView()
                .addSongRecView()
                .addProfileView()
                .addSignupUseCase()
                .addLoginUseCase()
                .addArtistRecUseCase()
                .addPlaylistRecUseCase()
                .addSongRecUseCase()
                .addFriendUseCase()
                .addSuggestSongUseCase()
                .addLikeSongUseCase()
                .addDislikeSongUseCase()
                .build();
        application.pack();
        application.setVisible(true);
    }
}
