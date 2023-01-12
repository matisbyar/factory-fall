package tetris.vues;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import tetris.TetrisIHM;
import tetris.singletons.Preferences;

import java.util.Objects;

public class Musique {
    private static Media mainMenuMusic;
    private static MediaPlayer mainMenuMusicPlayer;

    public static void playMusicMainMenu() {
        mainMenuMusic = new Media(Objects.requireNonNull(TetrisIHM.class.getResource("music/Factory_Fall_Main_Theme_20221229_205142.mp3")).toString());
        mainMenuMusicPlayer = new MediaPlayer(mainMenuMusic);
        mainMenuMusicPlayer.setAutoPlay(true);
        mainMenuMusicPlayer.volumeProperty().setValue(0.1);
        mainMenuMusicPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                mainMenuMusicPlayer.seek(Duration.ZERO);
            }
        });
    }

    public static void stopMusicMainMenu() {
        mainMenuMusicPlayer.stop();
    }

    public static void btnMute() {
        mainMenuMusicPlayer.setMute(!mainMenuMusicPlayer.isMute());
        Preferences.getInstance().setMusiqueMute(mainMenuMusicPlayer.isMute());
        System.out.println(mainMenuMusicPlayer.isMute());
    }
}
