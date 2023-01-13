package tetris.vues.menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tetris.logique.Score;
import tetris.singletons.Preferences;
import tetris.singletons.Ressources;
import tetris.stockage.DepartementManager;
import tetris.stockage.ScoreManager;
import tetris.stockage.Session;
import tetris.vues.Menu;
import tetris.vues.VueMenuPrincipal;
import tetris.vues.helpers.BarreNavigation;

import java.util.List;

public class VueClassement extends Stage implements Menu {

    private final BorderPane root;
    private final Scene scene;

    private final Label titre;
    private final VBox vbScores, bouton;
    private final GridPane classementTopScore, classementDepartement;

    private final Button changementDeClassement;

    public VueClassement(VueMenuPrincipal vueMenuPrincipal) {
        // Initialisations
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        titre = new Label("Meilleur Classement Généraux");

        classementTopScore = new GridPane();

        classementDepartement = new GridPane();

        changementDeClassement = new Button("vers top anonyme");

        vbScores = new VBox(titre, classementTopScore);

        bouton = new VBox(changementDeClassement);

        recupererClassements();

        // Styles et bindings
        styliser();
        creerBindings();

        // Affichage
        root.setCenter(vbScores);
        root.setTop(new BarreNavigation("Classement", vueMenuPrincipal, this));
        root.setRight(bouton);


        this.setScene(scene);
    }


    public void styliser() {
        // Root (BorderPane)
        root.setBackground(Preferences.getInstance().getBackground());

        // Titre
        titre.setFont(Ressources.getInstance().getPolice(32));
        titre.setStyle("-fx-text-fill: white;");
        titre.setAlignment(Pos.CENTER);

        // Classement
        classementTopScore.setHgap(20);
        classementTopScore.setVgap(20);
        classementTopScore.setAlignment(Pos.CENTER);

        classementDepartement.setHgap(20);
        classementDepartement.setVgap(20);
        classementDepartement.setAlignment(Pos.CENTER);

        for (int i = 0; i < classementTopScore.getChildren().size(); i++) {
            classementTopScore.getChildren().get(i).setStyle("-fx-text-fill: white");
        }

        for (int i = 0; i < classementDepartement.getChildren().size(); i++) {
            classementDepartement.getChildren().get(i).setStyle("-fx-text-fill: white");
        }

        //Button
        //changementdeclassement.setAlignment(Pos.TOP_CENTER); // n'est pas prioritaire par rapport au vbox

        // changementdeclassement.getStyleClass().add("bouton");
        // changementdeclassement.setStyle("-fx-background-color: black");
        changementDeClassement.getStyleClass().add("bouton-clair");
        changementDeClassement.setFont(Ressources.getInstance().getPolice(20));
        HBox.setMargin(changementDeClassement, new javafx.geometry.Insets(0, 50, 0, 0));
        // VBScores
        vbScores.setAlignment(Pos.CENTER);
    }

    public void creerBindings() {
        changementDeClassement.setOnAction(actionEvent -> changementDeClassement());
    }

    /**
     * gere les cas de changement des label du classement en fonction du button et de si on est connecté ou pas
     * <p>
     * bug connus : si on va voir le classement avant de se connecté et on se connecte et on refa voir le classement les vauvaise vue sont
     * affiché , pour fix il faut allez dans une autre vue(compte ) et revenir
     */
    private void changementDeClassement() {

        if (changementDeClassement.getText() == "vers top département" && Session.getInstance().isConnected()) {
            changementDeClassement.setText("vers top joueur");
            titre.setText("Meilleur Classement Dans Votre Département");
            vbScores.getChildren().remove(1);
            vbScores.getChildren().add(classementDepartement);

        } else if (changementDeClassement.getText() == "vers top joueur" && Session.getInstance().isConnected()) {
            changementDeClassement.setText("vers top département");
            titre.setText("Meilleur Classement Généraux");
            vbScores.getChildren().remove(1);
            vbScores.getChildren().add(classementTopScore);

        } else if (changementDeClassement.getText() == "vers top anonyme") {
            changementDeClassement.setText("vers top joueur");
            titre.setText("Meilleur Classement par Anonyme");
            vbScores.getChildren().remove(1);
            vbScores.getChildren().add(classementDepartement);
        } else {
            changementDeClassement.setText("vers top anonyme");
            titre.setText("Meilleur Classement Généraux");
            vbScores.getChildren().remove(1);
            vbScores.getChildren().add(classementTopScore);
        }
    }

    /**
     * Récupère tous les classements en un seul appel
     */
    private void recupererClassements() {
        recupererClassementGeneral();
        recupererClassementFiltres();
    }

    /**
     * Récupère le classement général
     */
    private void recupererClassementGeneral() {
        List<Score> topScore = ScoreManager.getInstance().getTopScores();

        // Récupération des scores top score
        for (int i = 1; i < 11; i++) {
            String login = "Anonyme";
            if (topScore.get(i - 1).getLogin() != null) {
                login = topScore.get(i - 1).getLogin();
            }
            listToGridPane(topScore, i, login, classementTopScore);
        }
    }

    /**
     * Récupère le classement depuis la base de données.
     * Si le joueur est connecté, on récupère le classement de son département.
     * Sinon, on récupère le classement des scores enregistrés en anonyme.
     */
    private void recupererClassementFiltres() {
        boolean estConnecte = Session.getInstance().isConnected();
        List<Score> topScores = estConnecte ? ScoreManager.getInstance().getTopScoresParDepartement(DepartementManager.getInstance().getDepartementByLogin(Session.getInstance().getLogin())) : ScoreManager.getInstance().getTopScoresAnonyme();
        for (int i = 1; i < 11; i++) {
            String login = estConnecte ? Session.getInstance().getLogin() : "Anonyme";
            listToGridPane(topScores, i, login, classementDepartement);
        }
    }

    /**
     * À partir de plusieurs paramètres, on transforme une liste de scores en un GridPane.
     *
     * @param scores   Liste de scores
     * @param indice   Ligne du GridPane
     * @param login    Login du joueur (anonyme si non connecté)
     * @param gridPane GridPane à remplir
     */
    private void listToGridPane(List<Score> scores, int indice, String login, GridPane gridPane) {
        gridPane.add(new Label(String.valueOf(indice)), 0, indice - 1);
        gridPane.add(new Label(login), 1, indice - 1);
        gridPane.add(new Label(String.valueOf(scores.get(indice - 1).getScore())), 2, indice - 1);
        gridPane.add(new Label(scores.get(indice - 1).getHorodatage().toString()), 3, indice - 1);
    }

    private void rafraichirClassements() {
        classementTopScore.getChildren().clear();
        classementDepartement.getChildren().clear();
        recupererClassements();
    }

    @Override
    public void afficherScene() {
        this.setScene(scene);
        mettreAJour();
    }

    public void mettreAJour() {
        root.setBackground(Preferences.getInstance().getBackground());
        rafraichirClassements();
    }
}
