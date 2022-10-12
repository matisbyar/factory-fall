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
}
