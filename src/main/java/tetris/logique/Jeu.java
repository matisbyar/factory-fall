package tetris.logique;

import javafx.beans.property.*;
import tetris.IJeu;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Jeu implements IJeu {
    public Joueur j;
    public Plateau p;
    public ObjectProperty<Plateau> plateau;
    public ArrayList<Piece> sacProchainesPieces;

    static Random random;
    public BooleanProperty jeuEnCours = new SimpleBooleanProperty();
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
        jeuEnCours.setValue(true);

        sacProchainesPieces = new ArrayList<Piece>();
        this.remplirSacProchainesPieces();
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
        if (!jeuEnCours.getValue()){
            timer.stop();
            p.afficherPlateau();
            System.out.println(j.getScore().getValue());
            System.out.println(p.getRang().getValue());
            System.out.println("Game Over");
            myJFrame.setVisible(false);
        } else {
            p.afficherPlateau();
            remplirSacProchainesPieces();
            System.out.println(j.getScore().getValue());
            System.out.println(p.getRang().getValue());
            System.out.println("_________________________________________\n");
        }
    }

    /**
     * Si le sac des prochaines pièces est vide, le rempli avec une pièce de chaque forme
     */
    public void remplirSacProchainesPieces(){
        if (sacProchainesPieces.isEmpty()) {
            for (int i = 1; i<8; i++) {
                sacProchainesPieces.add(new Piece(Forme.values()[i]));
            }
            Collections.shuffle(sacProchainesPieces);
        }
    }

    /**
     * Supprime et retourne la premiere piece de la liste des pieces suivantes.
     * @return la premiere piece de la liste en attente
     */
    public Piece recupererProchainePiece(){
        return sacProchainesPieces.remove(0);
    }

    /**
     * Créé une nouvelle pièce sur le plateau, au spawn.
     * Si la case de spawn est occupée, alors le jeu se termine.
     * La méthode supprime également les lignes remplies
     * Incremente le score et suprime les lignes si necessaire
     * Incremente le niveau de la partie
     */
    public void nouvellePieceActuelle() {
        pieceActuelle = recupererProchainePiece();
        ligneActuelle = 1;
        colonneActuelle = p.getLargeur() / 2 - 1;
        p.incrementerScoreJoueur(p.suppressionLignesRemplies());
        p.incrementerRang();
        if(!p.placerPiece(ligneActuelle, colonneActuelle, pieceActuelle)){
            jeuEnCours.setValue(false);
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
            jeuEnCours.setValue(false);
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
    public void tournerPieceActuelle(char sens) {
        p.supprimerPieceTotale(ligneActuelle, colonneActuelle, pieceActuelle);
        if (p.placementValide(ligneActuelle, colonneActuelle, pieceActuelle.creerPieceTournee(sens))) {
            pieceActuelle = pieceActuelle.creerPieceTournee(sens);
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
        tournerPieceActuelle('d');
        jouerTour();
    }

    @Override
    public void actionR() {
        System.out.println("Touche R est actionnée !");
        tournerPieceActuelle('g');
        jouerTour();
    }

    @Override
    public void actionEchap() {
        jeuEnCours.setValue(false);
        jouerTour();
    }

    @Override
    public boolean isJeuEnCours() {
        return jeuEnCours.getValue();
    }

    @Override
    public double getScoreJoueurChoisi(Joueur joueurChoisi) {
        return joueurChoisi.getScore().getValue();
    }

    @Override
    public String getPseudoJoueurChoisi(Joueur joueurChoisi) {
        return joueurChoisi.getPseudo();
    }

    @Override
    public IntegerProperty getRang(Plateau plateauChoisi) {
        return plateauChoisi.getRang();
    }

    @Override
    public DoubleProperty scoreProperty() {
        return getJoueur().getScore();
    }

    public BooleanProperty getJeuEnCoursProperty() {
        return jeuEnCours;
    }
}
