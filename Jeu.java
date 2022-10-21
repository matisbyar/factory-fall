import java.util.Random;
import java.util.Scanner;

public class Jeu {
    static Plateau p = new Plateau(10, 22);
    static Random random = new Random();
    static boolean jeuEnCours =true;

    static Piece pieceActuelle;
    static int ligneActuelle;
    static int colonneActuelle;

    public Jeu() {
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        nouvellePieceActuelle();

        while(jeuEnCours) {
            p.afficherPlateau();

            System.out.println("Entrez un numéro de colonne :   (negatif pour drop)");
            int i = scan.nextInt();

            if (i<0) tomberPieceActuelle();
            else deplacerPieceActuelle(i);
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

        p.placerPiece(ligneActuelle, colonneActuelle, pieceActuelle);
    }

    /**
     * Déplace la pièce actuelle horizontallement, la positionne sur la colonne indiquée.
     * Si la nouvelle pièce actuelle a bien été placée, supprime l'ancienne piece, et
     * actualise les coordonnées actuelles
     */
    public static void deplacerPieceActuelle(int colonne) {
        if(p.placerPiece(ligneActuelle, colonne, pieceActuelle)) {
            p.supprimerPiece(ligneActuelle, colonneActuelle);
            colonneActuelle = colonne;
        }
    }

    /**
     * Fais tomber la pièce le plus bas possible dans la colonne actuelle.
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
            System.out.println("GAME OVER");
        }
    }
}
