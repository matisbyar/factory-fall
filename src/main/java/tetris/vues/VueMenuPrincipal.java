package tetris.vues;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import tetris.TetrisIHM;

import java.awt.event.ActionListener;

public class VueMenuPrincipal extends Stage {

    Scene sceneMenu;
    BorderPane borderPaneMenu;
    BorderPane borderPaneJoueur;
    Background background;
    Button lancerJeu;
    Button parametres;
    Button personnaliser;
    Button compte;
    Button retourToMenu = new Button();
    Button connexion = new Button();
    Button creationCompte = new Button();
    VBox vbJoueur;
    Scene sceneJoueur;

    javafx.geometry.Insets insets;

    /** Elements du menu creation joueur*/
    Scene sceneCreationJoueur;
    BorderPane borderPaneCreationJoueur;
    Button creejoueur = new Button();
    VBox vbCreationJoueur;

    /** Elements du menu connexion joueur*/

    Scene sceneConnexionJoueur;
    BorderPane borderPaneConnexion;
    Button connecterJoueur;
    TextField nomJoueur;
    TextField motDePasse;
    Button btConnexion = new Button();
    VBox vbConnexionJoueur;

    Button retourToJoueur = new Button();

    VBox bouttons;
    public VueMenuPrincipal() {
        background = new Background(new BackgroundImage(new Image("file:src/main/resources/img/background.jpg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1280,720, false, false, false, false)));
        borderPaneMenu = new BorderPane();

        lancerJeu = new Button();
        parametres = new Button();
        personnaliser = new Button();
        compte = new Button();
        bouttons = new VBox(lancerJeu, parametres, personnaliser);
        insets = new Insets(10, 10 ,10, 10);
        sceneMenu = new Scene(borderPaneMenu, 1280,720);

        styliserMenu();
        creerBindings();

        this.setScene(sceneMenu);
    }

    /**
     * Applique tous les styles souhaités aux objets JavaFX
     */
    public void styliserMenu() {
        // Général
        this.setTitle("Menu Principal");
        this.setResizable(false);

        VBox.setMargin(lancerJeu, insets);
        VBox.setMargin(parametres, insets);
        VBox.setMargin(personnaliser, insets);

        sceneMenu.getStylesheets().add("file:src/main/resources/css/mainMenuPrincipale.css");

        insets = new Insets(10, 10 ,10, 10);

        // BorderPane
        borderPaneMenu.setCenter(bouttons);
        borderPaneMenu.setBackground(background);
        borderPaneMenu.setBottom(compte);

        // Vbox Boutton
        bouttons.setAlignment(Pos.CENTER);

        // Button
        lancerJeu.setGraphic(new ImageView(new Image("file:src/main/resources/img/jouer.png")));
        lancerJeu.setStyle("-fx-background-color: black");
        lancerJeu.getStyleClass().add("bouttonLancerJeu");

        parametres.setGraphic(new ImageView(new Image("file:src/main/resources/img/parametres.png")));
        parametres.setStyle("-fx-background-color: black");

        personnaliser.setGraphic(new ImageView(new Image("file:src/main/resources/img/personnaliser.png")));
        personnaliser.setStyle("-fx-background-color: black");

        compte.setGraphic(new ImageView(new Image("file:src/main/resources/img/compte.png")));
        compte.setStyle("-fx-background-color: black");
    }

    /**
     * Listener interclasses qui permet à TetrisIHM de savoir quand le bouton start a été cliqué
     * @param quandLeButtonCompteEstClique listener passé en paramètre dans la classe TetrisIHM (voir la classe en question)
     */
    public void setButtonCompteCliqueListener(EventHandler<ActionEvent> quandLeButtonCompteEstClique) {
        compte.setOnAction(actionEvent -> testVueJoueur());
        //compte.setOnAction(quandLeButtonCompteEstClique);
    }

    public void setButtonJouerCliqueListener(EventHandler<ActionEvent> quandLeButtonJouerEstClique) {
        lancerJeu.setOnAction(quandLeButtonJouerEstClique);
    }

    public void setButtonConnecterJoueurCliqueListener(EventHandler<ActionEvent> joueurconnecte) {
        btConnexion.setOnAction(joueurconnecte);
    }

    public void setButtonCreerJoueurCliqueListener(EventHandler<ActionEvent>nouveaujoueurcree) {
        creejoueur.setOnAction(nouveaujoueurcree);
    }

    public void testVueJoueur() {
        borderPaneJoueur = new BorderPane();
        borderPaneJoueur.setBackground(background);
        sceneJoueur = new Scene(borderPaneJoueur, 1280,720);
        vbJoueur = new VBox(connexion, creationCompte);
        borderPaneJoueur.setCenter(vbJoueur);
        borderPaneJoueur.setTop(retourToMenu);
        vbJoueur.setAlignment(Pos.CENTER);
        styliserJoueur();
        creerBindings();
        this.setScene(sceneJoueur);
    }

    public void afficherMenuPrincipal() {
        this.setScene(sceneMenu);
    }

    public void afficherMenuJoueur() {
        this.setScene(sceneJoueur);
    }

    public void styliserJoueur() {
        sceneJoueur.getStylesheets().add("file:src/main/resources/css/mainVueCreationJoueur.css");

        VBox.setMargin(connexion, insets);
        VBox.setMargin(creationCompte, insets);

        connexion.setGraphic(new ImageView(new Image("file:src/main/resources/img/connexion.png")));
        connexion.setStyle("-fx-background-color: black");
        connexion.getStyleClass().add("bouttonConnecterJoueur");


        creationCompte.setGraphic(new ImageView(new Image("file:src/main/resources/img/creer_compte.png")));
        creationCompte.setStyle("-fx-background-color: black");
        creationCompte.getStyleClass().add("bouttonCreerJoueur");

        retourToMenu.setGraphic(new ImageView(new Image("file:src/main/resources/img/fleche.png")));
        retourToMenu.setStyle("-fx-background-color: transparent");
    }

    public void connexionJoueur() {
        borderPaneConnexion = new BorderPane();
        borderPaneConnexion.setBackground(background);
        creejoueur = new Button();
        nomJoueur = new  TextField();
        motDePasse = new TextField();
        vbConnexionJoueur = new VBox( nomJoueur, motDePasse, btConnexion);
        borderPaneConnexion.setTop(retourToJoueur);
        sceneConnexionJoueur = new Scene(borderPaneConnexion, 1280,720);
        styliserConnexionJoueur();
        this.setScene(sceneConnexionJoueur);
    }

    public void creationJoueur() {
        borderPaneCreationJoueur = new BorderPane();
        borderPaneCreationJoueur.setBackground(background);
        //creejoueur = new Button();
        nomJoueur = new  TextField();
        motDePasse = new TextField();
        vbCreationJoueur = new VBox( nomJoueur,motDePasse, creejoueur);
        borderPaneCreationJoueur.setTop(retourToJoueur);
        sceneCreationJoueur = new Scene(borderPaneCreationJoueur, 1280,720);
        styliserCreationJoueur();
        sceneCreationJoueur.getStylesheets().add("file:src/main/resources/css/mainMenuPrincipale.css");
        this.setScene(sceneCreationJoueur);
    }

    public void styliserCreationJoueur() {
        // Général
        this.setTitle("Menu Joueur");
        this.setResizable(false);
        sceneCreationJoueur.getStylesheets().add("file:src/main/resources/css/mainVueCreationJoueur.css");
        // BorderPane
        borderPaneCreationJoueur.setCenter(vbCreationJoueur);
        borderPaneCreationJoueur.setBackground(background);
        // Vbox
        vbCreationJoueur.setAlignment(Pos.CENTER);
        VBox.setMargin(nomJoueur, insets);
        VBox.setMargin(creejoueur, insets);
        //Texte
        nomJoueur.setPromptText("Entrer votre Pseudo");
        nomJoueur.getStyleClass().add("textFieldNomJoueur");
        motDePasse.setPromptText("Entrer votre mot de passe");
        motDePasse.getStyleClass().add("textFieldNomJoueur");
        // Button
        creejoueur.setGraphic(new ImageView(new Image("file:src/main/resources/img/creer_compte.png")));
        creejoueur.getStyleClass().add("bouttonCreerJoueur");

        retourToJoueur.setGraphic(new ImageView(new Image("file:src/main/resources/img/fleche.png")));
        retourToJoueur.setStyle("-fx-background-color: transparent");
    }

    public void styliserConnexionJoueur() {
        // Général
        this.setTitle("Menu Joueur");
        this.setResizable(false);
        sceneConnexionJoueur.getStylesheets().add("file:src/main/resources/css/mainVueCreationJoueur.css");
        // BorderPane
        borderPaneConnexion.setCenter(vbConnexionJoueur);
        borderPaneConnexion.setBackground(background);
        // Vbox
        vbConnexionJoueur.setAlignment(Pos.CENTER);
        // Set les marges des elements
        VBox.setMargin(nomJoueur, insets);
        VBox.setMargin(creejoueur, insets);
        //Texte
        nomJoueur.setPromptText("Entrer votre Pseudo");
        nomJoueur.getStyleClass().add("textFieldNomJoueur");
        motDePasse.setPromptText("Entrer votre mot de passe");
        motDePasse.getStyleClass().add("textFieldNomJoueur");
        // Button
        btConnexion.setGraphic(new ImageView(new Image("file:src/main/resources/img/connexion.png")));
        btConnexion.getStyleClass().add("bouttonCreerJoueur");

        retourToJoueur.setGraphic(new ImageView(new Image("file:src/main/resources/img/fleche.png")));
        retourToJoueur.setStyle("-fx-background-color: transparent");
    }

    public TextField getNomjoueur() {
        return nomJoueur;
    }

    public TextField getMotDePasse() {
        return motDePasse;
    }

    public void creerBindings() {
        compte.setOnAction(actionEvent -> testVueJoueur());
        retourToMenu.setOnAction(actionEvent -> afficherMenuPrincipal());
        connexion.setOnAction(actionEvent -> connexionJoueur());
        creationCompte.setOnAction(actionEvent -> creationJoueur());
        retourToJoueur.setOnAction(actionEvent -> afficherMenuJoueur());
    }
}
