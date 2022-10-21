public class Plateau {
    private final int hauteur;
    private final int largeur;
    private final Piece[][] plateau;

    /**
     * Le plateau est définie en fonction des largeur et hauteur indiquées, et contient des pièces (NULL ou autre)
     */
    public Plateau(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.plateau = new Piece[hauteur][largeur];
        this.remplirTableau();
    }

    public int getLargeur() {
        return largeur;
    }

    /**
     * @return Le nom (string) de la pièce à l'emplacement indiqué
     */
    public String getPieceNom(int ligne, int colonne) {
        return this.plateau[ligne][colonne].getNom();
    }

    /**
     * Place des pièces NULL dans toutes les cases du plateau. C'est l'initialisation du plateau
     */
    public void remplirTableau() {
        Piece piece = Piece.NULL;

        for(int ligne = 0; ligne < this.hauteur; ++ligne) {
            for(int colonne = 0; colonne < this.largeur; ++colonne) {
                this.plateau[ligne][colonne] = piece;
            }
        }

    }

    /**
     * @return Vrai si la case indiqué n'est pas occupée par une pièce non NULL
     */
    public boolean estVide(int ligne, int colonne) {
        return this.plateau[ligne][colonne].getNom().equals(" ");
    }

    /**
     * @return Vrai si la case indiquée est dans les limites du plateau, et qu'elle est vide
     */
    public boolean placementValide(int ligne, int colonne, Piece piece) {
        return 0 <= ligne && ligne < this.hauteur && 0 <= colonne && colonne < this.largeur && this.estVide(ligne, colonne);
    }

    /**
     * Appelle placementValide() pour vérifier les coordonées, et place la pièce si le placement est valide
     * @return Vrai si la pièce a été placée
     */
    public boolean placerPiece(int ligne, int colonne, Piece piece) {
        if (this.placementValide(ligne, colonne, piece)) {
            this.plateau[ligne][colonne] = piece;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Le for est vide, car l'action se trouve dans la condition d'arret, via placerPiece()
     * @return Vrai si la pièce a pu être placée
     */
    public boolean placerPieceParColonne(int colonne, Piece piece) {
        int ligne;
        for(ligne = this.hauteur - 1; ligne >= 0 && !this.placerPiece(ligne, colonne, piece); --ligne) {
        }

        return ligne >= 0;
    }

    /**
     * Supprime la pièce à l'emplacement indiqué (la remplace par NULL)
     */
    public void supprimerPiece(int ligne, int colonne) {
        this.plateau[ligne][colonne] = Piece.NULL;
    }

    /**
     * Affiche le plateau mis en forme dans le terminal. Les pièces NULL ne sont pas affichées,
     * les autres sont représentées par leur nom.
     */
    public void afficherPlateau() {
        for(int ligne = 0; ligne < this.hauteur; ++ligne) {
            StringBuilder plateauString = new StringBuilder("| ");

            for(int colonne = 0; colonne < this.largeur; ++colonne) {
                plateauString.append(this.getPieceNom(ligne, colonne).concat(" | "));
            }

            System.out.println(plateauString);
        }

    }
}
