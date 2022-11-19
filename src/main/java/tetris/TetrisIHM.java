package tetris;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import tetris.logique.Jeu;
import tetris.logique.Joueur;
import tetris.logique.Plateau;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

public class TetrisIHM extends Application {
    Pane pane = new Pane();
    GridPane gp = new GridPane();

    IJeu jeu;

    Plateau p;
    Joueur j;

    private Stage primaryStage;
    private Scene scene;
    ActionListener descenteAuto;

    @Override
    public void start(Stage stage) {
        this.primaryStage = new Stage();
        demarrerPartie();
    }

    public void demarrerPartie() {
        //VuePlateau plateau = new VuePlateau();
        jeu = new Jeu();
        p = jeu.getPlateau();

        scene = new Scene(afficherplateau());
        primaryStage.setScene(scene);
        primaryStage.show();

        creerBindings();

        Jeu.timer = new Timer(1000, descenteAuto);
        Jeu.timer.start();

        afficherplateau();
        primaryStage.show();

    }

    public GridPane afficherplateau() {

        Image vide = new Image("file:src/main/resources/img/vide.png");

        Image imgS = new Image("file:src/main/resources/img/S.jpg");
        Image imgI = new Image("file:src/main/resources/img/I.jpg");
        Image imgJ = new Image("file:src/main/resources/img/J.jpg");
        Image imgL = new Image("file:src/main/resources/img/L.jpg");
        Image imgO = new Image("file:src/main/resources/img/O.jpg");
        Image imgT = new Image("file:src/main/resources/img/T.jpg");
        Image imgZ = new Image("file:src/main/resources/img/Z.jpg");

        for (int i = 0; i < p.getPlateau().length; i++) {
            for (int y = 0; y < p.getPlateau()[0].length; y++) {
                ImageView tf = new ImageView();
                tf.setImage(vide);
                tf.setFitHeight(45);
                tf.setFitWidth(45);
                String s = p.getPlateau()[i][y].getNom();
                switch (s) {
                    case "S" -> tf.setImage(imgS);
                    case "I" -> tf.setImage(imgI);
                    case "J" -> tf.setImage(imgJ);
                    case "L" -> tf.setImage(imgL);
                    case "O" -> tf.setImage(imgO);
                    case "T" -> tf.setImage(imgT);
                    case "Z" -> tf.setImage(imgZ);
                }
                //tf.setStyle("-fx-background-color: limegreen;");
                GridPane.setRowIndex(tf,i);
                GridPane.setColumnIndex(tf,y);
                gp.getChildren().add(tf);
            }
        }
        return gp;
    }



    public void effacerGridPane() {
        gp.getChildren().clear();
    }

    public void creerBindings() {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (jeu.isJeuEnCours()) {
                    switch(keyEvent.getCode()) {
                        case LEFT -> {
                            jeu.actionGauche();
                            effacerGridPane();
                            afficherplateau();
                        }
                        case RIGHT -> {
                            jeu.actionDroite();
                            effacerGridPane();
                            afficherplateau();
                        }
                        case DOWN -> {
                            jeu.actionBas();
                            effacerGridPane();
                            afficherplateau();
                        }
                        case UP -> {
                            jeu.actionHaut();
                            effacerGridPane();
                            afficherplateau();
                        }
                        case ESCAPE -> {
                            jeu.actionEchap();
                            effacerGridPane();
                            afficherplateau();
                        }
                        case SPACE -> {
                            jeu.actionEspace();
                            effacerGridPane();
                            afficherplateau();
                        }
                    }
                }
            }
        });

        // On définit l'action à faire automatiquement et le donne au Timer, qui l'exécutera tout seul
        descenteAuto = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        jeu.tomberPieceActuelle1Ligne();
                        effacerGridPane();
                        afficherplateau();
                        jeu.jouerTour();
                    }
                });
            }
        };

    }

    private String test(String path) {
        return Objects.requireNonNull(getClass().getResource(path)).toString();
    }
}
