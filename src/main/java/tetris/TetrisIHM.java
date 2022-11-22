package tetris;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tetris.logique.Jeu;
import tetris.logique.Plateau;
import tetris.vues.VueGameOver;
import tetris.vues.VueMenuPrincipal;
import tetris.vues.VuePlateau;

import javax.swing.*;
import java.awt.event.ActionListener;

public class TetrisIHM extends Application {

    // Objets issus de JavaFX pour l'IHM du jeu
    private Scene scene;
    private StackPane pane;
    private BorderPane borderPane;
    private VBox informationsJoueur;
    private ActionListener descenteAuto;
    private Button startJeu;
    Label score, pseudo, rang;

    // Vues personnelles (créées par l'équipe)
    private VueMenuPrincipal vueMenuPrincipal;
    private VuePlateau vuePlateau;

    // Objets de la logique du jeu
    IJeu jeu;
    Plateau p;

    private final EventHandler<ActionEvent> quandLeButtonEstClique = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            demarrerPartie();
            vueMenuPrincipal.close();

        }
    };

    @Override
    public void start(Stage primaryStage) {
        vueMenuPrincipal = new VueMenuPrincipal();
        vueMenuPrincipal.setButtonCliqueListener(quandLeButtonEstClique);
        vueMenuPrincipal.show();
    }

    public void demarrerPartie() {
        // Initialisations des objets nécessaires
        // squelette du javaFX
        Stage primaryStage = new Stage();
        scene = new Scene(borderPane);

        // javaFX
        borderPane = new BorderPane();
        pane = new StackPane();
        startJeu = new Button();
        score = new Label("0.0");
        pseudo = new Label(jeu.getJoueur().getPseudo());
        rang = new Label("Rang : 1");

        // classes de la logique du jeu
        jeu = new Jeu();
        p = jeu.getPlateau();
        vuePlateau = new VuePlateau(p);

        // Affectations et constitution de vues
        informationsJoueur = new VBox(pseudo, score, rang);
        pane.getChildren().add(startJeu);

        borderPane.setLeft(pane);
        borderPane.setCenter(vuePlateau);
        borderPane.getChildren().add(informationsJoueur);

        // "Stylisation" et bindings/listeners
        creerBindings();
        styliser();

        // Initialisation du timer (obligatoire après les bindings)
        Jeu.timer = new Timer(1000, descenteAuto);

        // Affichage définitif
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Créé tous les listeners/bindings afin que la partie se déroule correctement.
     * Voir les commentaires respectifs à chaque listener pour plus d'informations.
     */
    public void creerBindings() {
        // Listener sur les "inputs" (actions du joueur) lors d'une partie. Ces inputs sont les flèches du clavier
        scene.setOnKeyPressed(keyEvent -> {
            if (jeu.isJeuEnCours()) {
                switch (keyEvent.getCode()) {
                    case LEFT -> jeu.actionGauche();
                    case RIGHT -> jeu.actionDroite();
                    case DOWN -> jeu.actionBas();
                    case UP -> jeu.actionHaut();
                    case R -> jeu.actionR();
                    case ESCAPE -> jeu.actionEchap();
                    case SPACE -> jeu.actionEspace();
                }
                vuePlateau.mettreAJour();
            }
        });

        // On définit l'action à faire automatiquement et le donne au Timer, qui l'exécutera tout seul
        descenteAuto = e -> Platform.runLater(() -> {
            jeu.tomberPieceActuelle1Ligne();
            vuePlateau.mettreAJour();
            jeu.jouerTour();
        });

        // Listener sur le score du joueur
        jeu.getJoueur().getScore().addListener((observableValue, number, t1) -> score.setText(jeu.getJoueur().getScore().getValue() + ""));

        // Listener pour actualiser le rang de la partie
        jeu.getRang(jeu.getPlateau()).addListener((observableValue, number, t1) -> rang.setText("Rang : " + jeu.getRang(jeu.getPlateau()).getValue()));

        // Listener pour agir en cas de fin de jeu
        // Gère les cas : Rejouer et Réessayer
        jeu.jeuEnCoursProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!jeu.isJeuEnCours()) {
                VueGameOver vueGameOver = new VueGameOver();
                vueGameOver.arreterJeuProperty().addListener((observableValue12, aBoolean12, t112) -> {
                    if (vueGameOver.arreterJeuProperty().getValue()) {
                        System.exit(0);
                    }
                });

                vueGameOver.retryProperty().addListener((observableValue1, aBoolean1, t11) -> {
                    if (vueGameOver.retryProperty().getValue()) {
                        relancerPartie();
                    }
                });
                vueGameOver.show();
            }
        });

        // Lance le jeu une fois le bouton startJeu cliqué
        startJeu.setOnAction(actionEvent -> {
            Jeu.timer.start();
            startJeu.setDisable(true);
        });
    }

    /**
     * Relance la partie, en réinitialisant l'état du jeu
     */
    private void relancerPartie() {
        startJeu.setDisable(false);
        demarrerPartie();
    }

    /**
     * Applique tous les styles souhaités aux objets JavaFX
     */
    public void styliser() {
        // BorderPane
        borderPane.setStyle("-fx-background-color: #1E1E1E");
        borderPane.setPrefWidth(700);
        borderPane.setPrefHeight(1000);

        // Pseudo
        pseudo.setTextFill(Color.WHITE);
        pseudo.setStyle("-fx-font-size: 15px");
        pseudo.setMinSize(50, 50);
        pseudo.setLayoutX(150);

        // Score
        score.setLayoutX(150);
        score.setLayoutY(150);
        score.setStyle("-fx-font-size: 15px");
        score.setLayoutY(50);
        score.setTextFill(Color.WHITE);
        score.setAlignment(Pos.BASELINE_LEFT);
        score.setMinSize(150, 150);

        // Rang
        rang.setTextFill(Color.WHITE);
        rang.setStyle("-fx-font-size: 15px");
        rang.setMinSize(150, 150);
        rang.setLayoutX(150);
        rang.setLayoutY(200);

        // Labels
        informationsJoueur.setLayoutX(20);
        informationsJoueur.setLayoutY(20);

        // StartJeu
        startJeu.setGraphic(new ImageView(new Image("file:src/main/resources/img/start.png")));
        startJeu.setStyle("-fx-background-color: transparent");

        // Pane
        pane.setStyle("-fx-background-color: #1E1E1E");
    }
}
