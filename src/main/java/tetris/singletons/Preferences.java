package tetris.singletons;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import tetris.TetrisIHM;

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
    private int stylePieceActuel = 0;
    private final ImageView imagePiecePreference = new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/" + stylePiece + "/L.jpg"))));

    /**
     * La préférence du fond.
     */
    private String background = "industrial";
    private int backgroundActuel = 0;
    private Image imageBackground = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/background/" + background + ".png")));

    /**
     * Le fond (à l'intérieur des menus)
     */
    private final Background inMenu = new Background(new BackgroundImage(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/background/industrial-inmenu.png"))), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1280, 720, false, false, false, false)));

    private boolean muted = false;

    public static Preferences getInstance() {
        if (INSTANCE == null) INSTANCE = new Preferences();
        return INSTANCE;
    }

    public String getStylePiece() {
        return stylePiece;
    }

    public void setStylePiece(String stylePiece) {
        this.stylePiece = stylePiece;
    }

    /**
     * Permet de changer l'image de la pièce en fonction du style choisi
     *
     * @param etat permet de savoir si on doit changer l'image vers la gauche ou la droite
     */
    public void changerImage(String etat) {
        switch (etat) {
            case "+" -> stylePieceActuel++;
            case "-" -> stylePieceActuel--;
        }
        if (stylePieceActuel >= 3) {
            stylePieceActuel = 0;
        } else if (stylePieceActuel < 0) {
            stylePieceActuel = 2;
        }
        switch (stylePieceActuel) {
            case 0 -> this.setStylePiece("conteneur");
            case 1 -> this.setStylePiece("brique");
            case 2 -> this.setStylePiece("default");
        }
        // Met à jour l'image
        imagePiecePreference.setImage(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/" + stylePiece + "/L.jpg"))));
    }

    public Image getImagePiecePreference() {
        return imagePiecePreference.getImage();
    }

    public void changerFond(String etat) {
        switch (etat) {
            case "+" -> backgroundActuel++;
            case "-" -> backgroundActuel--;
        }
        if (backgroundActuel >= 3) {
            backgroundActuel = 0;
        } else if (backgroundActuel < 0) {
            backgroundActuel = 2;
        }
        switch (backgroundActuel) {
            case 0 -> imageBackground = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/background/industrial.png")));
            case 1 -> imageBackground = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/background/classic.jpg")));
            case 2 -> imageBackground = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/background/original.jpg")));
        }
    }

    public Background getBackground() {
        return new Background(new BackgroundImage(imageBackground, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1280, 720, false, false, false, false)));
    }

    public void setMusiqueMute(boolean etat) {
        muted = etat;
    }

    public boolean getMusiqueMute() {
        return muted;
    }
}
