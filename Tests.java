
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.testng.Assert.*;


public class Tests {
    @Test
    public void testDeplacerPiecesGauche(){
        Jeu.nouvellePieceActuelle();
        System.out.println("Colonne de spawn : " + Jeu.colonneActuelle);
        System.out.println("Flèche de gauche est actionnée !");
        Jeu.deplacerPieceActuelle(Jeu.colonneActuelle-1);
        System.out.println("Colonne déplacé : " + Jeu.colonneActuelle);
        assertEquals(Jeu.colonneActuelle, 3);
    }

    @Test
    public void testDeplacerPiecesDroite(){
        Jeu.nouvellePieceActuelle();
        System.out.println("Colonne de spawn : " + Jeu.colonneActuelle);
        System.out.println("Flèche de droite est actionnée !");
        Jeu.deplacerPieceActuelle(Jeu.colonneActuelle+1);
        System.out.println("Colonne déplacé : " + Jeu.colonneActuelle);
        assertEquals(Jeu.colonneActuelle, 5);
    }

    @Test
    public void testTomberPiece(){
        Jeu.nouvellePieceActuelle();
        String nomPieceEnCours = Jeu.pieceActuelle.getNom();
        System.out.println("Nom de la piece qui a spawn (avant le drop): " + nomPieceEnCours);
        System.out.println("Flèche du bas est actionnée !");
        Jeu.tomberPieceActuelle();
        System.out.println("Nom de la piece qui est en bas (apres le drop): " + Jeu.p.getPieceNom(21, 4));
        assertEquals(Jeu.colonneActuelle, 4);
        assertEquals(Jeu.p.getPieceNom(21, 4), nomPieceEnCours);
    }
}
