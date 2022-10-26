
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.testng.Assert.*;


public class Tests {

    void clear(){
        Jeu.p.remplirTableau();
        Jeu.nouvellePieceActuelle();
    }

    @Test
    public void TestTerminerjeu(){
        Jeu.p.remplirTableau();
        jeu.p.rempirligne(5) // on ajoute une colone remplie pour avoir game over desuite
        Jeu.nouvellePieceActuelle();
        Jeu.jouerTour();
        assertFalse(Jeu.jeuEnCours);
    }


    @disabled
    @Test
    public void TestTerminerjeuManuelement(){
        Jeu.p.remplirTableau();
        jeu.jeuEnCours =false; // simulation de on apuie sur echap pour quité , je vous pas comment faire
        assertFalse(Jeu.jeuEnCours);
    }



    @Test
    public void testDeplacerPiecesGauche(){
        clear();
        System.out.println("Colonne de spawn : " + Jeu.colonneActuelle);
        System.out.println("Flèche de gauche est actionnée !");
        Jeu.deplacerPieceActuelle(Jeu.colonneActuelle-1);
        System.out.println("Colonne déplacé : " + Jeu.colonneActuelle);
        assertEquals(Jeu.colonneActuelle, 3);
    }

    @Test
    public void testDeplacerPiecesDroite(){
        clear();
        System.out.println("Colonne de spawn : " + Jeu.colonneActuelle);
        System.out.println("Flèche de droite est actionnée !");
        Jeu.deplacerPieceActuelle(Jeu.colonneActuelle+1);
        System.out.println("Colonne déplacé : " + Jeu.colonneActuelle);
        assertEquals(Jeu.colonneActuelle, 5);
    }

    @Test
    public void testTomberPiece(){
        clear();
        String nomPieceEnCours = Jeu.pieceActuelle.getNom();
        System.out.println("Nom de la piece qui a spawn (avant le drop): " + nomPieceEnCours);
        System.out.println("Flèche du bas est actionnée !");
        Jeu.tomberPieceActuelle();
        System.out.println("Nom de la piece qui est en bas (apres le drop): " + Jeu.p.getPieceNom(21, 4));
        assertEquals(Jeu.colonneActuelle, 4);
        assertEquals(Jeu.p.getPieceNom(21, 4), nomPieceEnCours);
    }

    @Test
    public void testTomberPieceAvecPieceDejaEnBas(){
        clear();
        Jeu.tomberPieceActuelle();
        String nomPieceEnCours = Jeu.pieceActuelle.getNom();
        String nomPieceTombé = Jeu.p.getPieceNom(21, 4);
        System.out.println("Nom de la piece qui est deja tombée : " + nomPieceTombé);
        System.out.println("Flèche du bas est actionnée !");
        Jeu.tomberPieceActuelle();
        System.out.println("Nom de la piece qui est tombée au dessus (apres le drop): " + Jeu.p.getPieceNom(21 - 2, 4));
        assertEquals(Jeu.colonneActuelle, 4);
        /** La fonction assertNotEquals sert a rien, elle marche pas dans le cas ou le randomPiece nous affiche 2 fois la meme piece, dans ce cas les pieces l'une sur l'autre ont le meme nom*/
        //assertNotEquals(Jeu.p.getPieceNom(21 - 2, 4), nomPieceTombé);
        assertEquals(Jeu.p.getPieceNom(21 - 2, 4), nomPieceEnCours);
    }

    @Test
    public void testPieceExiste() {
        clear();
        assertNotEquals(Jeu.pieceActuelle, Piece.NULL);

    }

    @Test
    public void testPieceAffichee() {
        Jeu.p.remplirTableau();
        boolean estVide = true;
        for (int i = 0; i<Jeu.p.getPlateau().length; i++) {
            for (int j = 0; j<Jeu.p.getPlateau()[0].length; j++) {
                if (Jeu.p.getPlateau()[i][j]!=Piece.NULL) {
                    estVide = false;
                    break;
                }
            }
        }
        assertTrue(estVide);
        Jeu.nouvellePieceActuelle();

        for (int i = 0; i<Jeu.p.getPlateau().length; i++) {
            for (int j = 0; j<Jeu.p.getPlateau()[0].length; j++) {
                if (Jeu.p.getPlateau()[i][j]!=Piece.NULL) {
                    estVide = false;
                    break;
                }
            }
        }
        assertFalse(estVide);
    }

    /*@Test
    public void testPlateauExiste() {
        clear();
        Plateau p2 = new Plateau(10,22);
        assertNotEquals(Jeu.p, p2);
    }

    @Test
    public void testPlateauAffichee() {
        Plateau courant = new Plateau(10, 22);
        boolean estVide = true;
        for (int i = 0; i<Jeu.p.getPlateau().length; i++) {
            for (int j = 0; j<Jeu.p.getPlateau()[0].length; j++) {
                if (Jeu.p.getPlateau()[i][j] != courant.getPlateau()[i][j]) {
                    estVide = false;
                    break;
                }
            }
        }
        assertTrue(estVide);
    }*/

    @Test
    public void testJeuQuiLance() throws IOException {
        System.out.println("main");
        String[] args = null;
        final InputStream original = System.in;
        final FileInputStream fips = new FileInputStream(new File("Jeu.java"));
        System.setIn(fips);
        Jeu.main(args);
        System.setIn(original);
    }
}