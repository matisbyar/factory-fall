package tetris.logique;

import javafx.beans.property.*;
import tetris.IJeu;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class Jeu implements IJeu {
    private Joueur j;
    private Plateau p;

    private ArrayList<Piece> sacProchainesPieces;
    private BooleanProperty jeuEnCours;

    public static Timer timer;
    public static Piece pieceActuelle;
    public static int ligneActuelle;
    public static int colonneActuelle;

    public Jeu() {
        j = new Joueur("Luther");
        p = new Plateau(10, 22, j);

        jeuEnCours = new SimpleBooleanProperty(true);

        sacProchainesPieces = new ArrayList<>();
        this.remplirSacProchainesPieces();
        nouvellePieceActuelle();
    }

    /**
     * Affiche le Plateau si le jeu est toujours en cours et arrête la partie sinon
     */
    public void jouerTour(){
        if (!jeuEnCours.getValue()){
            timer.stop();
            p.afficherPlateau();
            System.out.println(j.getScore().getValue());
            System.out.println(p.getRang().getValue());
            System.out.println("Game Over");
        } else {
            p.afficherPlateau();
            remplirSacProchainesPieces();
            timer.setDelay( (int)(Math.pow(0.8-((p.getRang().getValue()-1)*0.007) ,p.getRang().getValue()-1) *1000 ));
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
     * Incrémente le score et supprime les lignes si nécessaires
     * Incrémente le niveau de la partie.
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
     * Si ça échoue, pose la pièce actuelle aux coordonnées actuelles
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
     * actuelle aux coordonnées actuelles. Si c'est valide, place la nouvelle pièce, sinon replace l'ancienne
     */
    public void tournerPieceActuelle(char sens) {
        p.supprimerPieceTotale(ligneActuelle, colonneActuelle, pieceActuelle);
        if (p.placementValide(ligneActuelle, colonneActuelle, pieceActuelle.creerPieceTournee(sens))) {
            pieceActuelle = pieceActuelle.creerPieceTournee(sens);
        }
        p.placerPiece(ligneActuelle, colonneActuelle, pieceActuelle);
    }

    @Override
    public void actionGauche() {
        System.out.println("Flèche de gauche est actionnée !");
        deplacerPieceActuelle(colonneActuelle - 1);
        jouerTour();
    }

    @Override
    public void actionDroite() {
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
    public IntegerProperty getRang(Plateau plateauChoisi) {
        return plateauChoisi.getRang();
    }

    @Override
    public Joueur getJoueur() {
        return j;
    }

    @Override
    public Plateau getPlateau() {
        return p;
    }

    public BooleanProperty jeuEnCoursProperty() {
        return jeuEnCours;
    }
}
