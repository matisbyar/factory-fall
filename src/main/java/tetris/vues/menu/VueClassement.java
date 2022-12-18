package tetris.vues.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tetris.logique.Score;
import tetris.stockage.ScoreManager;
import tetris.vues.VueMenuPrincipal;

import java.util.List;

public class VueClassement extends Stage {

    private final BorderPane root;
    private final Scene scene;

    private final Label titre;
    private final VBox vbScores;
    private final GridPane classement;
    private final Button retour;

    private final Insets paddingTopLeft = new Insets(30, 0, 0, 80);
    private final Font police = Font.loadFont("file:src/main/resources/fonts/Arcade.ttf", 32);

    public VueClassement(VueMenuPrincipal vueMenuPrincipal) {
        // Initialisations
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        titre = new Label("Classement");

        classement = new GridPane();

        vbScores = new VBox(titre, classement);

        List<Score> scores = ScoreManager.getInstance().getScores();

        retour = new Button();

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
        creerBindings(vueMenuPrincipal);

        // Affichage
        root.setCenter(vbScores);
        root.setTop(retour);

        this.setScene(scene);
    }

    public void creerBindings(VueMenuPrincipal vueMenuPrincipal) {
        this.setRetour(vueMenuPrincipal);
    }

    public void styliser() {
        // Root (BorderPane)
        root.setBackground(VueMenuPrincipal.background);

        // Flèche retour
        retour.setGraphic(new ImageView(new Image("file:src/main/resources/img/fleche.png")));
        retour.setStyle("-fx-background-color: transparent;");
        BorderPane.setMargin(retour, paddingTopLeft);

        // Titre
        titre.setFont(police);
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

    public void setRetour(VueMenuPrincipal vueMenuPrincipal) {
        retour.setOnAction(e -> {
            vueMenuPrincipal.afficherMenuPrincipal();
            this.setScene(scene);
        });
    }
}
