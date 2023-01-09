package tetris.vues.menu.compte;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tetris.TetrisIHM;
import tetris.logique.Preferences;
import tetris.logique.Score;
import tetris.stockage.ScoreManager;
import tetris.stockage.Session;
import tetris.vues.Menu;
import tetris.vues.VueMenuPrincipal;
import tetris.vues.helpers.BarreNavigation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VueCompteConnecte extends Stage implements Menu {

    private final BorderPane root;
    private final Scene scene;
    private final GridPane classementtopscorejoueurconnecte;
    private final VBox vbScores;
    private final Label titre;
    public VueCompteConnecte(VueMenuPrincipal vueMenuPrincipal) {
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        titre = new Label("Vos Meilleur Score");

        classementtopscorejoueurconnecte = new GridPane();

        vbScores = new VBox(titre,classementtopscorejoueurconnecte);


        if(Session.getInstance().isConnected()) {
            List<Score> topScorejoueur = ScoreManager.getInstance().getTopScoreparlogin(Session.getInstance().getLogin());
            int i = 1;
            String login = topScorejoueur.get(i).getLogin();
            while (i < 11 && topScorejoueur.get(i - 1) != null) {

                classementtopscorejoueurconnecte.add(new Label(String.valueOf(i)), 0, i - 1);
                classementtopscorejoueurconnecte.add(new Label(login), 1, i - 1);
                classementtopscorejoueurconnecte.add(new Label(String.valueOf(topScorejoueur.get(i - 1).getScore())), 2, i - 1);
                classementtopscorejoueurconnecte.add(new Label(topScorejoueur.get(i - 1).getHorodatage().toString()), 3, i - 1);
                i++;
            }
        }

        // Styles et bindings
        styliser();

        // Affichage
        root.setTop(new BarreNavigation("Compte", vueMenuPrincipal, this));
        root.setCenter(vbScores);


        this.setScene(scene);
    }

    protected void styliser() {
        //Titre
        titre.setFont(Preferences.getInstance().getPolice(32));
        titre.setStyle("-fx-text-fill: white;");
        titre.setAlignment(Pos.CENTER);
        //Classement
        classementtopscorejoueurconnecte.setHgap(20);
        classementtopscorejoueurconnecte.setVgap(20);
        classementtopscorejoueurconnecte.setAlignment(Pos.CENTER);

        for (int i = 0; i < classementtopscorejoueurconnecte.getChildren().size(); i++) {
            classementtopscorejoueurconnecte.getChildren().get(i).setStyle("-fx-text-fill: black");
        }
        // Root (BorderPane)
        root.setBackground(Preferences.getInstance().getBackground());
        // Scene
        scene.getStylesheets().add(Objects.requireNonNull(TetrisIHM.class.getResource("css/main.css")).toString());
        //gridpane
        vbScores.setAlignment(Pos.CENTER);
    }

    @Override
    public void afficherScene() {
        this.setScene(scene);
    }
}
