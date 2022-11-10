/**
 * La classe **enum** Piece contient toutes les formes de pièces. Sa nature enum est un choix qui nous permet de créer
 * plusieurs catégories de pièces.
 */
    /*
public enum Piece {
    /**Définition des instances de la classe, chaque pièce est définie comme une matrice de booleans pour plus de cohérence avec le plateau,
     dans le futur nous pourront également ajouter un attribut couleur à chaque pièce pour l'interface graphique.
     Chaque objet est créé puis initialisé dans cette classe, car c'est la particularité du type enum*/
/*
    I(new boolean[][]{{true, true, true, true}}, "I"),
    O(new boolean[][]{{true, true}, {true, true}}, "O"),
    T(new boolean[][]{{false, true, false}, {true, true, true}}, "T"),
    S(new boolean[][]{{false, true, true}, {true, true, false}}, "S"),
    Z(new boolean[][]{{true, true, false}, {false, true, true}}, "Z"),
    J(new boolean[][]{{true, false, false}, {true, true, true}}, "J"),
    L(new boolean[][]{{false, false, true}, {true, true, true}}, "L");

    private final String nom;
    private final boolean[][] piece;

    /**Constructeur de la classe*/
/*
    Piece(boolean[][] piece, String nom) {
        this.piece = piece;
        this.nom = nom;
    }

    /**Retourne la matrice de la pièce*/
/*
    public boolean[][] getPiece() {
        return this.piece;
    }

    /**Fonction qui permet d'afficher une pièce (utile pour du debug)*/
/*
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
    /**Retourne le nom de la pièce*/
/*
    public String getNom() {
        return nom;
    }
}
 */
