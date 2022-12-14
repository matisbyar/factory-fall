package tetris.vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class VueMenuPrincipal extends Stage {

    private final Scene sceneMenu;
    private final BorderPane borderPaneMenu;
    private BorderPane borderPaneJoueur;
    private final Background background;
    private final Button lancerJeu;
    private final Button parametres;
    private final Button personnaliser;
    private final Button compte;
    private final Button retourToMenu = new Button();
    private final Button connexion = new Button();
    private final Button creationCompte = new Button();
    private VBox vbJoueur;
    private Scene sceneJoueur;

    private javafx.geometry.Insets insets;

    /**
     * Elements du menu creation joueur
     */
    private Scene sceneCreationJoueur;
    private BorderPane borderPaneCreationJoueur;
    private Button creejoueur = new Button();
    private VBox vbCreationJoueur;

    /**
     * Elements du menu connexion joueur
     */

    private Scene sceneConnexionJoueur;
    private BorderPane borderPaneConnexion;
    private TextField nomJoueur;
    private TextField motDePasse;
    private final Button btConnexion = new Button();
    private VBox vbConnexionJoueur;
    private final Button retourToJoueur = new Button();
    private BorderPane borderPanePerso;
    private Scene scenePerso;
    private final VBox bouttons;
    private VBox vbPerso = new VBox();
    private HBox hbPerso = new HBox();
    private final Button flecheD = new Button();
    private final Button flecheG = new Button();
    private final Image pieceDefault = new Image("file:src/main/resources/img/default/L.jpg");
    private final Image pieceConteneur = new Image("file:src/main/resources/img/conteneur/L.jpg");
    private final Image pieceBrique = new Image("file:src/main/resources/img/brique/L.jpg");
    private final ImageView pieceEnCoursPerso = new ImageView(pieceConteneur);

    private String dossierImg = "conteneur";
    private int i = 0;


     private BorderPane borderPanetableau;

     private Button tableau = new Button();

     private VBox vbtableau = new VBox();

    public VueMenuPrincipal() {
        background = new Background(new BackgroundImage(new Image("file:src/main/resources/img/background/classic.jpg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1280, 720, false, false, false, false)));
        borderPaneMenu = new BorderPane();

        lancerJeu = new Button();
        parametres = new Button();
        personnaliser = new Button();
        compte = new Button();
        tableau = new Button();
        bouttons = new VBox(lancerJeu, parametres, personnaliser);
        insets = new Insets(10, 10, 10, 10);
        sceneMenu = new Scene(borderPaneMenu, 1280, 720);



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
        styliser();
        sceneMenu.getStylesheets().add("file:src/main/resources/css/mainMenuPrincipale.css");

        VBox.setMargin(lancerJeu, insets);
        VBox.setMargin(parametres, insets);
        VBox.setMargin(personnaliser, insets);

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
        parametres.getStyleClass().add("parametre");

        personnaliser.setGraphic(new ImageView(new Image("file:src/main/resources/img/personnaliser.png")));
        personnaliser.setStyle("-fx-background-color: black");
        personnaliser.getStyleClass().add("personnalite");


        compte.setGraphic(new ImageView(new Image("file:src/main/resources/img/compte.png")));
        compte.setStyle("-fx-background-color: black");



        borderPaneMenu.setTop(tableau);
        tableau.setGraphic(new Label("tableau"));
        tableau.setStyle("-fx-background-color: black");
    }

    /**
     * Fonction qui déclenche le lancement du jeu en Anonyme dans TetrisIHM
     */
    public void setButtonJouerCliqueListener(EventHandler<ActionEvent> quandLeButtonJouerEstClique) {
        lancerJeu.setOnAction(quandLeButtonJouerEstClique);
    }

    /**
     * Fonction qui déclenche le lancement du jeu après la connexion d'un utilisateur dans TetrisIHM
     */
    public void setButtonConnecterJoueurCliqueListener(EventHandler<ActionEvent> joueurconnecte) {
        btConnexion.setOnAction(joueurconnecte);
    }

    /**
     * Fonction qui déclenche le lancement du jeu après la création d'un nouvel utilisateur dans TetrisIHM
     */
    public void setButtonCreerJoueurCliqueListener(EventHandler<ActionEvent> nouveaujoueurcree) {
        creejoueur.setOnAction(nouveaujoueurcree);
    }

    public void setButtonTableauDesScoreListener(EventHandler<ActionEvent> tableaudesscore) {
        tableau.setOnAction(tableaudesscore);
    }

    /**
     * Fonction qui sert de refactoring a la vue ChoixJoueur
     */
    public void choixJoueur() {
        setTitleJoueur();
        borderPaneJoueur = new BorderPane();
        borderPaneJoueur.setBackground(background);
        sceneJoueur = new Scene(borderPaneJoueur, 1280, 720);
        sceneJoueur.getStylesheets().add("file:src/main/resources/css/mainVueCreationJoueur.css");
        vbJoueur = new VBox(connexion, creationCompte);
        borderPaneJoueur.setCenter(vbJoueur);
        borderPaneJoueur.setTop(retourToMenu);
        vbJoueur.setAlignment(Pos.CENTER);
        styliserJoueur();
        creerBindings();
        this.setScene(sceneJoueur);
    }

    /**
     * Fonction qui permet d'afficher le menu principal (utilisé lors d'un appui sur le bouton retour)
     */
    public void afficherMenuPrincipal() {
        this.setTitle("Menu Principal");
        this.setScene(sceneMenu);
    }

    /**
     * Fonction qui permet d'afficher le menu choix joueur (utilisé lors d'un appui sur le bouton retour de connexion et création de compte)
     */
    public void afficherMenuJoueur() {
        this.setTitle("Menu Joueur");
        this.setScene(sceneJoueur);
    }

    public void styliserJoueur() {
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

    /**
     * Fonction qui sert de refactoring a la vue ConnexionJoueur
     */
    public void connexionJoueur() {
        borderPaneConnexion = new BorderPane();
        borderPaneConnexion.setBackground(background);
        creejoueur = new Button();
        nomJoueur = new TextField();
        motDePasse = new TextField();
        vbConnexionJoueur = new VBox(nomJoueur, motDePasse, btConnexion);
        borderPaneConnexion.setTop(retourToJoueur);
        sceneConnexionJoueur = new Scene(borderPaneConnexion, 1280, 720);
        sceneConnexionJoueur.getStylesheets().add("file:src/main/resources/css/mainVueCreationJoueur.css");
        styliserConnexionJoueur();
        this.setScene(sceneConnexionJoueur);
    }

    /**
     * Fonction qui sert de refactoring a la vue CreationJoueur
     */
    public void creationJoueur() {
        setTitleJoueur();
        borderPaneCreationJoueur = new BorderPane();
        borderPaneCreationJoueur.setBackground(background);
        //creejoueur = new Button();
        nomJoueur = new TextField();
        motDePasse = new TextField();
        vbCreationJoueur = new VBox(nomJoueur, motDePasse, creejoueur);
        borderPaneCreationJoueur.setTop(retourToJoueur);
        sceneCreationJoueur = new Scene(borderPaneCreationJoueur, 1280, 720);
        styliserCreationJoueur();
        this.setScene(sceneCreationJoueur);
    }

    public void styliserCreationJoueur() {
        setTitleJoueur();
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

    /**
     * Fonction pour set le titre de la vue (évite la duplication de code)
     */
    public void setTitleJoueur() {
        this.setTitle("Menu Joueur");
    }

    public void styliserConnexionJoueur() {
        setTitleJoueur();
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

    /**
     * Getter utile pour la récupération dans TetrisIHM
     */
    public TextField getNomjoueur() {
        return nomJoueur;
    }

    /**
     * Getter utile pour la récupération dans TetrisIHM
     */
    public TextField getMotDePasse() {
        return motDePasse;
    }

    /**
     * Fonction qui créer tous les bindings in line utile pour l'ensemble des boutons du menu
     */
    public void creerBindings() {
        compte.setOnAction(actionEvent -> choixJoueur());
        retourToMenu.setOnAction(actionEvent -> afficherMenuPrincipal());
        connexion.setOnAction(actionEvent -> connexionJoueur());
        creationCompte.setOnAction(actionEvent -> creationJoueur());
        retourToJoueur.setOnAction(actionEvent -> afficherMenuJoueur());
        personnaliser.setOnAction(actionEvent -> personnalisation());
        tableau.setOnAction(actionEvent ->tableauScore());
        flecheD.setOnAction(actionEvent -> changerImage("+"));
        flecheG.setOnAction(actionEvent -> changerImage("-"));
    }

    /**
     * Fonction qui simule une VuePersonnalisation
     */
    public void personnalisation() {
        this.setTitle("Menu Personnalisation");
        borderPanePerso = new BorderPane();
        borderPanePerso.setBackground(background);
        borderPanePerso.setTop(retourToMenu);
        hbPerso = new HBox();
        vbPerso = new VBox();

        flecheD.setGraphic(new ImageView(new Image("file:src/main/resources/img/flechePersonnalisation/flecheD.png")));
        flecheG.setGraphic(new ImageView(new Image("file:src/main/resources/img/flechePersonnalisation/flecheG.png")));

        hbPerso.getChildren().addAll(flecheG, pieceEnCoursPerso, flecheD);
        vbPerso.getChildren().add(hbPerso);
        borderPanePerso.setCenter(vbPerso);
        styliserPerso();
        scenePerso = new Scene(borderPanePerso, 1280, 720);
        this.setScene(scenePerso);
    }

    public void styliserPerso() {
        retourToMenu.setGraphic(new ImageView(new Image("file:src/main/resources/img/fleche.png")));
        retourToMenu.setStyle("-fx-background-color: transparent");

        vbPerso.setAlignment(Pos.CENTER);
        hbPerso.setAlignment(Pos.CENTER);

        flecheD.setStyle("-fx-background-color: black");
        flecheG.setStyle("-fx-background-color: black");

        HBox.setMargin(flecheG, insets);
        HBox.setMargin(pieceEnCoursPerso, insets);
        HBox.setMargin(flecheD, insets);
    }


    public void tableauScore() {
        this.setTitle("Menu tableau");
        borderPanetableau = new BorderPane();
        borderPanetableau.setBackground(background);
        borderPanetableau.setTop(retourToMenu);

        vbtableau = new VBox();

        //  objet tableau a add
        //vbtableau.getChildren().add(onjet tableau);
        borderPanetableau.setLeft(vbtableau);
        styliserTableau();


        Scene scenetableau = new Scene(borderPanetableau, 1280, 720);
        this.setScene(scenetableau);
    }

    public void styliserTableau() {
        retourToMenu.setGraphic(new ImageView(new Image("file:src/main/resources/img/fleche.png")));
        retourToMenu.setStyle("-fx-background-color: transparent");

        vbPerso.setAlignment(Pos.CENTER);


       // + tout les config pour l'objet tableau


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
                dossierImg = "conteneur";
            }
            case 1 -> {
                pieceEnCoursPerso.setImage(pieceBrique);
                dossierImg = "brique";
            }
            case 2 -> {
                pieceEnCoursPerso.setImage(pieceDefault);
                dossierImg = "default";
            }
        }
    }

    /**
     * Getter pour TetrisIHM utile pour le constructeur des pièces dans les plateaux
     */
    public String getDossierImg() {
        return dossierImg;
    }

    public void styliser() {
        this.setResizable(false);
        insets = new Insets(10, 10, 10, 10);
    }


}
