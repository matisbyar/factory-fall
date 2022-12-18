package tetris.vues.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tetris.vues.VueMenuPrincipal;

public class VueMenuCompte extends Stage {

    private final BorderPane root;
    private final Scene scene;

    private final HBox option;
    private final VBox champsConnexion, champsCreation;

    private final Label titreConnexion, titreCreation;
    private final TextField pseudoConnexion, pseudoCreation;
    private final PasswordField motDePasseConnexion, motDePasseCreation, motDePasseCreationConfirmation;
    private final Button boutonConnexion, boutonCreation, retour;

    // Affichage
    private final Font police = Font.loadFont("file:src/main/resources/fonts/Arcade.ttf", 32);
    private final Insets paddingTopLeft = new Insets(30, 0, 30, 80);

    public VueMenuCompte(VueMenuPrincipal vueMenuPrincipal) {
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        option = new HBox();

        champsConnexion = new VBox();
        champsCreation = new VBox();

        boutonConnexion = new Button();
        boutonCreation = new Button();
        retour = new Button();

        pseudoConnexion = new TextField();
        pseudoCreation = new TextField();

        motDePasseConnexion = new PasswordField();
        motDePasseCreation = new PasswordField();
        motDePasseCreationConfirmation = new PasswordField();

        titreConnexion = new Label("Connexion");
        titreCreation = new Label("Inscription");

        // Styles et bindings
        styliser();
        creerBindings(vueMenuPrincipal);

        champsConnexion.getChildren().addAll(titreConnexion, pseudoConnexion, motDePasseConnexion, boutonConnexion);
        champsCreation.getChildren().addAll(titreCreation, pseudoCreation, motDePasseCreation, motDePasseCreationConfirmation, boutonCreation);

        option.getChildren().addAll(champsConnexion, champsCreation);

        root.setCenter(option);
        root.setTop(retour);

        this.setScene(scene);
    }

    public void creerBindings(VueMenuPrincipal vueMenuPrincipal) {
        this.setRetour(vueMenuPrincipal);
        setDisable();
    }

    public void styliser() {
        // Root (BorderPane)
        root.setBackground(VueMenuPrincipal.background);

        // Scene
        scene.getStylesheets().add("file:src/main/resources/css/menu.css");

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

        // Flèche de retour
        retour.setAlignment(Pos.BOTTOM_LEFT);
        retour.setPrefHeight(100);
        retour.setPrefWidth(100);
        retour.setGraphic(new ImageView(new Image("file:src/main/resources/img/fleche.png")));
        retour.setStyle("-fx-background-color: transparent");
        BorderPane.setMargin(retour, paddingTopLeft);
    }

    /**
     * Change la scène par celle du menu principal
     * @param vueMenuPrincipal vue où l'on souhaite retourner
     */
    public void setRetour(VueMenuPrincipal vueMenuPrincipal) {
        retour.setOnAction(e -> {
            vueMenuPrincipal.afficherMenuPrincipal();
            this.setScene(scene);
        });
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
     * @param pseudoCreation pseudo de l'utilisateur
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
     * @param pseudoConnexion pseudo de l'utilisateur
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
     * @return pseudo de l'utilisateur
     */
    public TextField getPseudo() {
        return pseudoConnexion.isDisabled() ? pseudoCreation : pseudoConnexion;
    }

    /**
     * Récupère le mot de passe de l'utilisateur
     * @return mot de passe de l'utilisateur
     */
    public PasswordField getMotDePasse() {
        return motDePasseConnexion.isDisabled() ? motDePasseCreation : motDePasseConnexion;
    }
}
