package tetris.logique;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import tetris.IJeu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Jeu implements IJeu {
    public Joueur j;
    public Plateau p;
    public ObjectProperty<Plateau> plateau;

    static Random random;
    public boolean jeuEnCours;
    public static Timer timer;

    static Piece pieceActuelle;
    static int ligneActuelle;
    static int colonneActuelle;
    static JFrame myJFrame = new JFrame();

    public Jeu() {
        j = new Joueur("Luther");
        p = new Plateau(10, 22, j);
        plateau = new SimpleObjectProperty<>();

        random = new Random();
        jeuEnCours = true;

        nouvellePieceActuelle();
    }

    /*public static void main(String[] args) {
        /

        p.afficherPlateau();
    }*/

    /**
     * Affiche le Plateau si le jeu est toujours en cours et arrete la partie sinon
     */
    public void jouerTour(){
        if (!jeuEnCours){
            timer.stop();
            p.afficherPlateau();
            System.out.println(j.getScore());
            System.out.println(p.getRang());
            System.out.println("Game Over");
            myJFrame.setVisible(false);
        } else {
            p.afficherPlateau();
            System.out.println(j.getScore());
            System.out.println(p.getRang());
            System.out.println("_________________________________________\n");
        }
    }

    /**
     * @return Une pièce non NULL aléatoire
     */
    public Piece genererPieceRandom() {
        return new Piece(Forme.values()[Math.abs(random.nextInt()) % 7 + 1]);
    }

    /**
     * Créé une nouvelle pièce sur le plateau, au spawn.
     * Si la case de spawn est occupée, alors le jeu se termine.
     * La méthode supprime également les lignes remplies
     * Incremente le score et suprime les lignes si necessaire
     * Incremente le niveau de la partie
     */
    public void nouvellePieceActuelle() {
        pieceActuelle = genererPieceRandom();
        ligneActuelle = 1;
        colonneActuelle = p.getLargeur() / 2 - 1;
        p.incrementerScoreJoueur(p.suppressionLignesRemplies());
        p.incrementerRang();
        if(!p.placerPiece(ligneActuelle, colonneActuelle, pieceActuelle)){
            jeuEnCours = false;
        }
    }

    /**
     * Supprime la pièce actuelle. Essaie de placer la pièce sur la colonne indiqué.
     * Si la nouvelle pièce actuelle a bien été placée, actualise les coordonnées actuelles.
     * Sinon, replace l'ancienne pièce actuelle.
     */
    public void deplacerPieceActuelle(int colonne) {
        p.supprimerPieceTotale(ligneActuelle, colonneActuelle, pieceActuelle);
        if (p.placerPiece(ligneActuelle, colonne, pieceActuelle)) {
            colonneActuelle = colonne;
        }
        else p.placerPiece(ligneActuelle, colonneActuelle, pieceActuelle);
    }

    /**
     * Fait tomber la pièce le plus bas possible dans la colonne actuelle.
     * Si ça échoue, c'est que toute la colonne est occupée, donc la pièce
     * dépasse les limites. On met alors fin au jeu.
     */
    public void tomberPieceActuelle() {
        if(p.placerPieceParColonne(ligneActuelle, colonneActuelle, pieceActuelle)) {
            nouvellePieceActuelle();
        }
        else {
            jeuEnCours = false;
        }
    }

    /**
     * Fait tomber la pièce d'une ligne dans la colonne actuelle.
     * Si ça échoue, pose la pièce actuelle aux coordonées actuelles
     * (donc sans baisser de ligne).
     */
    public void tomberPieceActuelle1Ligne() {
        p.supprimerPieceTotale(ligneActuelle, colonneActuelle, pieceActuelle);
        if (p.placerPiece(ligneActuelle+1, colonneActuelle, pieceActuelle)) {
            ligneActuelle++;
        }
        else {
            p.placerPiece(ligneActuelle, colonneActuelle, pieceActuelle);
            nouvellePieceActuelle();
        }
    }

    /**
     * Supprimer la pièce courante, puis vérifie la validité du placement de la rotation de la pièce
     * actuelle aux coordonées actuelles. Si c'est valide, place la nouvelle pièce, sinon replace l'ancienne
     */
    public void tournerPieceActuelle() {
        p.supprimerPieceTotale(ligneActuelle, colonneActuelle, pieceActuelle);
        if (p.placementValide(ligneActuelle, colonneActuelle, pieceActuelle.creerPieceTournee())) {
            pieceActuelle = pieceActuelle.creerPieceTournee();
        }
        p.placerPiece(ligneActuelle, colonneActuelle, pieceActuelle);
    }

    /**
     * Méthode de debug qui remplit les colonnes 0 à 9 du tableau à la ligne donnée
     * @param ligne Ligne que l'on souhaite remplir
     */
    public void remplirLigne(int ligne) {
        for (int colonne = 0; colonne < p.getLargeur() - 1; colonne++) {
            p.placerPiece(ligne, colonne, new Piece(Forme.I));
        }
    }

    public static Piece getPieceActuelle() {
        return pieceActuelle;
    }

    public static int getColonneActuelle() {
        return colonneActuelle;
    }

    @Override
    public Joueur getJoueur() {
        return j;
    }

    @Override
    public Plateau getPlateau() {
        return p;
    }

    public static int getLigneActuelle() {
        return ligneActuelle;
    }

    @Override
    public ObjectProperty<Plateau> plateauProperty() {
        return plateau;
    }

    @Override
    public void actionGauche() {
        // Bouger les pieces vers la gauche
        System.out.println("Flèche de gauche est actionnée !");
        deplacerPieceActuelle(colonneActuelle - 1);
        jouerTour();
    }

    @Override
    public void actionDroite() {
        // Bouger les pieces vers la droite
        System.out.println("Flèche de droite est actionnée !");
        deplacerPieceActuelle(colonneActuelle + 1);
        jouerTour();
    }

    @Override
    public void actionBas() {
        System.out.println("Flèche du bas est actionnée !");
        tomberPieceActuelle1Ligne();
        jouerTour();
    }

    @Override
    public void actionEspace() {
        System.out.println("Barre Espace est actionnée !");
        tomberPieceActuelle();
        jouerTour();
    }

    @Override
    public void actionHaut() {
        System.out.println("Flèche du haut est actionnée !");
        tournerPieceActuelle();
        jouerTour();
    }

    @Override
    public void actionEchap() {
        jeuEnCours = false;
        jouerTour();
    }

    @Override
    public boolean isJeuEnCours() {
        return jeuEnCours;
    }
}
