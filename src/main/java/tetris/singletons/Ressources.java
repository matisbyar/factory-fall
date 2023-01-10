package tetris.singletons;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import tetris.TetrisIHM;
import tetris.stockage.Session;

import java.util.Objects;

/**
 * À la différence de la classe Preferences, le singleton Ressources charge toutes les ressources du jeu
 */
public class Ressources {

    private static Ressources INSTANCE = null;

    // Pièces affichées dans MenuPersonnalisation
    private final Image pieceDefault = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/default/L.jpg")));
    private final Image pieceConteneur = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/conteneur/L.jpg")));
    private final Image pieceBrique = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/brique/L.jpg")));

    public static Ressources getInstance() {
        if (INSTANCE == null) INSTANCE = new Ressources();
        return INSTANCE;
    }

    public Font getPolice(int taille) {
        return Font.loadFont(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("fonts/arcade.ttf")), taille);
    }

    public Image getPieceDefault() {
        return pieceDefault;
    }

    public Image getPieceConteneur() {
        return pieceConteneur;
    }

    public Image getPieceBrique() {
        return pieceBrique;
    }

    public ImageView getFlecheGauche() {
        return new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/flechePersonnalisation/fleche-gauche.png")), 55, 55, true, true));
    }

    public ImageView getFlecheDroite() {
        return new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/flechePersonnalisation/fleche-droite.png")), 55, 55, true, true));
    }

    public boolean isLocked(String typePerso) {
        Preferences preferences = Preferences.getInstance();
        if (typePerso.equals("pieces")) {
            if (Session.getInstance().isConnected()) {
                return false;
            } else {
                if (preferences.getStylePiece().equals("brique") || preferences.getStylePiece().equals("default")) {
                    return true;
                }
            }
        }
        return false;
    }
}
