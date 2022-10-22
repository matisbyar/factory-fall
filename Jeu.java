import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Scanner;

public class Jeu {
    static Plateau p = new Plateau(10, 22);
    static Random random = new Random();
    static boolean jeuEnCours =true;

    static Piece pieceActuelle;
    static int ligneActuelle;
    static int colonneActuelle;
    static JFrame myJFrame = new JFrame();

    public Jeu() {
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        nouvellePieceActuelle();


        p.afficherPlateau();

        myJFrame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_DOWN) {
                    System.out.println("fleshe du bas est actionnée!");
                    tomberPieceActuelle();
                    jouerTour();
                    /**Bouger les pieces vers le bas**/
                }
                else if (keyCode == KeyEvent.VK_LEFT) {
                    System.out.println("fleshe de gauche est actionnée!");
                    deplacerPieceActuelle(colonneActuelle-1);
                    jouerTour();
                    /**Bouger les pieces vers la gauche**/
                }
                else if (keyCode == KeyEvent.VK_RIGHT) {
                    System.out.println("fleshe de droite est actionnée!");
                    deplacerPieceActuelle(colonneActuelle+1);
                    jouerTour();
                    /**Bouger les pieces vers la droite**/
                }
            }
        });
        /**Besoins d'afficher le JFrame pour que le code marche**/
        myJFrame.setVisible(true);
    }


    public static void jouerTour(){
        if (!jeuEnCours){
            System.out.println("Game Over");
            myJFrame.setVisible(false);
        }else{
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
     * Créer une nouvelle pièce sur le plateau, au spawn.
     * Si la case de spawn est occupée, alors le jeu se termine.
     */
    public static void nouvellePieceActuelle() {
        pieceActuelle = genererPieceRandom();
        ligneActuelle = 0;
        colonneActuelle = p.getLargeur() / 2 - 1;
        p.suppressionLignesRemplies();
        p.placerPiece(ligneActuelle, colonneActuelle, pieceActuelle);
    }

    /**
     * Déplace la pièce actuelle horizontalement, la positionne sur la colonne indiquée.
     * Si la nouvelle pièce actuelle a bien été placée, supprime l'ancienne piece, et
     * actualise les coordonnées actuelles
     */
    public static void deplacerPieceActuelle(int colonne) {
        if (p.placerPiece(ligneActuelle, colonne, pieceActuelle)) {
            p.supprimerPiece(ligneActuelle, colonneActuelle);
            colonneActuelle = colonne;
        }
    }

    /**
     * Fait tomber la pièce le plus bas possible dans la colonne actuelle.
     * Si ça échoue, c'est que toute la colonne est occupée, donc la pièce
     * dépasse les limites. On met alors fin au jeu.
     */
    public static void tomberPieceActuelle() {
        if(p.placerPieceParColonne(colonneActuelle, pieceActuelle)) {
            p.supprimerPiece(ligneActuelle, colonneActuelle);
            nouvellePieceActuelle();
        }
        else {
            jeuEnCours = false;
        }
    }

    public static void remplirLigne(int ligne) {
        for (int colonne = 0; colonne < p.getLargeur() - 1; colonne++) {
            p.placerPiece(ligne, colonne, Piece.I);
        }
    }
}
