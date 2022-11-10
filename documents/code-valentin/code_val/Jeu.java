/**
 * La classe Jeu instancie tous les éléments du jeu (Grille, Briques, ...) afin d'assurer le bon fonctionnement du jeu.
 */
/*
public class Jeu {
    static Plateau p = new Plateau(10, 20); //Création d'un objet plateau
    public static void main(String[] args) {
        /**C'est OK (pour tester sortez juste un bloc et l'exécuter)

         placerPiecesRotation(0, Piece.I, 19, 3);
         placerPiecesRotation(90, Piece.I, 19, 9);
         placerPiecesRotation(180, Piece.I, 16, 3);
         placerPiecesRotation(270, Piece.I, 19, 1);

         placerPiecesRotation(0, Piece.S, 6,1);
         placerPiecesRotation(90, Piece.S, 1,0);
         placerPiecesRotation(180, Piece.S, 6,8);
         placerPiecesRotation(270, Piece.S, 1,9);

         placerPiecesRotation(0, Piece.Z, 19, 1);
         placerPiecesRotation(90, Piece.Z, 10,0);
         placerPiecesRotation(180, Piece.Z, 18, 8);
         placerPiecesRotation(270, Piece.Z, 10,9);

         placerPiecesRotation(0, Piece.J, 6, 1);
         placerPiecesRotation(90, Piece.J, 1, 0);
         placerPiecesRotation(180, Piece.J, 6, 8);
         placerPiecesRotation(270, Piece.J, 1, 9);

         placerPiecesRotation(0, Piece.L, 18, 1);
         placerPiecesRotation(90, Piece.L, 10, 0);
         placerPiecesRotation(180, Piece.L, 18, 8);
         placerPiecesRotation(270, Piece.L, 10, 9);

         placerPiecesRotation(0, Piece.T, 18,1);
         placerPiecesRotation(90, Piece.T, 13,0);
         placerPiecesRotation(180, Piece.T, 18,7);
         placerPiecesRotation(270, Piece.T, 13,9);

         placerPiecesRotation(0, Piece.J, 18, 1);
         placerPiecesRotation(90, Piece.J, 10, 0);
         placerPiecesRotation(180, Piece.J, 18, 8);
         placerPiecesRotation(270, Piece.J, 10, 9);

         placerPiecesRotation(0, Piece.O, 4, 4);

         p.afficherPlateau();
         */

        /**Partie pour tester la suppression de ligne
        placerPiecesRotation(0, Piece.I, 19, 3);
        placerPiecesRotation(90, Piece.I, 19, 9);
        placerPiecesRotation(180, Piece.I, 16, 3);
        placerPiecesRotation(270, Piece.I, 19, 1);
        p.afficherPlateau();
        p.supprimerLigne(19);
        p.afficherPlateau();
        placerPiecesRotation(0, Piece.O, 18, 1);
        placerPiecesRotation(0, Piece.O, 18, 7);
        p.afficherPlateau();
        */
        /*
    }
*/
    /**Fonction qui sert à placer les pièces sur le plateau les angles de rotations possibles sont 0, 90, 180 et 270*/
