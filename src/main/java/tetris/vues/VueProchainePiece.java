package tetris.vues;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import tetris.logique.Plateau;

public class VueProchainePiece extends GridPane {

    private final Image vide = new Image("file:src/main/resources/img/vide.png");
    private final Image imgS = new Image("file:src/main/resources/img/S.jpg");
    private final Image imgI = new Image("file:src/main/resources/img/I.jpg");
    private final Image imgJ = new Image("file:src/main/resources/img/J.jpg");
    private final Image imgL = new Image("file:src/main/resources/img/L.jpg");
    private final Image imgO = new Image("file:src/main/resources/img/O.jpg");
    private final Image imgT = new Image("file:src/main/resources/img/T.jpg");
    private final Image imgZ = new Image("file:src/main/resources/img/Z.jpg");

    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

    Plateau p;

    public VueProchainePiece(Plateau p) {
        this.p = p;
    }

    public void mettreAJour() {
        // Effacer le GridPane (effectué à chaque mise à jour)
        this.getChildren().clear();

        // Ajoute les pièces dans la grille
        for (int colonne = 0; colonne < p.getPlateau().length; colonne++) {
            for (int ligne = 0; ligne < p.getPlateau()[0].length; ligne++) {
                ImageView imagePiece = new ImageView(vide);
                imagePiece.setFitHeight((primaryScreenBounds.getHeight()*0.04));
                imagePiece.setFitWidth((primaryScreenBounds.getHeight()*0.04));
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