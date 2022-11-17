package tetris;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import tetris.logique.Jeu;
import tetris.logique.Joueur;
import tetris.logique.Plateau;

import java.awt.*;
import java.io.File;
import java.util.Objects;


public class TetrisIHM extends Application {

    private final Joueur j = Jeu.j;
    private final Plateau p = Jeu.p;

    GridPane gp = new GridPane();

    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = new Stage();
        demarrerPartie();
    }

    public void demarrerPartie() {
        //VuePlateau plateau = new VuePlateau();
        Scene scene = new Scene(afficherplateau());
        primaryStage.setScene(scene);
        primaryStage.show();
        Jeu.nouvellePieceActuelle();
        afficherplateau();
        primaryStage.show();
    }

    public GridPane afficherplateau() {
        for (int i = 0; i < p.getPlateau().length; i++) {
            for (int y = 0; y < p.getPlateau()[0].length; y++) {
                TextField tf = new TextField();
                tf.setPrefHeight(45);
                tf.setPrefWidth(45);
                tf.setAlignment(Pos.CENTER);
                tf.setEditable(false);
                String s = p.getPlateau()[i][y].getNom();
                switch (s) {
                    case "S" :
                        tf.setStyle("-fx-background-color: limegreen;");
                        break;
                    case "I" :
                        tf.setStyle("-fx-background-color: #00bbff;");
                        break;
                    case "J" :
                        tf.setStyle("-fx-background-color: blue;");
                        break;
                    case "L" :
                        tf.setStyle("-fx-background-color: orange;");
                        break;
                    case "O" :
                        tf.setStyle("-fx-background-color: yellow;");
                        break;
                    case "T" :
                        tf.setStyle("-fx-background-color: purple;");
                        break;
                    case "Z" :
                        tf.setStyle("-fx-background-color: red;");
                        break;
                }
                tf.setText(s);
                //tf.setStyle("-fx-background-color: limegreen;");
                GridPane.setRowIndex(tf,i);
                GridPane.setColumnIndex(tf,y);
                gp.getChildren().add(tf);
            }
        }
        return gp;
    }

    private String test(String path) {
        return Objects.requireNonNull(getClass().getResource(path)).toString();
    }
}
