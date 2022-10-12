import java.util.Arrays;
//
public class Plateau {
    private int longueur;
    private int hauteur;
    private boolean[][] plateau;

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
            String a = "| ";

            for(int j = 0; this.longueur > j; ++j) {
                if (this.plateau[i][j]) {
                    a = a + "* | ";
                } else {
                    a = a + "  | ";
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
}
