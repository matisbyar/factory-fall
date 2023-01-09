package tetris.vues.menu;

import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import tetris.TetrisIHM;
import tetris.logique.Preferences;
import tetris.vues.Menu;
import tetris.vues.Musique;
import tetris.vues.VueMenuPrincipal;
import tetris.vues.helpers.BarreNavigation;

import java.util.Objects;

public class VuePersonnaliser extends Stage implements Menu {

    private final BorderPane root;
    private final Scene scene;

    private final VBox personnalisations;
    private final HBox stylePiece;

    private final ImageView imageStylePiece, cadenas;

    private final Image mute;

    private final Button flecheGauche, flecheDroite, retour, muteBtn;

    private StackPane locked;

    Preferences preferences = Preferences.getInstance();

    public VuePersonnaliser(VueMenuPrincipal vueMenuPrincipal) {
        // Instanciation
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        personnalisations = new VBox();
        stylePiece = new HBox();

        flecheGauche = new Button();
        flecheDroite = new Button();
        retour = new Button();

        imageStylePiece = new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/" + preferences.getStylePiece() + "/L.jpg"))));
        cadenas = new ImageView(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("icons/cadenas.png"))));
        locked = new StackPane(cadenas, imageStylePiece);
        mute = new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("icons/mute.png")));
        muteBtn = new Button();
        muteBtn.setBackground(new Background(new BackgroundImage(mute, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT , BackgroundPosition.CENTER ,BackgroundSize.DEFAULT)));
        muteBtn.setPrefWidth(64);
        muteBtn.setPrefHeight(64);

        // Styles et bindings
        styliser();
        creerBindings(vueMenuPrincipal);

        // Affections
        stylePiece.getChildren().addAll(flecheGauche, locked, flecheDroite);

        personnalisations.getChildren().add(stylePiece);

        root.setBottom(muteBtn);

        root.setCenter(personnalisations);
        root.setTop(new BarreNavigation("Personnaliser", vueMenuPrincipal, this));

        this.setScene(scene);
    }

    public void styliser() {
        scene.getStylesheets().add(Objects.requireNonNull(TetrisIHM.class.getResource("css/main.css")).toString());

        flecheGauche.getStyleClass().add("flecheG");
        flecheDroite.getStyleClass().add("flecheD");

        personnalisations.getStyleClass().add("personnalisations");
        stylePiece.getStyleClass().add("stylePiece");
    }

    public void creerBindings(VueMenuPrincipal vueMenuPrincipal) {
        flecheGauche.setOnAction(actionEvent -> changerImage(vueMenuPrincipal, "-"));
        flecheDroite.setOnAction(actionEvent -> changerImage(vueMenuPrincipal, "+"));
        muteBtn.setOnAction(e -> Musique.btnMute());
    }

    /**
     * Permet de changer l'image de la pièce en fonction du style choisi
     *
     * @param vueMenuPrincipal permet d'appeler la méthode changerImage contenue dans la classe VueMenuPrincipal
     * @param etat             permet de savoir si on doit changer l'image vers la gauche ou la droite
     */
    public void changerImage(VueMenuPrincipal vueMenuPrincipal, String etat) {
        vueMenuPrincipal.changerImage(etat);
        ObservableList<Node> childs = this.locked.getChildren();
        if (preferences.getStylePiece().equals("brique") || preferences.getStylePiece().equals("default")) {
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
        imageStylePiece.setImage(new Image(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("img/" + preferences.getStylePiece() + "/L.jpg"))));
    }

    @Override
    public void afficherScene() {
        this.setScene(scene);
    }
}
