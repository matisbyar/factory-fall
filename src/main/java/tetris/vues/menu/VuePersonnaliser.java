package tetris.vues.menu;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tetris.TetrisIHM;
import tetris.singletons.Preferences;
import tetris.vues.Menu;
import tetris.vues.VueMenuPrincipal;
import tetris.vues.helpers.BarreNavigation;
import tetris.vues.helpers.PanelPersonnalisation;

import java.util.Objects;

public class VuePersonnaliser extends Stage implements Menu {

    private final BorderPane root;
    private final Scene scene;

    private final VBox personnalisations;

    private final ImageView imageStylePiece;

    private final Button flecheGauche, flecheDroite;

    Preferences preferences = Preferences.getInstance();

    /**
     * Évènements déclenchés par chacune des personnalisations
     */
    EventHandler<ActionEvent> stylePieceG = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            Platform.runLater(() -> {
                preferences.changerImage("-");
                imageStylePiece.setImage(preferences.getImagePiecePreference());
            });
        }
    };
    EventHandler<ActionEvent> stylePieceD = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            Platform.runLater(() -> {
                preferences.changerImage("+");
                imageStylePiece.setImage(preferences.getImagePiecePreference());
            });
        }
    };

    EventHandler<ActionEvent> gauche = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            Platform.runLater(() -> {
                preferences.changerFond("-");
                root.setBackground(preferences.getBackground());
            });
        }
    };
    EventHandler<ActionEvent> droite = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            Platform.runLater(() -> {
                preferences.changerFond("+");
                root.setBackground(preferences.getBackground());
            });
        }
    };

    public VuePersonnaliser(VueMenuPrincipal vueMenuPrincipal) {
        // Instanciation
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        personnalisations = new VBox();

        flecheGauche = new Button();
        flecheDroite = new Button();

        imageStylePiece = new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/" + preferences.getStylePiece() + "/L.jpg"))));

        // Styles et bindings
        styliser();

        personnalisations.getChildren().addAll(
                new PanelPersonnalisation("Style des pièces", stylePieceG, imageStylePiece, stylePieceD),
                new PanelPersonnalisation("Image de fond", gauche, new ImageView(), droite)
                //new PanelPersonnalisation("Musique de jeu", gauche, new ImageView(), droite)
        );

        root.setCenter(personnalisations);
        root.setTop(new BarreNavigation("Personnaliser", vueMenuPrincipal, this));

        this.setScene(scene);
    }

    public void styliser() {
        root.setBackground(Preferences.getInstance().getBackground());
        scene.getStylesheets().add(Objects.requireNonNull(TetrisIHM.class.getResource("css/main.css")).toString());

        flecheGauche.getStyleClass().add("flecheG");
        flecheDroite.getStyleClass().add("flecheD");

        personnalisations.getStyleClass().add("personnalisations");
        personnalisations.setSpacing(30);
    }

    @Override
    public void afficherScene() {
        this.setScene(scene);
        root.setBackground(Preferences.getInstance().getBackground());
    }
}
