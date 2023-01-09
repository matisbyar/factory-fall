package tetris.logique;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import tetris.TetrisIHM;
import tetris.stockage.Session;

import java.sql.Connection;
import java.util.Objects;

/**
 * Exemple d'utilisation : Preferences.getInstance().getStylePiece();
 */
public class Preferences {

    /**
     * Instance unique de la classe Preferences. À appeler impérativement pour récupérer les préférences du joueur.
     */
    private static Preferences INSTANCE = null;
    /**
     * La préférence de l'image des pièces du plateau. Par défaut, "container".
     */
    private String stylePiece = "conteneur";

    public static Preferences getInstance() {
        if (INSTANCE == null) INSTANCE = new Preferences();
        return INSTANCE;
    }

    public String getStylePiece() {
        return stylePiece;
    }

    public boolean isLocked(String typePerso) {
        switch (typePerso) {
            case "pieces" :
                if(Session.getInstance().isConnected()) {
                    return false;
                } else {
                    if (getStylePiece().equals("brique") || getStylePiece().equals("default")) {
                        return true;
                    }
                }
            default:
                return false;
        }
    }

    public void setStylePiece(String stylePiece) {
        this.stylePiece = stylePiece;
    }

    public Background getBackground() {
        return new Background(new BackgroundImage(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/background/industrial-inmenu.png"))), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1280, 720, false, false, false, false)));
    }

    public Font getPolice(int taille) {
        return Font.loadFont(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("fonts/arcade.ttf")), taille);
    }
}
