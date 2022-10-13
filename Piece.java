/**
 * La classe **enum** Piece contient toutes les formes de pièces. Sa nature enum est un choix qui nous permet de créer
 * plusieurs catégories de pièces.
 *///
public enum Piece {
    I(new boolean[][]{{true, true, true, true}}, "I"),
    O(new boolean[][]{{true, true}, {true, true}}, "O"),
    T(new boolean[][]{{false, true, false}, {true, true, true}}, "T"),
    S(new boolean[][]{{false, true, true}, {true, true, false}}, "S"),
    Z(new boolean[][]{{true, true, false}, {false, true, true}}, "Z"),
    J(new boolean[][]{{true, false, false}, {true, true, true}}, "J"),
    L(new boolean[][]{{false, false, true}, {true, true, true}}, "L");

    private final String nom;
    private final boolean[][] piece;

    Piece(boolean[][] piece, String nom) {
        this.piece = piece;
        this.nom = nom;
    }

    public boolean[][] getPiece() {
        return this.piece;
    }

    public void afficherPiece() {

        for (boolean[] booleans : this.piece) {
            StringBuilder a = new StringBuilder();

            for (int j = 0; this.piece[0].length > j; ++j) {
                if (booleans[j]) {
                    a.append("*");
                } else {
                    a.append(" ");
                }
            }

            System.out.println(a);
        }

    }
    public String getNom() {
        return nom;
    }
}
