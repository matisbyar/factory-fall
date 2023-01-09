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


        /**
         * recuperation de tous les scores different
         */
        List<Score> topScore = ScoreManager.getInstance().getTopScore();

        List<Score> departementScore;
        System.out.println(Session.getInstance().isConnected());
        //TODO bug ici a fix
        if (Session.getInstance().isConnected()) {

            departementScore = ScoreManager.getInstance().getTopScoreParDepartement(DepartementManager.getInstance().getDepartementByLogin(Session.getInstance().getLogin()));
        } else {
            // cas de base qui ne sera jamais affiché mais nécessaire pour run la classe au debut
            departementScore = ScoreManager.getInstance().getTopScoreParDepartement("34");
        }


        List<Score> topScoreanonyme = ScoreManager.getInstance().getTopScoreAnonyme();


        // Récupération des scores top score
        for (int i = 1; i < 11; i++) {
            String login = "Anonyme";
            if (topScore.get(i - 1).getLogin() != null) {
                login = topScore.get(i - 1).getLogin();
            }
            classementTopScore.add(new Label(String.valueOf(i)), 0, i - 1);
            classementTopScore.add(new Label(login), 1, i - 1);
            classementTopScore.add(new Label(String.valueOf(topScore.get(i - 1).getScore())), 2, i - 1);
            classementTopScore.add(new Label(topScore.get(i - 1).getHorodatage().toString()), 3, i - 1);
        }

        // Récupération des scores par departements ou anonyme si le jouer n'est pas connecté
        /*
         ** important si le joueur est connecté cela prend les departement sinon les top anonyme
         */
        if (Session.getInstance().isConnected()) {
            int i = 1;
            while (i < 11 && departementScore.get(i - 1) != null) {

                String login = departementScore.get(i - 1).getLogin();

                classementDepartement.add(new Label(String.valueOf(i)), 0, i - 1);
                classementDepartement.add(new Label(login), 1, i - 1);
                classementDepartement.add(new Label(String.valueOf(departementScore.get(i - 1).getScore())), 2, i - 1);
                classementDepartement.add(new Label(departementScore.get(i - 1).getHorodatage().toString()), 3, i - 1);
                i++;
            }
        } else {


            for (int i = 1; i < 11; i++) {
                String login = "Anonyme";
                classementDepartement.add(new Label(String.valueOf(i)), 0, i - 1);
                classementDepartement.add(new Label(login), 1, i - 1);
                classementDepartement.add(new Label(String.valueOf(topScoreanonyme.get(i - 1).getScore())), 2, i - 1);
                classementDepartement.add(new Label(topScoreanonyme.get(i - 1).getHorodatage().toString()), 3, i - 1);
            }
        }


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
        changementDeClassement.setOnAction(actionEvent -> changedeclassement());
    }

    /**
     * gere les cas de changement des label du classement en fonction du button et de si on est connecté ou pas
     * <p>
     * bug connus : si on va voir le classement avant de se connecté et on se connecte et on refa voir le classement les vauvaise vue sont
     * affiché , pour fix il faut allez dans une autre vue(compte ) et revenir
     */
    private void changedeclassement() {

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

    @Override
    public void afficherScene() {
        this.setScene(scene);
    }


}
