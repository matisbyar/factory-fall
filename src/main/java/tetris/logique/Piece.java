package tetris.logique;

import tetris.IPiece;


/**
 * Les pièces sont définies par un nom
 */
public class Piece implements IPiece {

    private String nom;
    private int[][] coords = new int[4][2];

    public Piece(Forme forme) {
        int[][][] casesDefaut = {
                {{0,0},{0,0},{0,0},{0,0}}, //NULL
                {{0, -1}, {0, 0}, {0, 1}, {0, 2}}, //I
                {{0, 0}, {1, 0}, {0, 1}, {1, 1}}, //O
                {{0, -1}, {0, 0}, {0, 1}, {1, 0}}, //T
                {{0, -1}, {0, 0}, {1, 0}, {1, 1}}, //S
                {{1, -1}, {1, 0}, {0, 0}, {0, 1}}, //Z
                {{1, -1}, {0, -1}, {0, 0}, {0, 1}}, //J
                {{0, -1}, {0,0}, {0, 1}, {1,1}} //L
        };

        this.nom = forme.getNom();
        coords = casesDefaut[forme.ordinal()]; // On attribue la position relative des cases correspondantes au nom
    }

    public String getNom() {
        return this.nom;
    }

    public int[][] getForme() {
        return coords;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Créé une nouvelle pièce, y inscrit des positions de cases tournée par rapport à this
     * @return la nouvelle pièce
     */
    public Piece creerPieceTournee() {
        Piece nouvellePiece = new Piece(Forme.NULL);
        for(int i = 0; i < 4; i++) {
            nouvellePiece.coords[i][0] = this.coords[i][1] * -1;
            nouvellePiece.coords[i][1] = this.coords[i][0];
        }
        nouvellePiece.setNom(this.getNom());
        return nouvellePiece;
    }
}
