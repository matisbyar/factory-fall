package tetris.vues.menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tetris.TetrisIHM;
import tetris.logique.Preferences;
import tetris.vues.Menu;
import tetris.vues.VueMenuPrincipal;
import tetris.vues.helpers.BarreNavigation;

import java.util.Objects;

public class VuePersonnaliser extends Stage implements Menu {

    private final BorderPane root;
    private final Scene scene;

    private final VBox personnalisations;
    private final HBox stylePiece;

    private final ImageView imageStylePiece;

    private final Button flecheGauche, flecheDroite;

    Preferences preferences = Preferences.getInstance();

    public VuePersonnaliser(VueMenuPrincipal vueMenuPrincipal) {
        // Instanciation
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        personnalisations = new VBox();
        stylePiece = new HBox();

        flecheGauche = new Button();
        flecheDroite = new Button();

        imageStylePiece = new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/" + preferences.getStylePiece() + "/L.jpg"))));

        // Styles et bindings
        styliser();
        creerBindings(vueMenuPrincipal);

        // Affections
        stylePiece.getChildren().addAll(flecheGauche, imageStylePiece, flecheDroite);

        personnalisations.getChildren().add(stylePiece);

        root.setCenter(personnalisations);
        root.setTop(new BarreNavigation("Personnaliser", vueMenuPrincipal, this));

        this.setScene(scene);
    }

    public void styliser() {
        root.setBackground(preferences.getBackground());
        scene.getStylesheets().add(Objects.requireNonNull(TetrisIHM.class.getResource("css/menu.css")).toString());

        flecheGauche.setGraphic(new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/flechePersonnalisation/flecheG.png")))));
        flecheDroite.setGraphic(new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/flechePersonnalisation/flecheD.png")))));
        flecheGauche.setStyle("-fx-background-color: black");
        flecheDroite.setStyle("-fx-background-color: black");

        personnalisations.setAlignment(Pos.CENTER);
        stylePiece.setAlignment(Pos.CENTER);
    }

    public void creerBindings(VueMenuPrincipal vueMenuPrincipal) {
        flecheGauche.setOnAction(actionEvent -> changerImage(vueMenuPrincipal, "-"));
        flecheDroite.setOnAction(actionEvent -> changerImage(vueMenuPrincipal, "+"));
    }

    /**
     * Permet de changer l'image de la pièce en fonction du style choisi
     *
     * @param vueMenuPrincipal permet d'appeler la méthode changerImage contenue dans la classe VueMenuPrincipal
     * @param etat             permet de savoir si on doit changer l'image vers la gauche ou la droite
     */
    public void changerImage(VueMenuPrincipal vueMenuPrincipal, String etat) {
        vueMenuPrincipal.changerImage(etat);
        imageStylePiece.setImage(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/" + preferences.getStylePiece() + "/L.jpg"))));
    }

    @Override
    public void afficherScene() {
        this.setScene(scene);
    }
}
