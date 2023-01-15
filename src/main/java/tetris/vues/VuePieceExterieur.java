package tetris.vues;

import tetris.logique.Plateau;

/**
 * La VuePieceExterieur est une classe qui hérite de VueGrille.
 * Elle est utilisée pour afficher une portion d'une pièce dans la vue de la prochaine pièce ou de la pièce sauvegardée.
 *
 * @see VueGrille
 */
public class VuePieceExterieur extends VueGrille {

    public VuePieceExterieur(Plateau p) {
        super(p);
    }
}
