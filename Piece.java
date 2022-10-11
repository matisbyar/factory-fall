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

    private String nom;
    private boolean[][] piece;

    private Piece(boolean[][] piece, String nom) {
        this.piece = piece;
        this.nom = nom;
    }

    public boolean[][] getPiece() {
        return this.piece;
    }

    public void afficherPiece() {
        boolean[][] var1 = this.piece;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            boolean[] booleans = var1[var3];
            StringBuilder a = new StringBuilder();

            for(int j = 0; this.piece[0].length > j; ++j) {
                if (booleans[j]) {
                    a.append("*");
                } else {
                    a.append(" ");
                }
            }

            System.out.println(a);
        }

    }
}
