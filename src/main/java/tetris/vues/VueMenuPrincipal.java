package tetris.vues;

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
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tetris.TetrisIHM;
import tetris.logique.Preferences;
import tetris.logique.Score;
import tetris.stockage.ScoreManager;

import java.util.List;

public class VueMenuPrincipal extends Stage {

    private final Font police = Font.loadFont("file:src/main/resources/fonts/Bazaroni.ttf", 32);
    private ImageView titreMenu;
    private final Scene sceneMenu;
    private final BorderPane borderPaneMenu;
    private BorderPane borderPaneJoueur;
    private final Background background;
    private final Button lancerJeu;
    private final Button parametres;
    private final Button personnaliser;
    private final Button compte;
    private final Button retourToMenu = new Button();
    private final Button quitter = new Button();
    private final Button connexion = new Button();
    private final Button creationCompte = new Button();
    private VBox vbJoueur;
    private Scene sceneJoueur;

    private javafx.geometry.Insets insets;
    private javafx.geometry.Insets insets2;
    private javafx.geometry.Insets insets3;

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
    private PasswordField motDePasse;
    private final Button btConnexion = new Button();
    private VBox vbConnexionJoueur;
    private final Button retourToJoueur = new Button();
    private BorderPane borderPanePerso;
    private Scene scenePerso;
    private final VBox bouttons;
    private final VBox vbCompte;
    private VBox vbRetourMenu;
    private VBox vbRetourJoueur;
    private VBox vbPerso = new VBox();
    private HBox hbPerso = new HBox();
    private final Button flecheD = new Button();
    private final Button flecheG = new Button();
    private final Image pieceDefault = new Image("file:src/main/resources/img/default/L.jpg");
    private final Image pieceConteneur = new Image("file:src/main/resources/img/container/L.jpg");
    private final Image pieceBrique = new Image("file:src/main/resources/img/brique/L.jpg");
    private final ImageView pieceEnCoursPerso = new ImageView(pieceConteneur);

    //private String dossierImg = "conteneur";
    Preferences preferences = Preferences.getInstance();
    private int i = 0;

    /**
     * Elements concernant le classement
     */
    private BorderPane borderPaneClassement;
    private Button boutonClassement;
    private GridPane classement = new GridPane();

