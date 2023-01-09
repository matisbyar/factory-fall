package tetris.vues.menu;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import tetris.TetrisIHM;
import tetris.singletons.Preferences;
import tetris.singletons.Ressources;
import tetris.vues.Menu;
import tetris.vues.Musique;
import tetris.vues.VueMenuPrincipal;
import tetris.vues.helpers.BarreNavigation;
import tetris.vues.helpers.PanelPersonnalisation;

import java.util.Objects;

public class VuePersonnaliser extends Stage implements Menu {

    private final BorderPane root;
    private final Scene scene;

    private final VBox personnalisations;

    private final ImageView imageStylePiece, cadenas;

    private final Button flecheGauche, flecheDroite, muteBtn;

    private final Image mute;

    private StackPane locked;

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
                switchCadenas("pieces");
            });
        }
    };
    EventHandler<ActionEvent> stylePieceD = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            Platform.runLater(() -> {
                preferences.changerImage("+");
                imageStylePiece.setImage(preferences.getImagePiecePreference());
                switchCadenas("pieces");
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
        cadenas = new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("icons/cadenas.png"))));

        mute = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("icons/mute.png")));
        muteBtn = new Button();
        muteBtn.setOnAction(e -> Musique.btnMute());

        if (Ressources.getInstance().isLocked("pieces")) {
            locked = new StackPane(imageStylePiece, cadenas);
        } else {
            locked = new StackPane(cadenas, imageStylePiece);
        }


        // Styles et bindings
        styliser();

        personnalisations.getChildren().addAll(
                new PanelPersonnalisation("Style des pièces", stylePieceG, locked, stylePieceD),
                new PanelPersonnalisation("Image de fond", gauche, new ImageView(), droite),
                new PanelPersonnalisation("Musique de jeu", gauche, new ImageView(), droite)
        );

        root.setBottom(muteBtn);

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

        muteBtn.setBackground(new Background(new BackgroundImage(mute, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT , BackgroundPosition.CENTER ,BackgroundSize.DEFAULT)));
        muteBtn.setPrefWidth(64);
        muteBtn.setPrefHeight(64);
    }

    @Override
    public void afficherScene() {
        this.setScene(scene);
        root.setBackground(Preferences.getInstance().getBackground());
    }

    public void switchCadenas(String typePerso) {
        ObservableList<Node> childs = this.locked.getChildren();
        if (Ressources.getInstance().isLocked(typePerso)) {
            if (!childs.get(childs.size() - 1).equals(cadenas)) {
                if (childs.size() > 1) {
                    Node topNode = childs.get(childs.size() - 1);
                    topNode.toBack();
                }
            }
        } else if (childs.get(childs.size() - 1).equals(cadenas)){
            if (childs.size() > 1) {
                Node topNode = childs.get(childs.size() - 1);
                topNode.toBack();
            }
        }
    }
}
