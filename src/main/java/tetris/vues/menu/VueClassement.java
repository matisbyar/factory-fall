package tetris.vues.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.*;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tetris.IJeu;
import tetris.TetrisIHM;
import tetris.logique.Score;
import tetris.singletons.Preferences;
import tetris.singletons.Ressources;
import tetris.stockage.ScoreManager;
import tetris.stockage.Session;
import tetris.vues.Menu;
import tetris.vues.VueMenuPrincipal;
import tetris.vues.helpers.BarreNavigation;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

public class VueClassement extends Stage implements Menu {

    private final BorderPane root;
    private final Scene scene;

    private final VBox vbScores;
    private final HBox boutons;
    private final GridPane classementTopScore, classementFiltre;

    private final ToggleButton afficherTopScore, afficherFiltre;

    public VueClassement() {
        // Initialisations
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        classementTopScore = new GridPane();
        classementFiltre = new GridPane();

        ToggleGroup group = new ToggleGroup();
        afficherFiltre = new ToggleButton("Top Scores");
        afficherTopScore = new ToggleButton(Session.getInstance().isConnected() ? "Top Département" : "Top Anonymes");
        afficherTopScore.setSelected(true);
        afficherFiltre.setToggleGroup(group);
        afficherTopScore.setToggleGroup(group);

        boutons = new HBox(afficherTopScore, afficherFiltre);

        vbScores = new VBox(boutons, classementTopScore);

        recupererClassements();

        // Styles et bindings
        styliser();
        creerBindings();

        // Affichage
        root.setCenter(vbScores);
        root.setTop(new BarreNavigation("Classements", VueMenuPrincipal.getInstance(), this));

        this.setScene(scene);
    }


    public void styliser() {
        // Root (BorderPane)
        root.setBackground(Preferences.getInstance().getBackground());
        scene.getStylesheets().add(Objects.requireNonNull(TetrisIHM.class.getResource("css/main.css")).toString());

        // Classement
        classementTopScore.getStyleClass().add("classement");


        classementFiltre.getStyleClass().add("classement");

        for (int i = 0; i < classementTopScore.getChildren().size() - classementTopScore.getColumnCount(); i++) {
            ((Label) classementTopScore.getChildren().get(i)).setFont(Ressources.getInstance().getPolice(20));
            classementTopScore.getChildren().get(i).getStyleClass().remove(0);
            classementTopScore.getChildren().get(i).getStyleClass().add("textClassement");
        }

        for (int i = 0; i < classementFiltre.getChildren().size() - classementTopScore.getColumnCount(); i++) {
            classementFiltre.getChildren().get(i).setStyle("-fx-text-fill: white;");
        }

        afficherTopScore.getStyleClass().add("bouton-clair");
        afficherTopScore.setFont(Ressources.getInstance().getPolice(20));

        boutons.setAlignment(Pos.CENTER);

        afficherFiltre.getStyleClass().add("bouton-clair");
        afficherFiltre.setFont(Ressources.getInstance().getPolice(20));

        boutons.setPadding(new Insets(0, 0, 50, 0));

        // VBScores
        vbScores.setAlignment(Pos.CENTER);
    }

    public void creerBindings() {
        afficherTopScore.setOnAction(actionEvent -> changementDeClassement());
        afficherFiltre.setOnAction(actionEvent -> changementDeClassement());
    }