    public VueMenuPrincipal() {
        background = new Background(new BackgroundImage(new Image("file:src/main/resources/img/background/classic.jpg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1280, 720, false, false, false, false)));
        borderPaneMenu = new BorderPane();

        titreMenu = new ImageView(new Image("file:src/main/resources/img/titreMenu.png"));

        lancerJeu = new Button();
        parametres = new Button();
        personnaliser = new Button();
        compte = new Button();
        vbCompte = new VBox(compte);

        boutonClassement = new Button();

        bouttons = new VBox(titreMenu, lancerJeu, parametres, personnaliser, boutonClassement, quitter);
        insets = new Insets(10, 10, 10, 10);
        insets2 = new Insets(100, 0, 30, 0);
        insets3 = new Insets(30, 0, 30, 80);
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
        VBox.setMargin(boutonClassement, insets);
        VBox.setMargin(titreMenu, insets2);
        VBox.setMargin(compte, insets3);


        // BorderPane
        borderPaneMenu.setCenter(bouttons);
        borderPaneMenu.setBackground(background);
        borderPaneMenu.setBottom(vbCompte);

        // Vbox Boutton
        bouttons.setAlignment(Pos.TOP_CENTER);

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

        quitter.setText("QUITTER");
        quitter.setStyle("-fx-background-color: black");


        vbCompte.setAlignment(Pos.CENTER_LEFT);
        vbCompte.setPrefWidth(200);
        vbCompte.setPrefHeight(200);
        compte.setGraphic(new ImageView(new Image("file:src/main/resources/img/compte.png")));
        compte.setStyle("-fx-background-color: black");
        compte.getStyleClass().add("compte");

        boutonClassement.setGraphic(new ImageView(new Image("file:src/main/resources/img/classement.png")));
        boutonClassement.setStyle("-fx-background-color: black");
        boutonClassement.getStyleClass().add("classement");
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
        boutonClassement.setOnAction(tableaudesscore);
    }

    public void setButtonQuitterListener(EventHandler<ActionEvent> quitterAction) {
        quitter.setOnAction(quitterAction);
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

        vbRetourMenu = new VBox(retourToMenu);

        borderPaneJoueur.setCenter(vbJoueur);
        borderPaneJoueur.setTop(vbRetourMenu);

        insets2 = new Insets(200, 0, 0, 0);
        insets3 = new Insets(0, 50, 0, 75);

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
        VBox.setMargin(retourToMenu, insets3);
        VBox.setMargin(connexion, insets2);


        vbJoueur.setAlignment(Pos.TOP_CENTER);

        connexion.setGraphic(new ImageView(new Image("file:src/main/resources/img/connexion.png")));
        connexion.setStyle("-fx-background-color: black");
        connexion.getStyleClass().add("bouttonConnecterJoueur");

        creationCompte.setGraphic(new ImageView(new Image("file:src/main/resources/img/creer_compte.png")));
        creationCompte.setStyle("-fx-background-color: black");
        creationCompte.getStyleClass().add("bouttonCreerJoueur");


        vbRetourMenu.setAlignment(Pos.BOTTOM_LEFT);
        vbRetourMenu.setPrefWidth(100);
        vbRetourMenu.setPrefHeight(100);
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
        motDePasse = new PasswordField();
        vbConnexionJoueur = new VBox(nomJoueur, motDePasse, btConnexion);
        vbRetourJoueur = new VBox(retourToJoueur);
        borderPaneConnexion.setTop(vbRetourJoueur);
        insets2 = new Insets(200, 0, 0, 0);
        insets3 = new Insets(0, 50, 0, 75);
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
        motDePasse = new PasswordField();
        vbCreationJoueur = new VBox(nomJoueur, motDePasse, creejoueur);
        vbRetourJoueur = new VBox(retourToJoueur);
        borderPaneCreationJoueur.setTop(vbRetourJoueur);
        insets2 = new Insets(200, 0, 0, 0);
        insets3 = new Insets(0, 50, 0, 75);
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
        vbCreationJoueur.setAlignment(Pos.TOP_CENTER);
        VBox.setMargin(nomJoueur, insets);
        VBox.setMargin(creejoueur, insets);
        VBox.setMargin(retourToJoueur, insets3);
        VBox.setMargin(nomJoueur, insets2);
        //Texte
        nomJoueur.setPromptText("Entrer votre Pseudo");
        nomJoueur.getStyleClass().add("textFieldNomJoueur");
        motDePasse.setPromptText("Entrer votre mot de passe");
        motDePasse.getStyleClass().add("textFieldNomJoueur");
        // Button
        creejoueur.setGraphic(new ImageView(new Image("file:src/main/resources/img/creer_compte.png")));
        creejoueur.getStyleClass().add("bouttonCreerJoueur");


        vbRetourJoueur.setAlignment(Pos.BOTTOM_LEFT);
        vbRetourJoueur.setPrefWidth(100);
        vbRetourJoueur.setPrefHeight(100);
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
        vbConnexionJoueur.setAlignment(Pos.TOP_CENTER);
        // Set les marges des elements
        VBox.setMargin(nomJoueur, insets);
        VBox.setMargin(creejoueur, insets);
        VBox.setMargin(retourToJoueur, insets3);
        VBox.setMargin(nomJoueur, insets2);
        //Texte
        nomJoueur.setPromptText("Entrer votre Pseudo");
        nomJoueur.getStyleClass().add("textFieldNomJoueur");
        motDePasse.setPromptText("Entrer votre mot de passe");
        motDePasse.getStyleClass().add("textFieldNomJoueur");
        // Button
        btConnexion.setGraphic(new ImageView(new Image("file:src/main/resources/img/connexion.png")));
        btConnexion.getStyleClass().add("bouttonCreerJoueur");


        vbRetourJoueur.setAlignment(Pos.BOTTOM_LEFT);
        vbRetourJoueur.setPrefWidth(100);
        vbRetourJoueur.setPrefHeight(100);
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
    public PasswordField getMotDePasse() {
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
        boutonClassement.setOnAction(actionEvent -> tableauScore());
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
        borderPaneClassement = new BorderPane();
        borderPaneClassement.setBackground(background);
        borderPaneClassement.setTop(retourToMenu);

        List<Score> listeScore = ScoreManager.getInstance().getTopScore();
        classement = new GridPane();

        for (int i = 1; i < listeScore.size(); i++) {
            String login = "Anonyme";
            if (listeScore.get(i - 1).getLogin() != null) {
                login = listeScore.get(i - 1).getLogin();
            }
            System.out.println(listeScore.get(i - 1).getLogin());

            classement.add(new Label(String.valueOf(i)), 0, i - 1);
            classement.add(new Label(login), 1, i - 1);
            classement.add(new Label(String.valueOf(listeScore.get(i - 1).getScore())), 2, i - 1);
            classement.add(new Label(listeScore.get(i - 1).getHorodatage().toString()), 3, i - 1);
        }

        borderPaneClassement.setCenter(classement);
        styliserTableau();

        Scene scenetableau = new Scene(borderPaneClassement, 1280, 720);
        this.setScene(scenetableau);
    }

    public void styliserTableau() {
        retourToMenu.setGraphic(new ImageView(new Image("file:src/main/resources/img/fleche.png")));
        retourToMenu.setStyle("-fx-background-color: transparent");

        classement.setAlignment(Pos.CENTER);
        classement.setVgap(30);
        classement.setHgap(15);

        for (int i = 0; i < classement.getChildren().size(); i++) {
            classement.getChildren().get(i).setStyle("-fx-text-fill: white");
        }
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
                preferences.setStylePiece("container");
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

    public void styliser() {
        this.setResizable(false);
        insets = new Insets(10, 10, 10, 10);
    }
}
