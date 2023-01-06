package tetris.vues.menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tetris.logique.Preferences;
import tetris.logique.Score;
import tetris.stockage.ScoreManager;
import tetris.vues.Menu;
import tetris.vues.VueMenuPrincipal;
import tetris.vues.helpers.BarreNavigation;

import java.util.List;

public class VueClassement extends Stage implements Menu {

    private final BorderPane root;
    private final Scene scene;

    private final Label titre;
    private final VBox vbScores;
    private final GridPane classement;

    public VueClassement(VueMenuPrincipal vueMenuPrincipal) {
        // Initialisations
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        titre = new Label("Classement");

        classement = new GridPane();

        vbScores = new VBox(titre, classement);

        List<Score> scores = ScoreManager.getInstance().getTopScore();


        // Récupération des scores
        for (int i = 1; i < 11; i++) {
            String login = "Anonyme";
            if (scores.get(i - 1).getLogin() != null) {
                login = scores.get(i - 1).getLogin();
            }
            System.out.println(scores.get(i - 1).getLogin());

            classement.add(new Label(String.valueOf(i)), 0, i - 1);
            classement.add(new Label(login), 1, i - 1);
            classement.add(new Label(String.valueOf(scores.get(i - 1).getScore())), 2, i - 1);
            classement.add(new Label(scores.get(i - 1).getHorodatage().toString()), 3, i - 1);
        }

        // Styles et bindings
        styliser();

        // Affichage
        root.setCenter(vbScores);
        root.setTop(new BarreNavigation("Classement", vueMenuPrincipal, this));

        this.setScene(scene);
    }


    public void styliser() {
        // Root (BorderPane)
        root.setBackground(Preferences.getInstance().getBackground());

        // Titre
        titre.setFont(Preferences.getInstance().getPolice(32));
        titre.setStyle("-fx-text-fill: white;");
        titre.setAlignment(Pos.CENTER);

        // Classement
        classement.setHgap(20);
        classement.setVgap(20);
        classement.setAlignment(Pos.CENTER);
        for (int i = 0; i < classement.getChildren().size(); i++) {
            classement.getChildren().get(i).setStyle("-fx-text-fill: white");
        }

        // VBScores
        vbScores.setAlignment(Pos.CENTER);
    }

    @Override
    public void afficherScene() {
        this.setScene(scene);
    }
}
