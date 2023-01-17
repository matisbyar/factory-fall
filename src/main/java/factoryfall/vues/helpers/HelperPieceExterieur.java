package factoryfall.vues.helpers;

import factoryfall.logique.Plateau;

/**
 * La VuePieceExterieur est une classe qui hérite de VueGrille.
 * Elle est utilisée pour afficher une portion d'une pièce dans la vue de la prochaine pièce ou de la pièce sauvegardée.
 *
 * @see HelperGrille
 */
public class HelperPieceExterieur extends HelperGrille {

    public HelperPieceExterieur(Plateau p) {
        super(p);
    }
}
