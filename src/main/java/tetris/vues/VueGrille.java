package tetris.vues;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import tetris.TetrisIHM;
import tetris.logique.Plateau;
import tetris.logique.Preferences;

import java.util.Objects;

public class VueGrille extends GridPane {

    String dossierImg;
    Image vide = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/vide.png")));
    Image vide_clair = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/vide_clair.png")));
    final Image imgS, imgI, imgJ, imgL, imgO, imgT, imgZ;

    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

    Plateau p;

    public VueGrille(Plateau p) {
        this.p = p;
        this.dossierImg = Preferences.getInstance().getStylePiece();
        imgS = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/" + dossierImg + "/S.jpg")));
        imgI = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/" + dossierImg + "/I.jpg")));
        imgJ = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/" + dossierImg + "/J.jpg")));
        imgL = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/" + dossierImg + "/L.jpg")));
        imgO = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/" + dossierImg + "/O.jpg")));
        imgT = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/" + dossierImg + "/T.jpg")));
        imgZ = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/" + dossierImg + "/Z.jpg")));
    }

    public void initialiser() {
        // Effacer le GridPane (effectué à chaque mise à jour)
        this.getChildren().clear();

        // Ajoute les pièces vides dans la grille
        for (int colonne = 0; colonne < p.getPlateau().length; colonne++) {
            for (int ligne = 0; ligne < p.getPlateau()[0].length; ligne++) {
                ImageView imagePiece = getClass() == VuePieceExterieur.class ? new ImageView() : ligne % 2 == 0 ? new ImageView(vide) : new ImageView(vide_clair);

                imagePiece.setFitHeight((primaryScreenBounds.getHeight() * 0.03));
                imagePiece.setFitWidth((primaryScreenBounds.getHeight() * 0.03));

                this.add(imagePiece, ligne, colonne);
            }
        }
    }

    public void mettreAJour() {
        // Effacer le GridPane (effectué à chaque mise à jour)
        this.getChildren().clear();

        // Ajoute les pièces dans la grille
        for (int colonne = 0; colonne < p.getPlateau().length; colonne++) {
            for (int ligne = 0; ligne < p.getPlateau()[0].length; ligne++) {
                ImageView imagePiece = getClass() == VuePieceExterieur.class ? new ImageView() : ligne % 2 == 0 ? new ImageView(vide) : new ImageView(vide_clair);

                imagePiece.setFitHeight((primaryScreenBounds.getHeight() * 0.03));
                imagePiece.setFitWidth((primaryScreenBounds.getHeight() * 0.03));

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
