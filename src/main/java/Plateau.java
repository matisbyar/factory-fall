public class Plateau {
    private final int hauteur;
    private final int largeur;
    private final Piece[][] plateau;

    /**
     * Le plateau est défini en fonction des largeur et hauteur indiquées, et contient des pièces (NULL ou autre).
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
    public int getHauteur() { return hauteur; }
    public Piece[][] getPlateau() { return plateau;}

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
     * @return Vrai si la case indiquée n'est pas occupée par une pièce non NULL
     */
    public boolean estVide(int ligne, int colonne) {
        return this.plateau[ligne][colonne].getNom().equals(" ");
    }

    /**
     * @return Vrai si la case indiquée est dans les limites du plateau, et qu'elle est vide
     */
    public boolean placementValide(int ligne, int colonne, Piece piece) {
        for(int numCase = 0; numCase < 4; numCase++) {
            if (!(0 <= ligne-piece.getPiece()[numCase][0]
                    && ligne-piece.getPiece()[numCase][0] < this.hauteur
                    && 0 <= colonne+piece.getPiece()[numCase][1]
                    && colonne+piece.getPiece()[numCase][1] < this.largeur
                    && this.estVide(ligne-piece.getPiece()[numCase][0], colonne+piece.getPiece()[numCase][1]))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Appelle placementValide() pour vérifier les coordonnées et place la pièce si le placement est valide
     * @return Vrai si la pièce a été placée
     */
    public boolean placerPiece(int ligne, int colonne, Piece piece) {
        if (this.placementValide(ligne, colonne, piece)) {
            for (int numCase = 0; numCase < 4; numCase++) {
                this.plateau[ligne-piece.getPiece()[numCase][0]][colonne+piece.getPiece()[numCase][1]] = piece;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Le for est vide, car l'action se trouve dans la condition d'arrêt, via placerPiece()
     * @return Vrai si la pièce a pu être placée
     * Pré-requis : la position actuelle donnée en paramêtre est valide.
     */
    public boolean placerPieceParColonne(int ligneDepart, int colonne, Piece piece) {
        supprimerPieceTotale(ligneDepart, colonne, piece);

        if(placementValide(ligneDepart+1, colonne, piece)){
            return placerPieceParColonne(ligneDepart+1, colonne, piece);
        }
        else return placerPiece(ligneDepart, colonne, piece);
    }

    /**
     * Supprime la pièce à l'emplacement indiqué (la remplace par NULL)
     */
    public void supprimerPieceTotale(int ligne, int colonne, Piece piece) {
        for (int numCase = 0; numCase < 4; numCase++) {
            this.plateau[ligne-piece.getPiece()[numCase][0]][colonne+piece.getPiece()[numCase][1]] = Piece.NULL;
        }
    }

    /**
     * Supprime la ligne à l'emplacement indiqué (remplace toutes les colonnes par NULL)
     * @param ligne ligne observée
     */
    public void supprimerLigne(int ligne) {
        for (int colonne = 0; colonne < this.largeur; colonne++) {
            this.plateau[ligne][colonne] = Piece.NULL;
        }
    }

    /**
     * Vérifie pour chaque ligne, si elle est remplie et appelle la méthode de suppression supprimerLigne() le cas échéant
     */
    public void suppressionLignesRemplies() {
        // pour chaque ligne, on regarde si elle est remplie
        for (int ligne = 0; ligne < this.hauteur; ligne++) {
            if (ligneRemplie(ligne)) {
                // le cas échéant, on remplace toutes les pièces par Piece.NULL
                supprimerLigne(ligne);
                tomberLignesApresSuppression(ligne);
            }
        }
    }

    /**
     * Regarde la ligne et vérifie si elle est remplie
     * @param ligne ligne observée
     * @return Vrai, si la ligne est remplie.
     * Faux, sinon
     */
    public boolean ligneRemplie(int ligne) {
        for (int colonne = 0; colonne < this.largeur; colonne++) {
            if (this.estVide(ligne, colonne)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Fait tomber les pièces qui sont au-dessus d'une ligne venant d'être supprimée
     * @param ligneSupprimee la ligne qui a été supprimée
     */
    public void tomberLignesApresSuppression(int ligneSupprimee) {
        for (int ligne = ligneSupprimee; ligne >= 0 ; ligne--) {
            for (int colonne = 0; colonne < this.largeur; colonne++) {
                if (ligne != 0) {
                    // On remplit la ligne observée par les pièces de la ligne ligne - 1
                    this.plateau[ligne][colonne] = this.plateau[ligne - 1][colonne];
                } else {
                    // On remplit la colonne 0 (la plus haute) par des pièces NULL
                    this.plateau[0][colonne] = Piece.NULL;
                }
            }
        }
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
