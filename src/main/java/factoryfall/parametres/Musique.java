package factoryfall.parametres;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import factoryfall.FactoryFall;

import java.util.Objects;

public class Musique {
    private static Media mainMenuMusic;
    private static Media gameMusic;
    private static MediaPlayer musicPlayer;

    public static void playMusicMainMenu() {
        if (!Preferences.getInstance().getMusiqueMute()) {
            mainMenuMusic = new Media(Objects.requireNonNull(String.valueOf(FactoryFall.class.getResource("music/MainMenuMusic.mp3"))));
            musicPlayer = new MediaPlayer(mainMenuMusic);
            musicPlayer.setAutoPlay(true);
            musicPlayer.volumeProperty().setValue(0.1);
            musicPlayer.setOnEndOfMedia(new Runnable() {
                public void run() {
                    musicPlayer.seek(Duration.ZERO);
                }
            });
        }
    }

    public static void stopMusicMainMenu() {
        musicPlayer.stop();
    }

    public static void btnMute() {
        musicPlayer.setMute(!musicPlayer.isMute());
        Preferences.getInstance().setMusiqueMute(musicPlayer.isMute());
    }

    public static void playMusicGame() {
        if (!Preferences.getInstance().getMusiqueMute()) {
            gameMusic = new Media(Objects.requireNonNull(String.valueOf(FactoryFall.class.getResource("music/GameMusic.mp3"))));
            musicPlayer = new MediaPlayer(gameMusic);
            musicPlayer.setAutoPlay(true);
            musicPlayer.volumeProperty().setValue(0.1);
            musicPlayer.setOnEndOfMedia(new Runnable() {
                public void run() {
                    musicPlayer.seek(Duration.ZERO);
                }
            });
        }
    }

    public static void stopMusicGame() {
        if (musicPlayer != null) musicPlayer.stop();
    }
}
