package tetris.vues.menu.compte;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tetris.TetrisIHM;
import tetris.vues.VueMenuPrincipal;

import java.util.Objects;

public class VueCompteConnecte extends Stage {

    private final BorderPane root;
    private final Scene scene;

    private final Button retour;

    private final Font police = Font.loadFont(Objects.requireNonNull(TetrisIHM.class.getResourceAsStream("fonts/arcade.ttf")), 32);
    private final Insets paddingTopLeft = new Insets(30, 0, 30, 80);

    public VueCompteConnecte(VueMenuPrincipal vueMenuPrincipal) {
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        retour = new Button("Retour");

        // Styles et bindings
        styliser();
        creerBindings(vueMenuPrincipal);

        // Affichage
        root.setTop(retour);

        this.setScene(scene);
    }

    protected void creerBindings(VueMenuPrincipal vueMenuPrincipal) {
        retour.setOnAction(event -> {
            vueMenuPrincipal.afficherMenuPrincipal();
            this.setScene(scene);
        });
    }

    protected void styliser() {
        // Root (BorderPane)
        root.setBackground(VueMenuPrincipal.background);

        // Scene
        scene.getStylesheets().add(Objects.requireNonNull(TetrisIHM.class.getResource("css/menu.css")).toString());

        // Flèche de retour
        retour.setFont(police);
        retour.setAlignment(Pos.BOTTOM_LEFT);
        retour.setPrefHeight(100);
        retour.setPrefWidth(100);
        retour.setStyle("-fx-background-color: transparent; -fx-text-fill: white");
        BorderPane.setMargin(retour, paddingTopLeft);
    }
}
