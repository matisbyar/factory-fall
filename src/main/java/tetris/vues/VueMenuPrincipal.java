package tetris.vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import tetris.TetrisIHM;
import tetris.logique.Preferences;
import tetris.stockage.Session;
import tetris.vues.menu.VueClassement;
import tetris.vues.menu.VuePersonnaliser;
import tetris.vues.menu.compte.VueCompteConnecte;
import tetris.vues.menu.compte.VueCompteDeconnecte;

import java.util.Objects;

public class VueMenuPrincipal extends Stage implements Menu {

    private final Scene scene;
    //Objects.requireNonNull(TetrisIHM.class.getResourceAsStream())
    Preferences preferences = Preferences.getInstance();

    private final ImageView logo;
    private final Button start, parametres, personnaliser, compte, classement, quitter;

    //Elements du menu connexion joueur
    private final VBox boutons;
    private final VBox vbCompte;

    // Gestion de la personnalisation des pièces
    private final Image pieceDefault = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/default/L.jpg")));
    private final Image pieceConteneur = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/conteneur/L.jpg")));
    private final Image pieceBrique = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/brique/L.jpg")));
    private final ImageView pieceEnCoursPerso = new ImageView(pieceConteneur);
    private int i = 0;

    // Sous-vues
    VueCompteDeconnecte vueCompteDeconnecte;
    VueCompteConnecte vueCompteConnecte;
    VuePersonnaliser vuePersonnaliser;
    VueClassement vueClassement;


    public VueMenuPrincipal() {
        BorderPane root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        // Logo
        logo = new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/titreMenu.png"))));

        // Boutons
        start = new Button();
        parametres = new Button();
        personnaliser = new Button();
        classement = new Button();
        quitter = new Button();
        compte = new Button();

        vbCompte = new VBox(compte);

        boutons = new VBox(logo, start, parametres, personnaliser, classement, quitter);

        styliserMenu();
        creerBindings();

        // Affectations
        root.setCenter(boutons);
        root.setBottom(compte);
        root.setBackground(new Background(new BackgroundImage(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/background/industrial.png"))), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1280, 720, false, false, false, false))));

        vueCompteDeconnecte = new VueCompteDeconnecte(this);
        vueCompteConnecte = new VueCompteConnecte(this);
        vuePersonnaliser = new VuePersonnaliser(this);
        vueClassement = new VueClassement(this);

        this.setScene(scene);
    }

    /**
     * Applique tous les styles souhaités aux objets JavaFX
     */
    public void styliserMenu() {
        // Général
        scene.getStylesheets().add(Objects.requireNonNull(TetrisIHM.class.getResource("css/main.css")).toString());

        VBox.setMargin(logo, new Insets(100, 0, 30, 0));
        VBox.setMargin(compte, new Insets(30, 0, 30, 80));

        // VBox Bouton
        boutons.setAlignment(Pos.TOP_CENTER);
        boutons.setSpacing(20);

        // VBox Compte
        vbCompte.setAlignment(Pos.CENTER_LEFT);
        vbCompte.setPrefWidth(200);
        vbCompte.setPrefHeight(200);

        // Boutons
        start.setText("Jouer");
        start.setFont(preferences.getPolice(32));
        start.getStyleClass().add("bouton");

        parametres.setText("Paramètres");
        parametres.setFont(preferences.getPolice(32));
        parametres.getStyleClass().add("bouton");

        personnaliser.setText("Personnaliser");
        personnaliser.setFont(preferences.getPolice(32));
        personnaliser.getStyleClass().add("bouton");

        compte.setText("Compte");
        compte.setFont(preferences.getPolice(32));
        compte.getStyleClass().add("bouton");

        classement.setText("Classement");
        classement.setFont(preferences.getPolice(32));
        classement.getStyleClass().add("bouton");

        quitter.setText("Quitter");
        quitter.setFont(preferences.getPolice(32));
        quitter.getStyleClass().add("bouton");

        this.setResizable(false);
    }

    /**
     * Fonction qui permet de changer les images dans la "VuePersonnalisation"
     */
    public void changerImage(String etat) {
        switch (etat) {
            case "+" -> i++;
            case "-" -> i--;
        }
        if (i >= 3) {
            i = 0;
        } else if (i < 0) {
            i = 2;
        }
        switch (i) {
            case 0 -> {
                pieceEnCoursPerso.setImage(pieceConteneur);
                preferences.setStylePiece("conteneur");
            }
            case 1 -> {
                pieceEnCoursPerso.setImage(pieceBrique);
                preferences.setStylePiece("brique");
            }
            case 2 -> {
                pieceEnCoursPerso.setImage(pieceDefault);
                preferences.setStylePiece("default");
            }
        }
    }

    /**
     * Fonction qui créer tous les bindings in line utile pour l'ensemble des boutons du menu
     */
    public void creerBindings() {
        compte.setOnAction(actionEvent -> this.setScene(Session.getInstance().isConnected() ? vueCompteConnecte.getScene() : vueCompteDeconnecte.getScene()));
        personnaliser.setOnAction(actionEvent -> this.setScene(vuePersonnaliser.getScene()));
        classement.setOnAction(actionEvent -> this.setScene(vueClassement.getScene()));
    }

    /**
     * Getter utile pour la récupération dans TetrisIHM
     */
    public TextField getNomJoueur() {
        return vueCompteDeconnecte.getPseudo();
    }

    /**
     * Getter utile pour la récupération dans TetrisIHM
     */
    public PasswordField getMotDePasse() {
        return vueCompteDeconnecte.getMotDePasse();
    }

    /**
     * Getter utile pour la récupération dans TetrisIHM
     */
    public TextField getDepartement() {
        return vueCompteDeconnecte.getDepartement();
    }




    /**
     * Fonction qui déclenche le lancement du jeu en Anonyme dans TetrisIHM
     */
    public void setButtonJouerCliqueListener(EventHandler<ActionEvent> quandLeButtonJouerEstClique) {
        start.setOnAction(quandLeButtonJouerEstClique);
    }

    /**
     * Fonction qui déclenche le lancement du jeu après la connexion d'un utilisateur dans TetrisIHM
     */
    public void setButtonConnecterJoueurCliqueListener(EventHandler<ActionEvent> joueurConnecte) {
        vueCompteDeconnecte.setButtonConnecterJoueurCliqueListener(joueurConnecte);
    }

    /**
     * Fonction qui déclenche le lancement du jeu après la création d'un nouvel utilisateur dans TetrisIHM
     */
    public void setButtonCreerJoueurCliqueListener(EventHandler<ActionEvent> nouveauJoueurCree) {
        vueCompteDeconnecte.setButtonCreerJoueurCliqueListener(nouveauJoueurCree);
    }

    public void setButtonQuitterListener(EventHandler<ActionEvent> quitterAction) {
        quitter.setOnAction(quitterAction);
    }

    @Override
    public void afficherScene() {
        this.setScene(scene);
    }

}
