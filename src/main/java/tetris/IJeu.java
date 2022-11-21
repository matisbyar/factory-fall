package tetris;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import tetris.logique.Joueur;
import tetris.logique.Plateau;

public interface IJeu {
    ObjectProperty<Plateau> plateauProperty();

    DoubleProperty scoreProperty();

    BooleanProperty getJeuEnCoursProperty();
    
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

    double getScoreJoueurChoisi(Joueur joueurChoisi);

    String getPseudoJoueurChoisi(Joueur joueurChoisi);

    IntegerProperty getRang(Plateau plateauChoisi);


}
