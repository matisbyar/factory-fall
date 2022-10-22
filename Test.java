import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JFrame;

public class Test {
    static Plateau p = new Plateau(10, 20);

    private static final Random PIECE = new Random();

    public static Piece randomPiece()  {
        Piece[] directions = Piece.values();
        return directions[PIECE.nextInt(directions.length)];
    }

    public static void main(String[] args) {
        p.afficherPlateau();

        Piece.I.afficherPiece();
        System.out.println("----");
        Piece.O.afficherPiece();
        System.out.println("----");
        Piece.T.afficherPiece();
        System.out.println("----");
        Piece.S.afficherPiece();
        System.out.println("----");
        Piece.Z.afficherPiece();
        System.out.println("----");
        Piece.J.afficherPiece();
        System.out.println("----");
        Piece.L.afficherPiece();


        p.afficherPlateau();
        JFrame myJFrame = new JFrame();

        /**C'est OK**/



         myJFrame.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
         int keyCode = e.getKeyCode();
         if (keyCode == KeyEvent.VK_DOWN) {
         System.out.println("fleshe du bas est actionnée!");
         /**Bouger les pieces vers le bas**/
         }
         else if (keyCode == KeyEvent.VK_LEFT) {
         System.out.println("fleshe de gauche est actionnée!");
         /**Bouger les pieces vers la gauche**/
         }
         else if (keyCode == KeyEvent.VK_RIGHT) {
         System.out.println("fleshe de droite est actionnée!");
         /**Bouger les pieces vers la droite**/
         }
         }
         });
        myJFrame.setVisible(true);
        randomPiece().afficherPiece();

    /**
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
        //placerPiecesRotation(90, Piece.O, 17, 0);
        //placerPiecesRotation(90, Piece.I, 15, 4);
        placerPiecesRotation(270, Piece.L, 1, 1);


        //System.out.println(p.collisionEntrePieces(Piece.L, 15, 1, 180));
        //p.afficherPlateau();

    }

    public static void placerPiecesRotation(int rotation, Piece piece, int yDebut, int xDebut) {
        switch (piece) {
            case I :
                for (int i = 0; i < piece.getPiece()[0].length; i++) {
                    switch (rotation) {
                        case 0 -> p.placerPixel(true, yDebut, xDebut + i);
                        case 90 -> p.placerPixel(true, yDebut - i, xDebut);
                        case 180 -> p.placerPixel(true, yDebut + 1, xDebut + i);
                        case 270 -> p.placerPixel(true, yDebut - i, xDebut - 1);
                        default -> System.out.println("Mauvais angle de rotation !");
                    }
                }
                break;
            case S :
            case Z:
                gestionRotationPieces("S ou Z", rotation, yDebut, xDebut, piece);
                break;
            case L :
            case J:
                gestionRotationPieces("L ou J", rotation, yDebut, xDebut, piece);
                break;
            case O:
                for (int i = 0; i < piece.getPiece().length; i++) {
                    for (int j = 0; j < piece.getPiece()[0].length; j++) {
                        p.placerPixel(true, yDebut + i, xDebut + j);
                    }
                }
                break;
            case T:
                gestionRotationPieces("T", rotation, yDebut, xDebut, piece);
                break;
            default:
                System.out.println("Mauvaise pièce entrée !");
                break;
        }
    }

    private static void gestionRotationPieces(String nomPiece, int rotation, int yDebut, int xDebut, Piece piece) {
        for (int i = 0; i < piece.getPiece().length; i++) {
            for (int j = 0; j < piece.getPiece()[0].length; j++) {
                if (piece.getPiece()[i][j]) {
                    switch (nomPiece) {
                        case "S ou Z":
                            switch (rotation) {
                                case 0 -> placerSouZ("H", piece, yDebut - 1, xDebut - 1, i, j);
                                case 90 -> placerSouZ("V", piece, yDebut - 1, xDebut, -i + 1, j);
                                case 180 -> placerSouZ("H", piece, yDebut - 1, xDebut - 1, i + 1, j);
                                case 270 -> placerSouZ("V", piece, yDebut - 1, xDebut, -i, j);
                            }
                            break;
                        case "L ou J":
                            switch (rotation) {
                                case 0 -> placerJouL("H", piece, yDebut, xDebut - 1, i - 1, j);
                                case 90 -> placerJouL("V", piece, yDebut, xDebut, -i + 1, j - 1);
                                case 180 -> placerJouL("H", piece, yDebut, xDebut - 1, -i + 1, -j + 2);
                                case 270 -> placerJouL("V", piece, yDebut, xDebut, i - 1, -j + 1);
                            }
                            break;
                        case "T" :
                            switch (rotation) {
                                case 0 -> p.placerPixel(true, yDebut + i - 1, xDebut + j - 1);
                                case 90 -> p.placerPixel(true, yDebut + j - 1, xDebut - i + 1);
                                case 180 -> p.placerPixel(true, yDebut - i + 1, xDebut + j-1);
                                case 270 -> p.placerPixel(true, yDebut + j - 1, xDebut + i - 1);
                            }
                            break;
                    }
                }
            }
        }
    }

    public static void placerSouZ(String sens, Piece piece, int yDebut, int xDebut, int i, int j) {
        if (sens.equals("V")) {
            p.placerPixel(true, yDebut + j, xDebut + i);
        } else if (sens.equals("H")) {
            p.placerPixel(true, yDebut + i, xDebut + j);
        }
    }

    public static void placerJouL(String sens, Piece piece, int yDebut, int xDebut, int i, int j) {
        if (sens.equals("V")) {
            p.placerPixel(true, yDebut + j, xDebut + i);
        } else if (sens.equals("H")) {
            p.placerPixel(true, yDebut + i, xDebut + j);
        }
    }

}





/********************************************************************************Ancien Code qui marche mais DÉGUEULASSE !!!!******************************************************************************************/
/*
public static void placerPiecesRotation(int rotation, Piece piece, int yDebut, int xDebut) {
        if (piece.equals(Piece.I)) {
            if (rotation == 90) {
                for (int i = 0; i < piece.getPiece()[0].length; i++) {
                    p.placerPixel(true, yDebut - i, xDebut);
                }
            } else if (rotation == 0) {
                for (int i = 0; i < piece.getPiece()[0].length; i++) {
                    p.placerPixel(true, yDebut, xDebut + i);
                }
            }else if (rotation == 180) {
                for (int i = 0; i < piece.getPiece()[0].length; i++) {
                    p.placerPixel(true, yDebut + 1, xDebut + i);
                }
            }else if (rotation == 270) {
                for (int i = 0; i < piece.getPiece()[0].length; i++) {
                    p.placerPixel(true, yDebut - i, xDebut - 1);
                }
            }
        }else {

            if (piece.equals(Piece.S) || piece.equals(Piece.Z)) {
                for (int i = 0; i < piece.getPiece().length; i++) {
                    for (int j = 0; j < piece.getPiece()[0].length; j++) {
                        if (piece.getPiece()[i][j]) {
                            if (rotation == 0) {
                                placerSouZ("H", piece, yDebut-1, xDebut-1, i, j);
                            } else if (rotation == 90) {
                                placerSouZ("V", piece, yDebut-1, xDebut, -i+1, j);
                            } else if (rotation == 180) {
                                placerSouZ("H", piece, yDebut-1, xDebut-1, i + 1, j);
                            } else if (rotation == 270) {
                                placerSouZ("V", piece, yDebut-1, xDebut, -i, j);
                            }
                        }
                    }
                }
            } else if (piece.equals(Piece.L) || piece.equals(Piece.J)) {
                for (int i = 0; i < piece.getPiece().length; i++) {
                    for (int j = 0; j < piece.getPiece()[0].length; j++) {
                        if (piece.getPiece()[i][j]) {
                            if (rotation == 0) {
                                placerJouL("H", piece, yDebut, xDebut-1, i-1, j);
                            } else if (rotation == 90) {
                                placerJouL("V", piece, yDebut, xDebut, -i+1, j-1);
                            } else if (rotation == 180) {
                                placerJouL("H", piece, yDebut, xDebut-1, -i+1, -j+2);
                            } else if (rotation == 270) {
                                placerJouL("V", piece, yDebut, xDebut, i-1, -j+1);
                            }
                        }
                    }
                }
            } else if (piece.equals(Piece.O)) {
                for (int i = 0; i < piece.getPiece().length; i++) {
                    for (int j = 0; j < piece.getPiece()[0].length; j++) {
                        p.placerPixel(true, yDebut + i, xDebut + j);
                    }
                }
            } else {

                for (int i = 0; i < piece.getPiece().length; i++) {
                    for (int j = 0; j < piece.getPiece()[0].length; j++) {
                        if (piece.getPiece()[i][j]) {
                            if (rotation == 90) {
                                p.placerPixel(true, yDebut + j-1, xDebut - i+1);
                            } else if (rotation == 0) {
                                p.placerPixel(true, yDebut + i-1, xDebut + j-1);
                            } else if (rotation == 180) {
                                p.placerPixel(true, yDebut - i+1, xDebut + j);
                            } else if (rotation == 270) {
                                p.placerPixel(true, yDebut + j-1, xDebut + i-1);
                            }
                        }
                    }
                }
            }
        }
}*/