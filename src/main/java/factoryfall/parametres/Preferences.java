package factoryfall.parametres;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import factoryfall.FactoryFall;
import factoryfall.logique.Score;
import factoryfall.stockage.ScoreManager;
import factoryfall.stockage.Session;

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
    private ImageView imagePiecePreference = new ImageView(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/" + stylePiece + "/L.jpg"))));

    /**
     * La préférence du fond.
     */
    private String background = "1";
    private int backgroundActuel = 0;
    private Image imageBackground = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/background/" + background + ".png")));

    private boolean muted = false;

    public static Preferences getInstance() {
        if (INSTANCE == null) INSTANCE = new Preferences();
        return INSTANCE;
    }

    /**
     * Réinitialise toutes les préférences du joueur.
     */
    public void reinitialiser() {
        stylePiece = "conteneur";
        stylePieceActuel = 0;
        imagePiecePreference = new ImageView(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/" + stylePiece + "/L.jpg"))));

        backgroundActuel = 0;
        background = "industrial";
        imageBackground = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/background/" + background + ".png")));


        muted = false;
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
        imagePiecePreference.setImage(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/" + stylePiece + "/L.jpg"))));
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
            case 0 ->
                    imageBackground = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/background/1.png")));
            case 1 ->
                    imageBackground = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/background/3.png")));
            case 2 ->
                    imageBackground = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/background/2.png")));
        }
    }

    public void changerMusique() {
        Musique.btnMute();
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

    public boolean isLocked(String typePerso) {
        Preferences preferences = Preferences.getInstance();
        Session joueur = Session.getInstance();
        Score bestScore = ScoreManager.getInstance().getHighScoreByLogin(joueur.getLogin());
        switch (typePerso) {
            case "pieces":
                if (joueur.isConnected()) {
                    try {
                        if (bestScore.getScore() >= 40000) {
                            return false;
                        } else if (bestScore.getScore() >= 20000) {
                            if (preferences.getStylePiece().equals("default")) {
                                return true;
                            }
                            if (preferences.getStylePiece().equals("brique")) {
                                return false;
                            }
                        } else {
                            if (preferences.getStylePiece().equals("brique") || preferences.getStylePiece().equals("default")) {
                                return true;
                            }
                        }
                    } catch (NullPointerException e) {
                        return true;
                    }
                } else {
                    if (preferences.getStylePiece().equals("brique") || preferences.getStylePiece().equals("default")) {
                        return true;
                    }
                }
            default:
                return false;
        }
    }
}