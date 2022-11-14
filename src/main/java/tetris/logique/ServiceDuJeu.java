package tetris.logique;

/**
 * La classe ServiceDuJeu permet de créer une instance du jeu et est utilisée dans la classe TetrisIHM.
 * Cette classe TetrisIHM appelle les vues.
 */
public class ServiceDuJeu {
    private Jeu jeu;

    public ServiceDuJeu() {
        this.jeu = new Jeu();
    }

    public Jeu getJeu() {
        return jeu;
    }
}
