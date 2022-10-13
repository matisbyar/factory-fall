import java.util.Arrays;
//
public class Plateau {
    private final int longueur;
    private final int hauteur;
    private final boolean[][] plateau;

    public Plateau(int longueur, int hauteur) {
        this.longueur = longueur;
        this.hauteur = hauteur;
        this.plateau = new boolean[hauteur][longueur];
        this.remplirPlateau();
    }

    public void placerPixel(boolean a, int y, int x) {
        this.plateau[y][x] = a;
    }

    private void remplirPlateau() {
        for(int i = 0; this.hauteur > i; ++i) {
            for(int j = 0; this.longueur > j; ++j) {
                this.placerPixel(false, i, j);
            }
        }

    }

    public void afficherPlateau() {
        System.out.println(Arrays.deepToString(this.plateau));

        for(int i = 0; this.hauteur > i; ++i) {
            StringBuilder a = new StringBuilder("| ");

            for(int j = 0; this.longueur > j; ++j) {
                if (this.plateau[i][j]) {
                    a.append("* | ");
                } else {
                    a.append("  | ");
                }
            }

            System.out.println(a);
        }

    }

    public void peutSupprimerLigne() {
        boolean lignePleine = false;
        int y = 19;
        while (y < plateau.length && !lignePleine && y != 0) {
            lignePleine = true;
            for (int x = 0; x < plateau[0].length; x++) {
                if (!plateau[y][x]) {
                    lignePleine = false;
                    break;
                }
            }
            y--;
        }
        if (lignePleine) {
            y++;
            supprimerLigne((y));
        }
        //return lignePleine + " at " + (y);
    }

    public void supprimerLigne(int y) {
        for (int x = 0; x < plateau[0].length; x++) {
            plateau[y][x] = false;
        }
        int yDebut;
        yDebut = y;
        int i = y;
        while (i < plateau.length) {
            if (descendreLigne(i, yDebut)) {
                break;
            }
            i--;
        }
    }

    public boolean descendreLigne(int y, int yDebut) {
        boolean ligneVide = true;
        if (y != yDebut) {
            for (int x = 0; x < plateau[0].length; x++) {
                if (plateau[y][x]) {
                    ligneVide = false;
                    break;
                }
            }
        } else {
            ligneVide = false;
        }

        for (int x = 0; x < plateau[0].length; x++) {
            if (y == 0) {
                plateau[y][x] = false;
            }
            plateau[y][x] = plateau[(y-1)][x];
        }
        return ligneVide;
    }

    public boolean colisionPieces(Piece piece, int positionY, int positionX, int rotation){
        switch (piece) {
            case I :
                switch (rotation) {
                    case 0 :
                        for (int x = positionX; x < (positionX + piece.getPiece()[0].length); x++) {
                            if (plateau[positionY][x]) {
                                return false;
                            }
                        }
                        break;
                    case 90 :
                        for (int y = positionY; y > (positionY - piece.getPiece()[0].length); y--) {
                            if (plateau[y][positionX]) {
                                return false;
                            }
                        }
                        break;
                    case 180 :
                        for (int x = positionX; x < (positionX + piece.getPiece()[0].length); x++) {
                            if (plateau[(positionY+1)][x]) {
                                return false;
                            }
                        }
                        break;
                    case 270 :
                        for (int y = positionY; y > (positionY - piece.getPiece()[0].length); y--) {
                            if (plateau[y][positionX-1]) {
                                return false;
                            }
                        }
                        break;
                }
                break;
            case O :
                for (int y = positionY; y < (positionY + piece.getPiece().length); y++) {
                    for (int x = positionX; x < (positionX + piece.getPiece()[0].length); x++) {
                        if (plateau[y][x]) {
                            return false;
                        }
                    }
                }
                break;
            case T :
                switch (rotation) {
                    case 0 :
                        for (int y = positionY; y > (positionY - piece.getPiece().length); y--) {
                            for (int x = (positionX - 1); x < (positionX + piece.getPiece()[0].length - 1); x++) {
                                if (x == 0) {
                                    if (piece.getPiece()[(y - positionY + 1)][(0)]) {
                                        if (plateau[y][x]) {
                                            return false;
                                        }
                                    }
                                } else if (piece.getPiece()[(y - positionY + 1)][(x + 1 - positionX)]) {
                                    if (plateau[y][x]) {
                                        return false;
                                    }
                                }
                            }
                        }
                        break;
                    case 90 :
                        for (int x = positionX+1; x > (positionX - piece.getPiece().length +1); x--) {
                            for (int y = positionY; y > (positionY - piece.getPiece()[0].length); y--) {
                                if (piece.getPiece()[(x - positionX)][(y - positionY + 2)]) {
                                    if (plateau[y+1][positionX+2-x]) {
                                        return false;
                                    }
                                }
                            }
                        }
                        break;
                    case 180 :
                        for (int y = positionY; y < (positionY + piece.getPiece().length); y++) {
                            for (int x = (positionX - 1); x < (positionX + piece.getPiece()[0].length - 1); x++) {
                                if (x == 0) {
                                    if (piece.getPiece()[(y - positionY)][(0)]) {
                                        if (plateau[y][x]) {
                                            return false;
                                        }
                                    }
                                } else if (piece.getPiece()[(y - positionY)][(x + 1 - positionX)]) {
                                    if (plateau[y][x]) {
                                        return false;
                                    }
                                }
                            }
                        }
                        break;
                    case 270 :
                        for (int x = positionX; x < (positionX + piece.getPiece().length); x++) {
                            for (int y = positionY; y > (positionY - piece.getPiece()[0].length); y--) {
                                if (piece.getPiece()[(x - positionX)][(y - positionY + 2)]) {
                                    if (plateau[y+1][x-1]) {
                                        return false;
                                    }
                                }
                            }
                        }
                        break;
                }
                break;
            case S :
                switch (rotation) {
                    case 0 :
                        break;
                    case 90 :
                        break;
                    case 180 :
                        break;
                    case 270 :
                        break;
                }
                break;
            case Z :
                switch (rotation) {
                    case 0 :
                        break;
                    case 90 :
                        break;
                    case 180 :
                        break;
                    case 270 :
                        break;
                }
                break;
            case J :
                switch (rotation) {
                    case 0 :
                        break;
                    case 90 :
                        break;
                    case 180 :
                        break;
                    case 270 :
                        break;
                }
                break;
            case L :
                switch (rotation) {
                    case 0 :
                        break;
                    case 90 :
                        break;
                    case 180 :
                        break;
                    case 270 :
                        break;
                }
                break;
        }
        return true;
    }
}
