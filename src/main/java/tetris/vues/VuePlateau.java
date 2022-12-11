package tetris.vues;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import org.w3c.dom.ls.LSOutput;
import tetris.TetrisIHM;
import tetris.logique.Plateau;

public class VuePlateau extends GridPane {

    private final Image vide = new Image("file:src/main/resources/img/vide.png");
    private final Image imgS = new Image("file:src/main/resources/img/conteneur/S.png");
    private final Image imgI = new Image("file:src/main/resources/img/conteneur/I.png");
    private final Image imgJ = new Image("file:src/main/resources/img/conteneur/J.png");
    private final Image imgL = new Image("file:src/main/resources/img/conteneur/L.png");
    private final Image imgO = new Image("file:src/main/resources/img/conteneur/O.png");
    private final Image imgT = new Image("file:src/main/resources/img/conteneur/T.png");
    private final Image imgZ = new Image("file:src/main/resources/img/conteneur/Z.png");

    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

    Plateau p;

    public VuePlateau(Plateau p) {
        this.p = p;
    }

    public void mettreAJour() {
        // Effacer le GridPane (effectué à chaque mise à jour)
        this.getChildren().clear();
        System.out.println(primaryScreenBounds.getHeight());
        // Ajoute les pièces dans la grille
        for (int colonne = 0; colonne < p.getPlateau().length; colonne++) {
            for (int ligne = 0; ligne < p.getPlateau()[0].length; ligne++) {
                ImageView imagePiece = new ImageView(vide);

                if (primaryScreenBounds.getWidth() == 1920){
                    imagePiece.setFitHeight((primaryScreenBounds.getHeight()*0.03));
                    imagePiece.setFitWidth((primaryScreenBounds.getHeight()*0.03));
                }else if(primaryScreenBounds.getWidth() == 1280){
                    imagePiece.setFitHeight((primaryScreenBounds.getHeight()*0.046));
                    imagePiece.setFitWidth((primaryScreenBounds.getHeight()*0.046));
                }else if(primaryScreenBounds.getWidth() == 1366){
                    imagePiece.setFitHeight((primaryScreenBounds.getHeight()*0.043));
                    imagePiece.setFitWidth((primaryScreenBounds.getHeight()*0.043));
                }
                String nomPiece = p.getPlateau()[colonne][ligne].getNom();
                switch (nomPiece) {
                    case "S" -> imagePiece.setImage(imgS);
                    case "I" -> imagePiece.setImage(imgI);
                    case "J" -> imagePiece.setImage(imgJ);
                    case "L" -> imagePiece.setImage(imgL);
                    case "O" -> imagePiece.setImage(imgO);
                    case "T" -> imagePiece.setImage(imgT);
                    case "Z" -> imagePiece.setImage(imgZ);
                }
                this.add(imagePiece, ligne, colonne);
            }
        }
    }
}
