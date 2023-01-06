package tetris.vues.menu.compte;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tetris.TetrisIHM;
import tetris.vues.Menu;
import tetris.vues.VueMenuPrincipal;
import tetris.vues.helpers.BarreNavigation;

import java.util.Objects;

public class VueCompteDeconnecte extends Stage implements Menu {

    private final BorderPane root;
    private final Scene scene;

    private final HBox option;
    private final VBox champsConnexion, champsCreation;

    private final Label titreConnexion, titreCreation;
    private final TextField pseudoConnexion, pseudoCreation;
    private final PasswordField motDePasseConnexion, motDePasseCreation, motDePasseCreationConfirmation;
    private final Button boutonConnexion, boutonCreation;

    // Affichage
    private final Font police = Font.loadFont(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("fonts/arcade.ttf")), 32);

    public VueCompteDeconnecte(VueMenuPrincipal vueMenuPrincipal) {
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        option = new HBox();

        champsConnexion = new VBox();
        champsCreation = new VBox();

        boutonConnexion = new Button();
        boutonCreation = new Button();

        pseudoConnexion = new TextField();
        pseudoCreation = new TextField();

        motDePasseConnexion = new PasswordField();
        motDePasseCreation = new PasswordField();
        motDePasseCreationConfirmation = new PasswordField();

        titreConnexion = new Label("Connexion");
        titreCreation = new Label("Inscription");

        // Styles et bindings
        styliser();
        setDisable();

        champsConnexion.getChildren().addAll(titreConnexion, pseudoConnexion, motDePasseConnexion, boutonConnexion);
        champsCreation.getChildren().addAll(titreCreation, pseudoCreation, motDePasseCreation, motDePasseCreationConfirmation, boutonCreation);

        option.getChildren().addAll(champsConnexion, champsCreation);

        root.setCenter(option);
        root.setTop(new BarreNavigation("Compte", vueMenuPrincipal, this));

        this.setScene(scene);
    }

    public void styliser() {
        // Root (BorderPane)
        root.setBackground(VueMenuPrincipal.background);

        // Scene
        scene.getStylesheets().add(Objects.requireNonNull(TetrisIHM.class.getResource("css/menu.css")).toString());

        // Options (connexion ou création)
        option.setAlignment(Pos.CENTER);
        option.setSpacing(100);

        // Titre
        titreConnexion.setFont(police);
        titreCreation.setFont(police);
        titreConnexion.setStyle("-fx-text-fill: white");
        titreCreation.setStyle("-fx-text-fill: white");

        // Box de connexion/création
        champsConnexion.setSpacing(20);
        champsCreation.setSpacing(20);

        // Champs pseudo
        pseudoConnexion.setPromptText("Pseudo");
        pseudoCreation.setPromptText("Pseudo");
        pseudoConnexion.getStyleClass().add("textFieldNomJoueur");
        pseudoCreation.getStyleClass().add("textFieldNomJoueur");

        // Champs mot de passe
        motDePasseConnexion.setPromptText("Mot de passe");
        motDePasseCreation.setPromptText("Mot de passe");
        motDePasseCreationConfirmation.setPromptText("Confirmation du mot de passe");
        motDePasseConnexion.getStyleClass().add("textFieldNomJoueur");
        motDePasseCreation.getStyleClass().add("textFieldNomJoueur");
        motDePasseCreationConfirmation.getStyleClass().add("textFieldNomJoueur");

        // Boutons
        boutonConnexion.setText("Connexion");
        boutonConnexion.getStyleClass().add("bouton");
        boutonCreation.setText("S'inscrire");
        boutonCreation.getStyleClass().add("bouton");
    }


    /**
     * Empêche l'utilisateur de renseigner des champs de connexion s'il a déjà écrit dans des champs d'inscription, et vice-versa.
     */
    public void setDisable() {
        champsConnexionLock(pseudoConnexion, motDePasseConnexion);
        champsConnexionLock(motDePasseConnexion, pseudoConnexion);
        champsCreationLock(pseudoCreation, motDePasseCreation);
        champsCreationLock(motDePasseCreation, pseudoCreation);
    }

    /**
     * Empêche l'utilisateur de renseigner des champs de connexion s'il a déjà écrit dans des champs d'inscription
     *
     * @param pseudoCreation     pseudo de l'utilisateur
     * @param motDePasseCreation mot de passe de l'utilisateur
     */
    private void champsCreationLock(TextField pseudoCreation, TextField motDePasseCreation) {
        pseudoCreation.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                pseudoConnexion.setDisable(true);
                motDePasseConnexion.setDisable(true);
            } else if (motDePasseCreation.getText().isEmpty() && motDePasseCreationConfirmation.getText().isEmpty()) {
                pseudoConnexion.setDisable(false);
                motDePasseConnexion.setDisable(false);
            }
        });
    }

    /**
     * Empêche l'utilisateur de renseigner des champs d'inscription s'il a déjà écrit dans des champs de connexion
     *
     * @param pseudoConnexion     pseudo de l'utilisateur
     * @param motDePasseConnexion mot de passe de l'utilisateur
     */
    private void champsConnexionLock(TextField pseudoConnexion, TextField motDePasseConnexion) {
        pseudoConnexion.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                pseudoCreation.setDisable(true);
                motDePasseCreation.setDisable(true);
                motDePasseCreationConfirmation.setDisable(true);
            } else if (motDePasseConnexion.getText().isEmpty()) {
                pseudoCreation.setDisable(false);
                motDePasseCreation.setDisable(false);
                motDePasseCreationConfirmation.setDisable(false);
            }
        });
    }

    /**
     * Fonction qui déclenche le lancement du jeu après la connexion d'un utilisateur dans TetrisIHM
     */
    public void setButtonConnecterJoueurCliqueListener(EventHandler<ActionEvent> joueurConnecte) {
        boutonConnexion.setOnAction(joueurConnecte);
    }

    /**
     * Fonction qui déclenche le lancement du jeu après la création d'un utilisateur dans TetrisIHM
     */
    public void setButtonCreerJoueurCliqueListener(EventHandler<ActionEvent> joueurCree) {
        boutonCreation.setOnAction(joueurCree);
    }

    /**
     * Récupère le pseudo de l'utilisateur
     *
     * @return pseudo de l'utilisateur
     */
    public TextField getPseudo() {
        return pseudoConnexion.isDisabled() ? pseudoCreation : pseudoConnexion;
    }

    /**
     * Récupère le mot de passe de l'utilisateur
     *
     * @return mot de passe de l'utilisateur
     */
    public PasswordField getMotDePasse() {
        return motDePasseConnexion.isDisabled() ? motDePasseCreation : motDePasseConnexion;
    }

    @Override
    public void afficherScene() {
        this.setScene(scene);
    }
}
