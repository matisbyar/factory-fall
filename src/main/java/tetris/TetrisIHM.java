package tetris;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import tetris.logique.Jeu;
import tetris.logique.Joueur;
import tetris.logique.Plateau;
import tetris.vues.VueGameOver;

import javax.swing.*;
import javafx.event.ActionEvent;
import tetris.vues.VueMenuPrincipal;

import java.awt.event.ActionListener;
import java.util.Objects;

public class TetrisIHM extends Application {

    private final EventHandler<ActionEvent> quandLeButtonEstClique = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            demarrerPartie();
            vueMenuPrincipal.close();

        }
    };
    StackPane pane = new StackPane();
    BorderPane borderPane = new BorderPane();
    BorderPane borderPaneAvantLancement = new BorderPane();

    GridPane gp = new GridPane();


    Label score = new Label("0.0");

    Label pseudo = new Label("User");

    Label rang = new Label("Rang : 1");

    VBox labelsValue = new VBox(pseudo, score, rang);
    IJeu jeu;

    Plateau p;
    Joueur j;

    private Stage primaryStage = new Stage();
    private Scene scene;
    ActionListener descenteAuto;

    Button startMenu = new Button("Lancer Jeu");
    Button startJeu = new Button("Start");

    VueMenuPrincipal vueMenuPrincipal;

    @Override
    public void start(Stage primaryStage) throws Exception {
        vueMenuPrincipal = new VueMenuPrincipal();
        vueMenuPrincipal.setButtonCliqueListener(quandLeButtonEstClique);
        vueMenuPrincipal.show();
    }


    public void demarrerPartie() {
        //VuePlateau plateau = new VuePlateau();
        jeu = new Jeu();
        p = jeu.getPlateau();
        pseudo.setText(jeu.getPseudoJoueurChoisi(jeu.getJoueur()));

        pane.setStyle("-fx-background-color: #1E1E1E");

        borderPane.setStyle("-fx-background-color: #1E1E1E");
        borderPane.setLeft(pane);
        borderPane.setCenter(gp);
        gp.setAlignment(Pos.CENTER);

        borderPane.getChildren().add(labelsValue);
        score.setTextFill(Color.WHITE);
        score.setAlignment(Pos.BASELINE_LEFT);
        pseudo.setTextFill(Color.WHITE);
        rang.setTextFill(Color.WHITE);
        score.setLayoutX(150);
        score.setLayoutY(150);
        score.setStyle("-fx-font-size: 15px");
        pseudo.setStyle("-fx-font-size: 15px");
        pseudo.setMinSize(50, 50);
        pseudo.setLayoutX(150);
        score.setLayoutY(50);
        labelsValue.setLayoutX(20);
        labelsValue.setLayoutY(20);
        score.setMinSize(150, 150);

        rang.setStyle("-fx-font-size: 15px");
        rang.setMinSize(150, 150);
        rang.setLayoutX(150);
        rang.setLayoutY(200);

        //gridPane.add(afficherButtonMenu(), 1,1);
        borderPane.setPrefWidth(700);
        borderPane.setPrefHeight(1000);

        scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();

        creerBindings();

        pane.getChildren().add(startJeu);

        startJeu.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent actionEvent) {
                Jeu.timer.start();
                startJeu.setDisable(true);
            }
        });

        Jeu.timer = new Timer(1000, descenteAuto);
        //Jeu.timer.start();
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
                ImageView imgView = new ImageView();
                imgView.setImage(vide);
                imgView.setFitHeight(45);
                imgView.setFitWidth(45);
                String s = p.getPlateau()[i][y].getNom();
                switch (s) {
                    case "S" -> imgView.setImage(imgS);
                    case "I" -> imgView.setImage(imgI);
                    case "J" -> imgView.setImage(imgJ);
                    case "L" -> imgView.setImage(imgL);
                    case "O" -> imgView.setImage(imgO);
                    case "T" -> imgView.setImage(imgT);
                    case "Z" -> imgView.setImage(imgZ);
                }
                GridPane.setRowIndex(imgView,i);
                GridPane.setColumnIndex(imgView,y);
                gp.getChildren().add(imgView);
            }
        }
        return gp;
    }

    public Pane afficherBackground(){
        Button text = new Button("Menu");
        pane.setStyle("-fx-color: white" + text);
        pane.setStyle("-fx-background-color: #1E1E1E");
        pane.setPrefHeight(990);
        pane.setPrefWidth(400);

        return pane;
    }
    public Button afficherButtonMenu(){
        Button buttonMenu = new Button("Menu");
        return buttonMenu;
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
            public void actionPerformed(java.awt.event.ActionEvent e) {
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

        jeu.getJoueur().getScore().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                score.setText(jeu.getJoueur().getScore().getValue()+"");
            }
        });

        jeu.getRang(jeu.getPlateau()).addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                rang.setText("Rang : " + jeu.getRang(jeu.getPlateau()).getValue());
            }
        });

        jeu.getJeuEnCoursProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!jeu.isJeuEnCours()) {
                    VueGameOver vueGameOver = new VueGameOver();
                    vueGameOver.arreterJeuProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                            if (vueGameOver.arreterJeuProperty().getValue()) {
                                Platform.exit();
                            }
                        }
                    });

                    vueGameOver.retryProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                            if (vueGameOver.retryProperty().getValue()) {
                                demarrerPartie();
                            }
                        }
                    });
                    vueGameOver.show();
                }
            }
        });
    }

    private String test(String path) {
        return Objects.requireNonNull(getClass().getResource(path)).toString();
    }
}
