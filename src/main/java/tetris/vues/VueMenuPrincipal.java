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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tetris.logique.Preferences;
import tetris.vues.menu.VueClassement;
import tetris.vues.menu.VueMenuCompte;
import tetris.vues.menu.VueMenuPersonnaliser;

public class VueMenuPrincipal extends Stage {

    private final Scene scene;

    public static Background background = new Background(new BackgroundImage(new Image("file:src/main/resources/img/background/classic.jpg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1280, 720, false, false, false, false)));
    Preferences preferences = Preferences.getInstance();
    private final Font police = Font.loadFont("file:src/main/resources/fonts/Arcade.ttf", 32);

    private final ImageView logo;
    private final Button start, parametres, personnaliser, compte, classement, quitter;

    //Elements du menu connexion joueur
    private final VBox boutons;
    private final VBox vbCompte;

    // Gestion de la personnalisation des pièces
    private final Image pieceDefault = new Image("file:src/main/resources/img/default/L.jpg");
    private final Image pieceConteneur = new Image("file:src/main/resources/img/conteneur/L.jpg");
    private final Image pieceBrique = new Image("file:src/main/resources/img/brique/L.jpg");
    private final ImageView pieceEnCoursPerso = new ImageView(pieceConteneur);
    private int i = 0;

    // Sous-vues
    VueMenuCompte vueMenuCompte;
    VueMenuPersonnaliser vueMenuPersonnaliser;
    VueClassement vueClassement;


    public VueMenuPrincipal() {
        BorderPane root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        // Logo
        logo = new ImageView(new Image("file:src/main/resources/img/titreMenu.png"));

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
        root.setBackground(background);
        root.setBottom(compte);

        vueMenuCompte = new VueMenuCompte(this);
        vueMenuPersonnaliser = new VueMenuPersonnaliser(this);
        vueClassement = new VueClassement(this);

        this.setScene(scene);
    }

    /**
     * Applique tous les styles souhaités aux objets JavaFX
     */
    public void styliserMenu() {
        // Général
        scene.getStylesheets().add("file:src/main/resources/css/menu.css");

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
        start.setFont(police);
        start.getStyleClass().add("bouton");

        parametres.setText("Paramètres");
        parametres.setFont(police);
        parametres.getStyleClass().add("bouton");

        personnaliser.setText("Personnaliser");
        personnaliser.setFont(police);
        personnaliser.getStyleClass().add("bouton");

        compte.setText("Compte");
        compte.setFont(police);
        compte.getStyleClass().add("bouton");

        classement.setText("Classement");
        classement.setFont(police);
        classement.getStyleClass().add("bouton");

        quitter.setText("Quitter");
        quitter.setFont(police);
        quitter.getStyleClass().add("bouton");

        this.setResizable(false);
    }

    /**
     * Fonction qui permet d'afficher le menu principal (utilisé lors d'un appui sur le bouton retour)
     */
    public void afficherMenuPrincipal() {
        this.setScene(scene);
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
        compte.setOnAction(actionEvent -> this.setScene(vueMenuCompte.getScene()));
        personnaliser.setOnAction(actionEvent -> this.setScene(vueMenuPersonnaliser.getScene()));
        classement.setOnAction(actionEvent -> this.setScene(vueClassement.getScene()));
    }

    /**
     * Getter utile pour la récupération dans TetrisIHM
     */
    public TextField getNomJoueur() {
        return vueMenuCompte.getPseudo();
    }

    /**
     * Getter utile pour la récupération dans TetrisIHM
     */
    public PasswordField getMotDePasse() {
        return vueMenuCompte.getMotDePasse();
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
        vueMenuCompte.setButtonConnecterJoueurCliqueListener(joueurConnecte);
    }

    /**
     * Fonction qui déclenche le lancement du jeu après la création d'un nouvel utilisateur dans TetrisIHM
     */
    public void setButtonCreerJoueurCliqueListener(EventHandler<ActionEvent> nouveauJoueurCree) {
        vueMenuCompte.setButtonCreerJoueurCliqueListener(nouveauJoueurCree);
    }

    public void setButtonQuitterListener(EventHandler<ActionEvent> quitterAction) {
        quitter.setOnAction(quitterAction);
    }
}
