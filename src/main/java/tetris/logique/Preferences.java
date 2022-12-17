package tetris.logique;

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
    private String stylePiece = "container";

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
}
