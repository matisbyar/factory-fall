package tetris.vues;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import tetris.TetrisIHM;
import tetris.singletons.Ressources;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VueControles extends VBox {

    Label titre;
    ImageView flechesDirectionnelles, espace, toucheR, toucheC, toucheP, haut, echap;
    Label flechesDirectionnellesLabel, espaceLabel, toucheRLabel, toucheCLabel, touchePLabel, hautLabel, echapLabel;

    ArrayList<HBox> hBoxes;
    ArrayList<ImageView> imageViews;

    public VueControles() {
        titre = new Label("Contrôles :");

        flechesDirectionnelles = new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("icons/flechesDirectionnelles.png"))));
        espace = new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("icons/espace.png"))));
        toucheR = new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("icons/toucheR.png"))));
        toucheC = new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("icons/toucheC.png"))));
        toucheP = new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("icons/toucheP.png"))));
        haut = new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("icons/haut.png"))));
        echap = new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("icons/echap.png"))));

        flechesDirectionnellesLabel = new Label("Déplacer la pièce");
        espaceLabel = new Label("Descendre la pièce");
        toucheRLabel = new Label("Rotation anti-horaire");
        toucheCLabel = new Label("Sauvegarder la pièce");
        touchePLabel = new Label("Pause");
        hautLabel = new Label("Rotation horaire");
        echapLabel = new Label("Quitter le jeu");

        HBox flechesDirectionnellesHBox = new HBox(flechesDirectionnelles, flechesDirectionnellesLabel);
        HBox espaceHBox = new HBox(espace, espaceLabel);
        HBox toucheRHBox = new HBox(toucheR, toucheRLabel);
        HBox toucheCHBox = new HBox(toucheC, toucheCLabel);
        HBox touchePHBox = new HBox(toucheP, touchePLabel);
        HBox hautHBox = new HBox(haut, hautLabel);
        HBox echapHBox = new HBox(echap, echapLabel);

        hBoxes = new ArrayList<>();
        hBoxes.addAll(List.of(flechesDirectionnellesHBox, espaceHBox, touchePHBox, toucheRHBox, toucheCHBox, hautHBox, echapHBox));

        imageViews = new ArrayList<>();
        imageViews.addAll(List.of(flechesDirectionnelles, espace, toucheR, toucheC, toucheP, haut, echap));

        styliser();

        this.getChildren().addAll(titre, flechesDirectionnellesHBox, espaceHBox, toucheRHBox, toucheCHBox, touchePHBox, hautHBox, echapHBox);
    }

    public void styliser() {
        // VBox
        this.setAlignment(Pos.BOTTOM_LEFT);
        this.setBackground(Background.EMPTY);

        // Titre
        titre.setStyle("-fx-font-weight: bold; -fx-text-fill: #ffffff;");
        titre.setFont(Ressources.getInstance().getPolice(30));
        titre.setTextAlignment(TextAlignment.LEFT);

        // HBoxes
        for (HBox hBox : hBoxes) {
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setSpacing(5);
            hBox.setPrefHeight(75.0);
            hBox.setBackground(Background.EMPTY);

            HBox.setHgrow(hBox.getChildren().get(0), Priority.ALWAYS);

            hBox.getChildren().get(1).setStyle("-fx-text-fill: #ffffff;");
            ((Label) hBox.getChildren().get(1)).setFont(Ressources.getInstance().getPolice(16));
            ((Label) hBox.getChildren().get(1)).setAlignment(Pos.CENTER_RIGHT);
            ((Label) hBox.getChildren().get(1)).setWrapText(true);
        }

        // ImageViews
        for (ImageView imageView : imageViews) {
            imageView.setFitHeight(40.0);
            imageView.setFitWidth(190.0);
            imageView.setPreserveRatio(true);
        }
    }
}