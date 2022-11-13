package tetris.logique;

import tetris.IPiece;

/**
 * Les pièces sont définies par un nom
 */
public enum Piece implements IPiece {
    NULL(" ", new int[][]{{0,0},{0,0},{0,0},{0,0}}),
    I("I", new int[][]{{0, -1}, {0, 0}, {0, 1}, {0, 2}}),
    O("O", new int[][]{{0, 0}, {1, 0}, {0, 1}, {1, 1}}),
    T("T", new int[][]{{0, -1}, {0, 0}, {0, 1}, {1, 0}}),
    S("S", new int[][]{{0, -1}, {0, 0}, {1, 0}, {1, 1}}),
    Z("Z", new int[][]{{1, -1}, {1, 0}, {0, 0}, {0, 1}}),
    J("J", new int[][]{{1, -1}, {0, -1}, {0, 0}, {0, 1}}),
    L("L", new int[][]{{0, -1}, {0,0}, {0, 1}, {1,1}});

    private final String nom;
    private final int [][] piece;

    Piece(String nom, int [][] piece) {
        this.nom = nom;
        this.piece = piece;
    }

    /**
     * @return Le nom (string) de l'objet this
     */
    public String getNom() {
        return this.nom;
    }

    public int[][] getPiece() {
        return piece;
    }

    public Piece creerPieceTournee() {
        Piece nouvellePiece = Piece.NULL;
        for(int i = 0; i < 4; ++i) {
            nouvellePiece.getPiece()[i][0] = this.getPiece()[i][1] * -1;
            nouvellePiece.getPiece()[i][1] = this.getPiece()[i][0];
        }
        return nouvellePiece;
    }
}
