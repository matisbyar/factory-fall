import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class testPiece{


//partie user story afficher piece + bouger piece + affiche plateau

@BeforeEach
void init() {
     Plateau plat = new Plateau(10,22);  // creation plateau
}

@Disabled
@Test
void plateauestuneclasse() {
    assertTrue( plat instanceof Plateau);

 
@Test
    pieceaétécréee() {  // test que la piece a bien été crée
        Piece t = new Piece("T"); // creation d'une  piece
    assertTrue( t instanceof Piece);
   }



@test
   pieceestaffiché() {  // test que la piece est bien presente sur le plateau
   p.placerPiece(2, 7, Piece.T); // crée piece sur le plateau
        assertTrue(p.getPlateau().getPiece(2, 7) ); // pas sur 
}



@test
   pieceabienetebougé() { // test qui verifie que la piece qui a bouge a bien bougé au bon endroit
    p.placerPiece(2, 7, Piece.T); // piece de base
    p.placerPiece(2, 6, Piece.T); // on bouge la piece ??
    assertTrue(p.getPlateau().getPiece(2, 6)); // assert que la piece a bien bougé , pas sur que c'est ca 
   }




}
