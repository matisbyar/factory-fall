package tetris;

import javafx.beans.property.ObjectProperty;
import tetris.logique.Jeu;
import tetris.logique.Joueur;
import tetris.logique.Plateau;

public interface IJeu {
    ObjectProperty<Plateau> plateauProperty();

    void actionGauche();
    void actionDroite();
    void actionBas();
    void actionHaut();
    void actionEspace();
    void actionEchap();

    Plateau getPlateau();
    Joueur getJoueur();

    void nouvellePieceActuelle();

    void jouerTour();

    void tomberPieceActuelle1Ligne();

    boolean isJeuEnCours();
}
