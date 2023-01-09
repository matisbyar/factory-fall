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
import tetris.singletons.Preferences;
import tetris.singletons.Ressources;
import tetris.stockage.Session;
import tetris.vues.menu.VueClassement;
import tetris.vues.menu.VuePersonnaliser;
import tetris.vues.menu.VueRegles;
import tetris.vues.menu.compte.VueCompteConnecte;
import tetris.vues.menu.compte.VueCompteDeconnecte;

import java.util.Objects;

public class VueMenuPrincipal extends Stage implements Menu {

    private final BorderPane root;
    private final Scene scene;
    //Objects.requireNonNull(TetrisIHM.class.getResourceAsStream())
    Preferences preferences = Preferences.getInstance();

    private final ImageView logo;
    private final Button start, regles, personnaliser, compte, classement, quitter;

    //Elements du menu connexion joueur
    private final VBox boutons;
    private final VBox vbCompte;

    // Sous-vues
    VueCompteDeconnecte vueCompteDeconnecte;
    VueCompteConnecte vueCompteConnecte;
    VuePersonnaliser vuePersonnaliser;
    VueClassement vueClassement;
    VueRegles vueRegles;


    public VueMenuPrincipal() {
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        // Logo
        logo = new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/titre.png"))));

        // Boutons
        start = new Button();
        regles = new Button();
        personnaliser = new Button();
        classement = new Button();
        quitter = new Button();
        compte = new Button();

        vbCompte = new VBox(compte);

        boutons = new VBox(logo, start, regles, personnaliser, classement, quitter);

        styliser();
        creerBindings();

        // Affectations
        root.setCenter(boutons);
        root.setBottom(compte);
        root.setBackground(new Background(new BackgroundImage(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/background/industrial.png"))), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1280, 720, false, false, false, false))));

        vueCompteDeconnecte = new VueCompteDeconnecte(this);
        vueCompteConnecte = new VueCompteConnecte(this);
        vuePersonnaliser = new VuePersonnaliser(this);
        vueClassement = new VueClassement(this);
        vueRegles = new VueRegles(this);

        this.setScene(scene);
    }

    /**
     * Applique tous les styles souhaités aux objets JavaFX
     */
    private void styliser() {
        // Général
        scene.getStylesheets().add(Objects.requireNonNull(TetrisIHM.class.getResource("css/main.css")).toString());

        // Titre
        logo.setStyle("-fx-padding: 5px; -fx-background-color: black;");

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
        start.setFont(Ressources.getInstance().getPolice(32));
        start.getStyleClass().add("bouton");

        regles.setText("Règles");
        regles.setFont(Ressources.getInstance().getPolice(32));
        regles.getStyleClass().add("bouton");

        personnaliser.setText("Personnaliser");
        personnaliser.setFont(Ressources.getInstance().getPolice(32));
        personnaliser.getStyleClass().add("bouton");

        compte.setText("Compte");
        compte.setFont(Ressources.getInstance().getPolice(32));
        compte.getStyleClass().add("bouton");

        classement.setText("Classement");
        classement.setFont(Ressources.getInstance().getPolice(32));
        classement.getStyleClass().add("bouton");

        quitter.setText("Quitter");
        quitter.setFont(Ressources.getInstance().getPolice(32));
        quitter.getStyleClass().add("bouton");

        this.setResizable(false);
    }

    /**
     * Fonction qui créer tous les bindings in line utile pour l'ensemble des boutons du menu
     */
    private void creerBindings() {
        compte.setOnAction(actionEvent -> {
            this.setScene(Session.getInstance().isConnected() ? vueCompteConnecte.getScene() : vueCompteDeconnecte.getScene());
            vueCompteConnecte.mettreAJourFond();
            vueCompteDeconnecte.mettreAJourFond();
        });
        regles.setOnAction(actionEvent -> {
            this.setScene(vueRegles.getScene());
            vueRegles.mettreAJourFond();
        });
        personnaliser.setOnAction(actionEvent -> {
            this.setScene(vuePersonnaliser.getScene());
            vuePersonnaliser.mettreAJourFond();
        });
        classement.setOnAction(actionEvent -> {
            this.setScene(vueClassement.getScene());
            vueClassement.mettreAJourFond();
        });
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
        mettreAJourFond();
    }

    public void mettreAJourFond() {
        root.setBackground(Preferences.getInstance().getBackground());
    }
}
