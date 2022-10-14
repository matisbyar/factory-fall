import java.util.Arrays;
//
public class Plateau {
    //On peut définir la hauteur le la longueur que l'on souhaite pour le plateau
    private final int longueur;
    private final int hauteur;
    //Le plateau est une matrice de booleans car cela est plus simple pour le traitement parce qu'on peut directement savoir si une case est prise ou non
    private final boolean[][] plateau;

    public Plateau(int longueur, int hauteur) {
        this.longueur = longueur;
        this.hauteur = hauteur;
        this.plateau = new boolean[hauteur][longueur];
        this.remplirPlateau();
    }

    /**Fonction qui sert à placer un pixel sur le plateau (vide ou non)*/
    public void placerPixel(boolean a, int y, int x) {
        this.plateau[y][x] = a;
    }

    /**Fonction qui initialise le plateau pour qu'il soit vide*/
    private void remplirPlateau() {
        for(int i = 0; this.hauteur > i; ++i) {
            for(int j = 0; this.longueur > j; ++j) {
                this.placerPixel(false, i, j);          //place un pixel vide (état à false)
            }
        }
    }

    /**Fonction permettant d'afficher le plateau dans le terminal (en cas de false il affiche un espace et * dans le cas d'un true)*/
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

    /**Fonction qui permet de savoir si une ligne est supprimable, si elle est pleine,
     la fonction appelle la fonction de suppression de ligne*/
    public void peutSupprimerLigne() {
        boolean lignePleine = false;
        int y = 19;
        while (y < plateau.length && !lignePleine && y != 0) {      //les y < plateau.length && y != 0 servent a ne pas dépasser du plateau
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
    }

    /**Fonction qui permet de supprimer une ligne d'on l'identifiant est fourni et appelle la fonction pour descendre les lignes du dessus*/
    public void supprimerLigne(int y) {
        for (int x = 0; x < plateau[0].length; x++) {
            plateau[y][x] = false;
        }
        int yDebut;
        yDebut = y;
        int i = y;
        while (i < plateau.length) {
            if (descendreLigne(i, yDebut)) {    //si la ligne du dessus est vide alors la fonction s'arrête
                break;
            }
            i--;
        }
    }

    /**Fonction qui permet de descendre une ligne, elle retourne si la ligne du dessus est vide ou non*/
    public boolean descendreLigne(int y, int yDebut) {
        boolean ligneVide = true;
        if (y != yDebut) {                                      //Ce if permet que la fonction ne renvoie pas true dans le premier appel
            for (int x = 0; x < plateau[0].length; x++) {       //Cette boucle for permet de savoir si la ligne du dessus est vide
                if (plateau[y][x]) {
                    ligneVide = false;
                    break;
                }
            }
        } else {
            ligneVide = false;
        }

        for (int x = 0; x < plateau[0].length; x++) {       //Cette boucle descend tous les pixels de la ligne du dessus
            if (y == 0) {
                plateau[y][x] = false;
            }
            plateau[y][x] = plateau[(y-1)][x];
        }
        return ligneVide;
    }

    /**Fonction permettant de vérifier si la pièce donnée en paramètres, aux coordonnées données
     entre en collision avec une autre déjà existante*/
    public boolean collisionPieces(Piece piece, int positionY, int positionX, int rotation){
        switch (piece) {               //Switch case permettant de savoir quelle pièce est placé en paramètre et d'adopter le bon raisonnement
            case I :
                switch (rotation) {    //Switch case permettant de connaitre la rotation de la piece
                    case 0 :
                        //Boucle qui vérifie si chaque case que la pièce peut potentiellement toucher est vide
                        for (int x = positionX; x < (positionX + piece.getPiece()[0].length); x++) {
                            if (plateau[positionY][x]) {    //Si la case est a true alors on renvoie false (on fixe sur l'axe des Y)
                                return false;
                            }
                        }
                        break;
                    case 90 :
                        //Même boucle que la précédente, mais on prend y en paramètre, car la pièce est à la verticale (y décrémente parce qu'on veut placer les pixels au-dessus)
                        for (int y = positionY; y > (positionY - piece.getPiece()[0].length); y--) {
                            if (plateau[y][positionX]) {    //Si la case est a true alors on renvoie false (on fixe sur l'axe des X)
                                return false;
                            }
                        }
                        break;
                    case 180 :
                        for (int x = positionX; x < (positionX + piece.getPiece()[0].length); x++) {
                            if (plateau[(positionY+1)][x]) {    //Le +1 ici sert à décaler la pièce, car d'après la documentation les pièces tournées a 0 et 180° n'ont pas la même positon
                                return false;
                            }
                        }
                        break;
                    case 270 :
                        for (int y = positionY; y > (positionY - piece.getPiece()[0].length); y--) {
                            if (plateau[y][positionX-1]) {  //Même problématique pour le 270 par rapport au 90° que pour le 0 et le 180°
                                return false;
                            }
                        }
                        break;
                }
                break;
            case O :    //Dans ce cas, on ne cherche pas à différentier les angles de rotation, car la pièce a toujours la même forme et position
                //Les boucles imbriquées vérifient la validité du placement sur l'axe X et Y
                for (int y = positionY; y < (positionY + piece.getPiece().length); y++) {
                    for (int x = positionX; x < (positionX + piece.getPiece()[0].length); x++) {
                        if (plateau[y][x]) {
                            return false;
                        }
                    }
                }
                break;
            case T :
                /*Ici le cas est très similaire au deux précédents, la seule différence est que l'on cherche à savoir
                si un pixel de la pièce est a false (dans ce cas, on ne le prend pas en compte) ou a true (dans ce cas, on le prend en compte)
                (tous les ajouts dans les [] sont là pour ajuster graphiquement les pièces ou pour ne pas avoir d'erreurs a l'exécution*/
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
            case S :        //Ici les cas S et Z sont exactement pareil pour le traitement, on peut donc fusionner les cas comme ça
            case Z :
                /*Dans l'ensemble aucune différence fondamentale dans le traitement que dans le cas du T seul
                des ajustements graphiques sont présents ces bouts de codes pourrait être réunis dans une fonction qui serait
                appelée par les trois cas d'utilisation avec seulement de paramètres différents (à voir dans le futur si on a du temps)*/
                switch (rotation) {
                    case 0 :
                        for (int y = positionY; y < (positionY + piece.getPiece().length); y++) {
                            for (int x = positionX; x < (positionX + piece.getPiece()[0].length); x++) {
                                if (piece.getPiece()[(y-positionY)][(x - positionX)]) {
                                    if (plateau[y-1][x-1]) {
                                        return false;
                                    }
                                }
                            }
                        }
                        break;
                    case 90 :
                        for (int x = positionX; x < (positionX + piece.getPiece()[0].length-1); x++) {
                            for (int y = positionY+1; y > (positionY - piece.getPiece().length); y--) {
                                if (piece.getPiece()[(x-positionX)][(positionY-y+1)]) {
                                    if (plateau[y][x]) {
                                        return false;
                                    }
                                }
                            }
                        }
                        break;
                    case 180 :
                        for (int y = positionY; y < (positionY + piece.getPiece().length); y++) {
                            for (int x = positionX; x < (positionX + piece.getPiece()[0].length); x++) {
                                if (piece.getPiece()[(y-positionY)][(x - positionX)]) {
                                    if (plateau[y][x-1]) {
                                        return false;
                                    }
                                }
                            }
                        }
                        break;
                    case 270 :
                        for (int x = positionX; x < (positionX + piece.getPiece()[0].length-1); x++) {
                            for (int y = positionY+1; y > (positionY - piece.getPiece().length); y--) {
                                if (piece.getPiece()[(x-positionX)][(positionY-y+1)]) {
                                    if (plateau[y][x-1]) {
                                        return false;
                                    }
                                }
                            }
                        }
                        break;
                }
                break;
            case J :
                switch (rotation) {
                    case 0 :
                        for (int y = positionY; y < (positionY + piece.getPiece().length); y++) {
                            for (int x = positionX; x < (positionX + piece.getPiece()[0].length); x++) {
                                if (piece.getPiece()[(y - positionY)][(x - positionX)]) {
                                    if (plateau[y-1][x]) {
                                        return false;
                                    }
                                }
                            }
                        }
                        break;
                    case 90 :
                        int i = 0;
                        for (int x = positionY; x < (positionY + piece.getPiece().length); x++) {
                            for (int y = positionX; y < (positionX + piece.getPiece()[0].length); y++) {
                                /*le if et le else servent à de l'ajustement graphique précis et unique à ce cas,
                                car le placement de la ligne d'étoile est très différente de la colonne, ce code
                                peut largement être amélioré, mais il fonctionne, il faut le voir comme un premier jet du a un raz le bol
                                comme tous les autres ifs pour un cas très précis des cas suivants*/
                                if (x-positionY == 0) { //Ce if sert à placer le pixel de la ligne {true, false, false} en bas et le premier de la ligne entièrement en true
                                    if (piece.getPiece()[x-positionY][y-positionX]) {
                                        if (plateau[x-1][y+1]) {
                                            return false;
                                        }
                                    }
                                } else {
                                    switch (y - positionX) {
                                        case 0, 1, 2 -> {
                                            if (plateau[x - i][y - i]) {
                                                return false;
                                            }
                                        }
                                    }
                                    i++;
                                }
                            }
                        }
                        break;
                    case 180 :
                        for (int y = positionY+1; y > (positionY - piece.getPiece().length+1); y--) {
                            for (int x = positionX+2; x > (positionX - piece.getPiece()[0].length+2); x--) {
                                if (piece.getPiece()[(y - positionY)][(x - positionX)]) {
                                    if (y-positionY == 0 && x - positionX == 0) {   //Ce if sert à placer le seul pixel à true de la ligne {true, false, false}
                                        if (plateau[y+1][x+1]) {
                                            return false;
                                        }
                                    } else {
                                        if (plateau[y-1][x-1]) {
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case 270 :
                        int z = 0;
                        for (int x = positionY; x < (positionY + piece.getPiece().length); x++) {
                            for (int y = positionX; y < (positionX + piece.getPiece()[0].length); y++) {
                                if (x-positionY == 0) { //Ce if sert à placer le pixel de la ligne {true, false, false} en bas et le premier de la ligne entièrement en true
                                    if (piece.getPiece()[x-positionY][y-positionX]) {
                                        if (plateau[x+1][y-1]) {
                                            return false;
                                        }
                                    }
                                } else {
                                    switch (y - positionX) {
                                        case 0, 1, 2 -> {
                                            if (plateau[x - z][y - z]) {
                                                return false;
                                            }
                                        }
                                    }
                                    z++;
                                }
                            }
                        }
                        break;
                }
                break;
            case L :        //Le raisonnement ici st similaire à celui pour le J
                switch (rotation) {
                    case 0 :
                        for (int y = positionY; y < (positionY + piece.getPiece().length); y++) {
                            for (int x = positionX; x < (positionX + piece.getPiece()[0].length); x++) {
                                if (piece.getPiece()[(y - positionY)][(x - positionX)]) {
                                    if (plateau[y-1][x-1]) {
                                        return false;
                                    }
                                }
                            }
                        }
                        break;
                    case 90 :
                        int i = 0;
                        for (int x = positionY; x < (positionY + piece.getPiece().length); x++) {
                            for (int y = positionX; y < (positionX + piece.getPiece()[0].length); y++) {
                                if (x-positionY == 0) {
                                    if (piece.getPiece()[x-positionY][y-positionX]) {
                                        if (plateau[x+1][y-1]) {
                                            return false;
                                        }
                                    }
                                } else {
                                    switch (y - positionX) {
                                        case 0, 1, 2 -> {
                                            if (plateau[x - i][y - i]) {
                                                return false;
                                            }
                                        }
                                    }
                                    i++;
                                }
                            }
                        }
                        break;
                    case 180 :
                        for (int y = positionY+1; y > (positionY - piece.getPiece().length+1); y--) {
                            for (int x = positionX+2; x > (positionX - piece.getPiece()[0].length+2); x--) {
                                if (piece.getPiece()[(y - positionY)][(x - positionX)]) {
                                    if (y-positionY == 0 && x - positionX == 2) {
                                        if (plateau[y+1][x-3]) {
                                            return false;
                                        }
                                    } else {
                                        if (plateau[y-1][x-1]) {
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case 270 :
                        int z = 0;
                        for (int x = positionY; x < (positionY + piece.getPiece().length); x++) {
                            for (int y = positionX; y < (positionX + piece.getPiece()[0].length); y++) {
                                if (x-positionY == 0) {
                                    if (piece.getPiece()[x-positionY][y-positionX]) {
                                        if (plateau[x-1][y-3]) {
                                            return false;
                                        }
                                    }
                                } else {
                                    switch (y - positionX) {
                                        case 0, 1, 2 -> {
                                            if (plateau[x - z][y - z]) {
                                                return false;
                                            }
                                        }
                                    }
                                    z++;
                                }
                            }
                        }
                        break;
                }
                break;
        }
        return true;
    }
}
