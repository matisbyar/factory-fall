package tetris;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tetris.logique.AuthPlayer;
import tetris.logique.Jeu;
import tetris.logique.Plateau;
import tetris.stockage.PlayerManager;
import tetris.stockage.Security;
import tetris.stockage.Session;
import tetris.vues.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class TetrisIHM extends Application {

    // Objets issus de JavaFX pour l'IHM du jeu
    private boolean jeuEnPause = false;
    private Scene scene;
    Stage primaryStage;
    private BorderPane borderPane;
    private VBox informationsJoueur;
    private ActionListener descenteAuto;
    private Button startJeu;
    Label score, pseudo, rang, prochainePieceLabel;
    private final Font police = Font.loadFont("file:src/main/resources/fonts/arcade.ttf", 32);

    // Vues personnelles (créées par l'équipe)
    private VueMenuPrincipal vueMenuPrincipal;
    private VuePlateau vuePlateau;
    private VueProchainePiece vueProchainePiece;
    private VueControles vueControles;

    private VBox contenerDroit;

    private VBox contenerGauche;

    // Objets de la logique du jeu
    IJeu jeu;
    Plateau p;
    Plateau prochainePiece;

    private String nomjoueur = "";

    /**
     * Le StackPane sp permet de superposer les éléments de l'IHM. Ici, il superpose le plateau de jeu et l'icone de pause.
     */
    private StackPane sp;
    private final ImageView imgPause = new ImageView(new Image("file:src/main/resources/img/pause.png"));

    @Override
    public void start(Stage primaryStage) {
        vueMenuPrincipal = new VueMenuPrincipal();
        vueMenuPrincipal.setButtonJouerCliqueListener(quandLeButtonJouerEstClique);
        vueMenuPrincipal.setButtonConnecterJoueurCliqueListener(joueurconnecte);
        vueMenuPrincipal.setButtonCreerJoueurCliqueListener(nouveaujoueurcree);
        vueMenuPrincipal.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Vérifie si les données rentrées sont valides.
     * Lance la vue demarrer partie apres avoir crée le joueur, et l'avoir connécté
     */
    private final EventHandler<ActionEvent> nouveaujoueurcree = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            AuthPlayer j = PlayerManager.getInstance().getPlayer(vueMenuPrincipal.getNomjoueur().getText());

            if (j != null) {
                System.out.println("Cet identifiant n'est pas disponible");
            } else {
                PlayerManager.getInstance().createPlayer(vueMenuPrincipal.getNomjoueur().getText(), vueMenuPrincipal.getMotDePasse().getText());

                nomjoueur = vueMenuPrincipal.getNomjoueur().getText();
                Session.getInstance().connect(nomjoueur);
                demarrerPartie();
                vueMenuPrincipal.close();
            }
        }
    };

    /**
     * Vérifie si les données rentrées sont valides.
     * Lance la vue demarrer partie apres avoir connecté le joueur.
     */
    private final EventHandler<ActionEvent> joueurconnecte = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            AuthPlayer j = PlayerManager.getInstance().getPlayer(vueMenuPrincipal.getNomjoueur().getText());
            boolean connexionOK = false;

            if (j != null) {
                try {
                    connexionOK = Security.checkPassword(vueMenuPrincipal.getMotDePasse().getText(), j.getSalt(), j.getHashedPassword());
                } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                    e.printStackTrace();
                }

                if (!connexionOK) {
                    //Si le mot de passe est incorrect (mais le login existe dans la BD)
                    System.out.println("Mot de passe incorrect");
                }
            } else {
                //Si l'identifiant est incorrect (aucun joueur de ce login n'est inscrit dans la BD)
                System.out.println("Identifiant incorrect");
            }

            if (connexionOK) {
                nomjoueur = vueMenuPrincipal.getNomjoueur().getText();
                Session.getInstance().connect(nomjoueur);
                demarrerPartie();
                vueMenuPrincipal.close();
            }
        }
    };

    private final EventHandler<ActionEvent> quandLeButtonJouerEstClique = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            nomjoueur = "Anonyme";
            demarrerPartie();
            vueMenuPrincipal.close();
        }
    };

    public void demarrerPartie() {
        // Initialisations des objets nécessaires
        // classes de la logique du jeu
        jeu = new Jeu(nomjoueur);
        jeu.jeuEnCoursProperty().setValue(false);
        p = jeu.getPlateau();
        prochainePiece = jeu.getProchainePiece();

        // javaFX
        borderPane = new BorderPane();
        primaryStage = new Stage();

        vuePlateau = new VuePlateau(p, vueMenuPrincipal.getDossierImg());
        vueProchainePiece = new VueProchainePiece(prochainePiece, vueMenuPrincipal.getDossierImg());
        vueControles = new VueControles();

        startJeu = new Button();
        score = new Label("score : 0.0");
        pseudo = new Label(jeu.getJoueur().getPseudo());
        rang = new Label("rang : 1");
        prochainePieceLabel = new Label("prochaine :");
        contenerDroit = new VBox(prochainePieceLabel, vueProchainePiece, vueControles);
        informationsJoueur = new VBox(pseudo, score, rang);
        contenerGauche = new VBox(informationsJoueur, startJeu);

        // Gestion Plateau/Bouton Pause
        sp = new StackPane(imgPause, vuePlateau);

        scene = new Scene(borderPane);

        // Affectations et constitution de vues
        borderPane.setLeft(contenerGauche);
        borderPane.setCenter(sp);
        borderPane.setRight(contenerDroit);

        // Initialisation des grilles
        vuePlateau.initialiser();
        vueProchainePiece.initialiser();

        // "Stylisation" et bindings/listeners
        creerBindings();
        styliser();

        // Initialisation du timer (obligatoire après les bindings)
        Jeu.timer = new Timer(1000, descenteAuto);

        //boutton pause
        imgPause.setVisible(false);

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
                if (!jeuEnPause) {
                    switch (keyEvent.getCode()) {
                        case LEFT -> jeu.actionGauche();
                        case RIGHT -> jeu.actionDroite();
                        case DOWN -> jeu.actionBas();
                        case UP -> jeu.actionHaut();
                        case R -> jeu.actionR();
                        case ESCAPE -> jeu.actionEchap();
                        case SPACE -> jeu.actionEspace();
                    }
                }
                if (keyEvent.getCode() == KeyCode.P) {
                    jeuEnPause = !jeuEnPause;
                    if (jeuEnPause) {
                        Jeu.timer.stop();
                        imgPause.setVisible(true);
                    } else {
                        Jeu.timer.start();
                        imgPause.setVisible(false);
                    }
                    ObservableList<Node> childs = this.sp.getChildren();

                    if (childs.size() > 1) {
                        Node topNode = childs.get(childs.size() - 1);
                        topNode.toBack();
                    }
                }
                vuePlateau.mettreAJour();
                vueProchainePiece.mettreAJour();
            }
        });

        // On définit l'action à faire automatiquement et le donne au Timer, qui l'exécutera tout seul
        descenteAuto = e -> Platform.runLater(() -> {
            jeu.tomberPieceActuelle1Ligne();
            vuePlateau.mettreAJour();
            jeu.jouerTour();
            vueProchainePiece.mettreAJour();
        });

        // Listener sur le score du joueur
        jeu.getJoueur().getScore().addListener((observableValue, number, t1) -> score.setText("score : " + jeu.getJoueur().getScore().getValue() + ""));

        // Listener pour actualiser le rang de la partie
        jeu.getRang(jeu.getPlateau()).addListener((observableValue, number, t1) -> rang.setText("rang : " + jeu.getRang(jeu.getPlateau()).getValue()));

        // Listener pour agir en cas de fin de jeu
        // Gère les cas : Rejouer et Réessayer
        jeu.jeuEnCoursProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!jeu.isJeuEnCours()) {
                VueGameOver vueGameOver = new VueGameOver();
                //ScoreManager.getInstance().createScore(jeu.getJoueur().getScore().getValue(), Session.getInstance().getLogin());
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
            jeu.jeuEnCoursProperty().setValue(true);
            startJeu.setVisible(false);
        });
    }

    /**
     * Relance la partie, en réinitialisant l'état du jeu
     */
    private void relancerPartie() {
        primaryStage.close();
        startJeu.setDisable(false);
        sp.getChildren().clear();
        demarrerPartie();
        score.setText("score : 0.0");
        rang.setText("rang : 1");
        pseudo.setText(jeu.getJoueur().getPseudo());
    }

    /**
     * Applique tous les styles souhaités aux objets JavaFX
     */
    public void styliser() {

        // Scene
        scene.getStylesheets().add("file:src/main/resources/css/main.css");

        // BorderPane
        borderPane.getStyleClass().add("borderPane");
        borderPane.setPrefWidth(1280);
        borderPane.setPrefHeight(720);
        borderPane.getLeft().getStyleClass().add("leftPane");
        borderPane.getRight().getStyleClass().add("rightPane");
        borderPane.getCenter().getStyleClass().add("centerPane");

        // VuePlateu
        vuePlateau.getStyleClass().add("vuePlateau");
        vuePlateau.setAlignment(Pos.CENTER);
        vuePlateau.setMaxWidth(426);

        // VueProchainePiece
        vueProchainePiece.setPrefWidth(426);
        vueProchainePiece.setAlignment(Pos.TOP_LEFT);

        // Pseudo
        pseudo.setTextFill(Color.WHITE);
        pseudo.setFont(police);
        //pseudo.setStyle("-fx-font-size: 15px");
        pseudo.setMinSize(150, 50);
        pseudo.setLayoutX(150);
        pseudo.setPrefWidth(426);
        pseudo.setAlignment(Pos.TOP_RIGHT);

        // Score
        score.getStyleClass().add("score");
        score.setFont(police);
        //score.setStyle("-fx-font-size: 15px");
        score.setLayoutY(50);
        score.setTextFill(Color.WHITE);
        score.setAlignment(Pos.BASELINE_LEFT);
        score.setMinSize(150, 50);
        score.setAlignment(Pos.TOP_RIGHT);
        score.setPrefWidth(426);

        // Rang
        rang.setTextFill(Color.WHITE);
        rang.setFont(police);
        //rang.setStyle("-fx-font-size: 15px");
        rang.setMinSize(150, 50);
        rang.setLayoutX(150);
        rang.setLayoutY(200);
        rang.setAlignment(Pos.TOP_RIGHT);
        rang.setPrefWidth(426);

        // Label Prochaine pièce
        prochainePieceLabel.setTextFill(Color.WHITE);
        prochainePieceLabel.setFont(police);
        prochainePieceLabel.setMinSize(150, 50);
        prochainePieceLabel.setLayoutX(150);
        prochainePieceLabel.setLayoutY(200);
        prochainePieceLabel.setAlignment(Pos.TOP_LEFT);
        prochainePieceLabel.setPrefWidth(426);

        // Labels
        informationsJoueur.setLayoutX(20);
        informationsJoueur.setLayoutY(20);

        // StartJeu
        startJeu.setGraphic(new ImageView(new Image("file:src/main/resources/img/start.png")));
        startJeu.setStyle("-fx-background-color: black");
        startJeu.getStyleClass().add("bouttonStart");

        // Container gauche
        contenerGauche.setAlignment(Pos.TOP_RIGHT);
        contenerGauche.setPrefWidth(426);

        // Container droit
        contenerDroit.setAlignment(Pos.TOP_RIGHT);
        contenerDroit.setPrefWidth(426);

        // Vue Prochaine pièce
        vueProchainePiece.setAlignment(Pos.CENTER_LEFT);

        // VueControles
        vueControles.setPrefWidth(426);
        vueControles.setAlignment(Pos.BOTTOM_LEFT);

        // Image Pause
        imgPause.setPreserveRatio(true);
        imgPause.setFitWidth(150);

    }
}