    /**
     * Gère l'action de changement de classement
     */
    private void changementDeClassement() {
        if (afficherTopScore.isSelected() && !vbScores.getChildren().contains(classementTopScore)) {
            vbScores.getChildren().remove(classementFiltre);
            vbScores.getChildren().add(classementTopScore);
        } else if (afficherFiltre.isSelected() && !vbScores.getChildren().contains(classementFiltre)) {
            vbScores.getChildren().remove(classementTopScore);
            vbScores.getChildren().add(classementFiltre);
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
        if (topScore.isEmpty()) {
            Label erreur = new Label("Aucun score enregistré");
            erreur.setFont(Ressources.getInstance().getPolice(25));
            classementTopScore.add(erreur, 0, 0);
        } else {
            Label loginLabel = new Label("Login");
            Label num = new Label("#");
            Label score = new Label("Score");
            Label date = new Label("Date");
            Label heure = new Label("Heure");
            loginLabel.setFont(Ressources.getInstance().getPolice(25));
            score.setFont(Ressources.getInstance().getPolice(25));
            date.setFont(Ressources.getInstance().getPolice(25));
            num.setFont(Ressources.getInstance().getPolice(25));
            heure.setFont(Ressources.getInstance().getPolice(25));
            classementTopScore.add(num, 0, 0);
            classementTopScore.add(loginLabel, 1, 0);
            classementTopScore.add(score, 2, 0);
            classementTopScore.add(date, 3, 0);
            classementTopScore.add(heure, 4, 0);
            // Récupération des scores top score
            for (int i = 1; i < 11; i++) {
                String login = "Anonyme";
                if (topScore.get(i - 1).getLogin() != null) {
                    login = topScore.get(i - 1).getLogin();
                }
                listToGridPane(topScore, i, login, classementTopScore);
            }
        }
    }

    /**
     * Récupère le classement depuis la base de données.
     * Si le joueur est connecté, on récupère le classement de son département.
     * Sinon, on récupère le classement des scores enregistrés en anonyme.
     */
    private void recupererClassementFiltres() {
        boolean estConnecte = Session.getInstance().isConnected();
        List<Score> topScores = estConnecte ? ScoreManager.getInstance().getTopScoresParDepartement(Session.getInstance().getDepartement()) : ScoreManager.getInstance().getTopScoresAnonyme();
        if (topScores.isEmpty()) {
            Label erreur = new Label("Aucun score enregistré");
            erreur.setFont(Ressources.getInstance().getPolice(25));
            classementFiltre.add(erreur, 0, 0);
        } else {
            Label loginLabel = new Label("Login");
            Label num = new Label("#");
            Label score = new Label("Score");
            Label date = new Label("Date");
            Label heure = new Label("Heure");
            loginLabel.setFont(Ressources.getInstance().getPolice(25));
            score.setFont(Ressources.getInstance().getPolice(25));
            date.setFont(Ressources.getInstance().getPolice(25));
            num.setFont(Ressources.getInstance().getPolice(25));
            heure.setFont(Ressources.getInstance().getPolice(25));
            classementTopScore.add(num, 0, 0);
            classementTopScore.add(loginLabel, 1, 0);
            classementTopScore.add(score, 2, 0);
            classementTopScore.add(date, 3, 0);
            classementTopScore.add(heure, 4, 0);
            classementFiltre.add(num, 0, 0);
            classementFiltre.add(loginLabel, 1, 0);
            classementFiltre.add(score, 2, 0);
            classementFiltre.add(date, 3, 0);
            classementFiltre.add(heure, 4, 0);
            for (int j = 1; j < 11 && j <= topScores.size() - 1; j++) {
                String login = estConnecte ? topScores.get(j - 1).getLogin() : "Anonyme";
                listToGridPane(topScores, j, login, classementFiltre);
            }
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
        Label num = new Label(String.valueOf(indice));
        Label loginLabel = new Label(login);
        Label score = new Label(String.valueOf(scores.get(indice - 1).getScore()));
        Label date = new Label(new SimpleDateFormat("dd/MM/yyyy").format(scores.get(indice - 1).getHorodatage()));
        Label heure = new Label(new SimpleDateFormat("HH:mm").format(scores.get(indice - 1).getHorodatage()));
        loginLabel.setFont(Ressources.getInstance().getPolice(20));
        score.setFont(Ressources.getInstance().getPolice(20));
        date.setFont(Ressources.getInstance().getPolice(20));
        num.setFont(Ressources.getInstance().getPolice(20));
        heure.setFont(Ressources.getInstance().getPolice(20));
        gridPane.add(num, 0, indice);
        gridPane.add(loginLabel, 1, indice);
        gridPane.add(score, 2, indice);
        gridPane.add(date, 3, indice);
        gridPane.add(heure, 4, indice);
    }

    private void rafraichirClassements() {
        classementTopScore.getChildren().clear();
        classementFiltre.getChildren().clear();
        recupererClassements();
    }

    @Override
    public void afficherScene() {
        mettreAJour();
        this.setScene(scene);
    }

    public void mettreAJour() {
        root.setBackground(Preferences.getInstance().getBackground());
        rafraichirClassements();

    }
}
