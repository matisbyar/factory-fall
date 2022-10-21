import java.util.Random;

public class Jeu {
    static Plateau p = new Plateau(10, 5);
    static Random random = new Random();

    public Jeu() {
    }

    /**
     * @return Une pièce non NULL aléatoire
     */
    public static Piece genererPieceRandom() {
        return Piece.values()[Math.abs(random.nextInt()) % 7 + 1];
    }

    public static void main(String[] args) {
        p.placerPieceParColonne(7, Piece.I);
        p.placerPiece(2, 7, Piece.T);
        p.placerPieceParColonne(7, Piece.J);
        p.placerPieceParColonne(7, Piece.I);
        p.placerPieceParColonne(4, Piece.O);

        p.afficherPlateau();
    }
}
