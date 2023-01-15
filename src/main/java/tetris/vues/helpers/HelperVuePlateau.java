package tetris.vues.helpers;

import javafx.geometry.Pos;
import tetris.logique.Plateau;

/**
 * La VuePlateau est une classe qui hérite de VueGrille.
 * Elle est utilisée pour afficher toute la grille du jeu.
 *
 * @see HelperGrille
 */
public class HelperVuePlateau extends HelperGrille {

    public HelperVuePlateau(Plateau p) {
        super(p);
        styliser();
    }

    private void styliser() {
        this.setAlignment(Pos.CENTER);
    }
}
