package tetris.vues;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import tetris.logique.Plateau;

public class VueProchainePiece extends GridPane {

    private String dossierImg = "conteneur";
    private final Image vide = new Image("file:src/main/resources/img/vide.png");
    private final Image imgS;
    private final Image imgI;
    private final Image imgJ;
    private final Image imgL;
    private final Image imgO;
    private final Image imgT;
    private final Image imgZ;

    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

    Plateau p;

    public VueProchainePiece(Plateau p, String dossierImg) {
        this.p = p;
        this.dossierImg = dossierImg;
        imgS = new Image("file:src/main/resources/img/" + dossierImg + "/S.jpg");
        imgI = new Image("file:src/main/resources/img/" + dossierImg + "/I.jpg");
        imgJ = new Image("file:src/main/resources/img/" + dossierImg + "/J.jpg");
        imgL = new Image("file:src/main/resources/img/" + dossierImg + "/L.jpg");
        imgO = new Image("file:src/main/resources/img/" + dossierImg + "/O.jpg");
        imgT = new Image("file:src/main/resources/img/" + dossierImg + "/T.jpg");
        imgZ = new Image("file:src/main/resources/img/" + dossierImg + "/Z.jpg");
    }

    public void mettreAJour() {
        // Effacer le GridPane (effectué à chaque mise à jour)
        this.getChildren().clear();

        // Ajoute les pièces dans la grille
        for (int colonne = 0; colonne < p.getPlateau().length; colonne++) {
            for (int ligne = 0; ligne < p.getPlateau()[0].length; ligne++) {
                ImageView imagePiece = new ImageView(vide);
                imagePiece.setFitHeight((primaryScreenBounds.getHeight()*0.03));
                imagePiece.setFitWidth((primaryScreenBounds.getHeight()*0.03));
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