/*
    public static void placerPiecesRotation(int rotation, Piece piece, int yDebut, int xDebut) {
        switch (piece) {
            case I :
                for (int i = 0; i < piece.getPiece()[0].length; i++) {
                    switch (rotation) {
                        case 0 -> p.placerPixel(true, yDebut, xDebut + i);  //L'incrémentation se fait sur l'axe des X
                        case 90 -> p.placerPixel(true, yDebut - i, xDebut); //L'incrémentation se fait sur l'axe des Y
                        case 180 -> p.placerPixel(true, yDebut + 1, xDebut + i);    //L'incrémentation se fait sur l'axe des X
                        case 270 -> p.placerPixel(true, yDebut - i, xDebut - 1);    //L'incrémentation se fait sur l'axe des Y
                        default -> System.out.println("Mauvais angle de rotation !");
                    }
                }
                break;
            case S :    //le cas S et le cas Z sont similaires, ils seront donc traités au même endroit
            case Z:
                gestionRotationPieces("S ou Z", rotation, yDebut, xDebut, piece); //appel de la fonction qui gère les S, Z, J, L et T
                break;
            case L :    //le cas L et le cas J sont similaires, ils seront donc traités au même endroit
            case J:
                gestionRotationPieces("L ou J", rotation, yDebut, xDebut, piece); //appel de la fonction qui gère les S, Z, J, L et T
                break;
            case O:         //Cette pièce est toujours la même peu importe le sens de rotation, on ne le prend donc pas en compte
                for (int i = 0; i < piece.getPiece().length; i++) {
                    for (int j = 0; j < piece.getPiece()[0].length; j++) {
                        p.placerPixel(true, yDebut + i, xDebut + j);
                    }
                }
                break;
            case T:
                gestionRotationPieces("T", rotation, yDebut, xDebut, piece);  //appel de la fonction qui gère les S, Z, J, L et T
                break;
            default:
                System.out.println("Mauvaise pièce entrée !");
                break;
        }
        p.peutSupprimerLigne();
    }
*/
    /**Fonction tampon qui gère les S, Z, J, L et T séparément pour une meilleure lisibilité
     (pour toute la fonction les -1 et les +1 servent à faire de l'ajustement graphique pour que chaque pixel soit bien placé)*/
/*
    private static void gestionRotationPieces(String nomPiece, int rotation, int yDebut, int xDebut, Piece piece) {
        for (int i = 0; i < piece.getPiece().length; i++) {
            for (int j = 0; j < piece.getPiece()[0].length; j++) {
                if (piece.getPiece()[i][j]) {
                    switch (nomPiece) {
                        case "S ou Z":
                            switch (rotation) {
                                case 0 -> placerSouZouJouL("H", yDebut - 1, xDebut - 1, i, j);
                                case 90 -> placerSouZouJouL("V", yDebut - 1, xDebut, -i + 1, j);
                                case 180 -> placerSouZouJouL("H", yDebut - 1, xDebut - 1, i + 1, j);
                                case 270 -> placerSouZouJouL("V", yDebut - 1, xDebut, -i, j);
                            }
                            break;
                        case "L ou J":
                            switch (rotation) {
                                case 0 -> placerSouZouJouL("H", yDebut, xDebut - 1, i - 1, j);
                                case 90 -> placerSouZouJouL("V", yDebut, xDebut, -i + 1, j - 1);
                                case 180 -> placerSouZouJouL("H", yDebut, xDebut - 1, -i + 1, -j + 2);
                                case 270 -> placerSouZouJouL("V", yDebut, xDebut, i - 1, -j + 1);
                            }
                            break;
                        case "T" :
                            switch (rotation) {
                                case 0 -> p.placerPixel(true, yDebut + i - 1, xDebut + j - 1);
                                case 90 -> p.placerPixel(true, yDebut + j - 1, xDebut - i + 1);
                                case 180 -> p.placerPixel(true, yDebut - i + 1, xDebut + j);
                                case 270 -> p.placerPixel(true, yDebut + j - 1, xDebut + i - 1);
                            }
                            break;
                    }
                }
            }
        }
    }
*/
    /**Fonction qui traite les cas ou la pièce est un Z, un S, un J ou un L (elle existe pour éviter la duplication de code*/
/*
    public static void placerSouZouJouL(String sens, int yDebut, int xDebut, int i, int j) {
        if (sens.equals("V")) {         //Si le sens est Vertical alors on inverse l'axe de X et des Y
            p.placerPixel(true, yDebut + j, xDebut + i);
        } else if (sens.equals("H")) {  //Sinon on le place normalement
            p.placerPixel(true, yDebut + i, xDebut + j);
        }
    }

}
*/