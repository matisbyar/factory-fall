package tetris.logique;

import tetris.IJeu;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Scanner;

public class Jeu implements IJeu {
    public static Plateau p = new Plateau(10, 22);
    static Random random = new Random();
    static boolean jeuEnCours =true;

    static Piece pieceActuelle;
    static int ligneActuelle;
    static int colonneActuelle;
    static JFrame myJFrame = new JFrame();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        nouvellePieceActuelle();

        p.afficherPlateau();

        myJFrame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_SPACE) {
                    // Bouger les pieces tout en bas
                    System.out.println("Flèche du bas est actionnée !");
                    tomberPieceActuelle();
                    jouerTour();
                }
                if (keyCode == KeyEvent.VK_DOWN) {
                    // Bouger les pieces vers le bas
                    System.out.println("Flèche du bas est actionnée !");
                    tomberPieceActuelle1Ligne();
                    jouerTour();
                }
                else if (keyCode == KeyEvent.VK_LEFT) {
                    // Bouger les pieces vers la gauche
                    System.out.println("Flèche de gauche est actionnée !");
                    deplacerPieceActuelle(colonneActuelle - 1);
                    jouerTour();
                }
                else if (keyCode == KeyEvent.VK_RIGHT) {
                    // Bouger les pieces vers la droite
                    System.out.println("Flèche de droite est actionnée !");
                    deplacerPieceActuelle(colonneActuelle + 1);
                    jouerTour();
                }
                else if (keyCode == KeyEvent.VK_UP) {
                    System.out.println("Flèche du haut est actionnée !");
                    Jeu.tournerPieceActuelle();
                    Jeu.jouerTour();
                }
                else if (keyCode == KeyEvent.VK_ESCAPE) {
                    jeuEnCours = false;
                    jouerTour();
                }
            }
        });
        // Besoin d'afficher le JFrame pour que le code marche
        myJFrame.setVisible(true);
    }

    /**
     * Affiche le Plateau si le jeu est toujours en cours et arrete la partie sinon
     */
    public static void jouerTour(){
        if (!jeuEnCours){
            p.afficherPlateau();
            System.out.println("Game Over");
            myJFrame.setVisible(false);
        } else {
            p.afficherPlateau();
        }
    }

    /**
     * @return Une pièce non NULL aléatoire
     */
    public static Piece genererPieceRandom() {
        return Piece.values()[Math.abs(random.nextInt()) % 7 + 1];
    }

    /**
     * Créé une nouvelle pièce sur le plateau, au spawn.
     * Si la case de spawn est occupée, alors le jeu se termine.
     * La méthode supprime également les lignes remplies
     */
    public static void nouvellePieceActuelle() {
        pieceActuelle = genererPieceRandom();
        ligneActuelle = 1;
        colonneActuelle = p.getLargeur() / 2 - 1;
        p.suppressionLignesRemplies();
        if(!p.placerPiece(ligneActuelle, colonneActuelle, pieceActuelle)){
            jeuEnCours = false;
        }
    }

    /**
     * Supprime la pièce actuelle. Essaie de placer la pièce sur la colonne indiqué.
     * Si la nouvelle pièce actuelle a bien été placée, actualise les coordonnées actuelles.
     * Sinon, replace l'ancienne pièce actuelle.
     */
    public static void deplacerPieceActuelle(int colonne) {
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
    public static void tomberPieceActuelle() {
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
    public static void tomberPieceActuelle1Ligne() {
        p.supprimerPieceTotale(ligneActuelle, colonneActuelle, pieceActuelle);
        if (p.placerPiece(ligneActuelle+1, colonneActuelle, pieceActuelle)) {
            ligneActuelle++;
        }
        else {
            p.placerPiece(ligneActuelle, colonneActuelle, pieceActuelle);
            nouvellePieceActuelle();
        }
    }

    public static void tournerPieceActuelle() {
        p.supprimerPieceTotale(ligneActuelle, colonneActuelle, pieceActuelle);
        if (p.placementValide(ligneActuelle, colonneActuelle, pieceActuelle.creerPieceTournee())) {
            pieceActuelle = pieceActuelle.creerPieceTournee();
        }
        p.placerPiece(ligneActuelle, colonneActuelle, pieceActuelle);
    }

    /**
     * Méthode de debug qui remplit les colonnes 0 à 10 du tableau à la ligne donnée
     * @param ligne Ligne que l'on souhaite remplir
     */
    public static void remplirLigne(int ligne) {
        for (int colonne = 0; colonne < p.getLargeur() - 1; colonne++) {
            p.placerPiece(ligne, colonne, Piece.I);
        }
    }

    public static boolean isJeuEnCours() {
        return jeuEnCours;
    }

    public static void setJeuEnCours(boolean jeuEnCours) {
        Jeu.jeuEnCours = jeuEnCours;
    }

    public static Piece getPieceActuelle() {
        return pieceActuelle;
    }

    public static int getColonneActuelle() {
        return colonneActuelle;
    }

}
