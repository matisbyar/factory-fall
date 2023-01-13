package tetris.vues.menu.compte;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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

public class VueCompteConnecte extends Stage implements Menu {

    private final BorderPane root;
    private final Scene scene;
    private final GridPane classement;
    private final VBox container, vbScores;
    private final Label titre;

    public VueCompteConnecte(VueMenuPrincipal vueMenuPrincipal) {
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        titre = new Label("Vos Meilleurs Scores");

        classement = new GridPane();

        vbScores = new VBox(titre, classement);
        container = new VBox(vbScores);

        recupererClassement();

        // Styles et bindings
        styliser();

        // Affichage
        root.setTop(new BarreNavigation("Compte", vueMenuPrincipal, this));
        root.setCenter(container);


        this.setScene(scene);
    }

    protected void styliser() {
        // Root (BorderPane)
        root.setBackground(Preferences.getInstance().getBackground());

        //Titre
        titre.setFont(Ressources.getInstance().getPolice(32));
        titre.setStyle("-fx-text-fill: white;");
        titre.setAlignment(Pos.TOP_CENTER);

        //Classement
        classement.setHgap(20);
        classement.setVgap(20);
        classement.setAlignment(Pos.TOP_CENTER);

        for (int i = 0; i < classement.getChildren().size(); i++) {
            classement.getChildren().get(i).setStyle("-fx-text-fill: white");
        }

        // Scene
        scene.getStylesheets().add(Objects.requireNonNull(TetrisIHM.class.getResource("css/main.css")).toString());

        // Panel classement (VBox contenant classement)
        container.getStyleClass().add("classement");
        container.setAlignment(Pos.TOP_CENTER);
        container.setPadding(new Insets(20));

        // Classement (VBox)
        vbScores.getStyleClass().add("panel-classement");
        vbScores.setAlignment(Pos.TOP_CENTER);
    }

    protected void recupererClassement() {
        if (Session.getInstance().isConnected()) {
            List<Score> scores = ScoreManager.getInstance().getTopScoreParLogin(Session.getInstance().getLogin());
            if (scores.isEmpty()) {
                classement.add(new Label("Aucun score enregistré"), 0, 0);
            } else {
                classement.add(new Label("#"), 0, 0);
                classement.add(new Label("Login"), 1, 0);
                classement.add(new Label("Score"), 2, 0);
                classement.add(new Label("Date"), 3, 0);
                classement.add(new Label("Heure"), 4, 0);
                for (int indice = 1; indice < 11 && indice <= scores.size(); indice++) {
                    classement.add(new Label(String.valueOf(indice - 1)), 0, indice);
                    classement.add(new Label(Session.getInstance().getLogin()), 1, indice);
                    classement.add(new Label(String.valueOf(scores.get(indice - 1).getScore())), 2, indice);
                    classement.add(new Label(new SimpleDateFormat("dd/MM/yyyy").format(scores.get(indice - 1).getHorodatage())), 3, indice);
                    classement.add(new Label(new SimpleDateFormat("HH:mm").format(scores.get(indice - 1).getHorodatage())), 4, indice);
                }
            }
        }
    }

    @Override
    public void afficherScene() {
        this.setScene(scene);
        mettreAJour();
    }

    public void mettreAJour() {
        root.setBackground(Preferences.getInstance().getBackground());
        classement.getChildren().clear();
        recupererClassement();
    }
}
