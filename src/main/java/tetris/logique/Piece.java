package tetris.logique;

import tetris.IPiece;

/**
 * Les pièces sont définies par un nom
 */
public class Piece implements IPiece {

    private String nom;
    private int[][] coords = new int[4][2];

    public Piece(Forme forme) {
        this.nom = forme.getNom();
        System.arraycopy(forme.getFormeInit(), 0, coords, 0, 4);
    }

    /**
     * @return Le nom (string) de l'objet this
     */
    public String getNom() {
        return this.nom;
    }

    public int[][] getForme() {
        return coords;
    }

    public Piece creerPieceTournee() {
        int[][] temp = new int[4][2];
        System.arraycopy(this.coords, 0, temp, 0, 4);
        for(int i = 0; i < 4; i++) {
            this.coords[i][0] = temp[i][1] * -1;
            this.coords[i][1] = temp[i][0];
        }
        return this;
    }
}
