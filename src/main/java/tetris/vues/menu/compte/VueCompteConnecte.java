package tetris.vues.menu.compte;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tetris.TetrisIHM;
import tetris.logique.Preferences;
import tetris.vues.Menu;
import tetris.vues.VueMenuPrincipal;
import tetris.vues.helpers.BarreNavigation;

import java.util.Objects;

public class VueCompteConnecte extends Stage implements Menu {

    private final BorderPane root;
    private final Scene scene;

    public VueCompteConnecte(VueMenuPrincipal vueMenuPrincipal) {
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        // Styles et bindings
        styliser();

        // Affichage
        root.setTop(new BarreNavigation("Compte", vueMenuPrincipal, this));

        this.setScene(scene);
    }

    protected void styliser() {
        // Root (BorderPane)
        root.setBackground(Preferences.getInstance().getBackground());

        // Scene
        scene.getStylesheets().add(Objects.requireNonNull(TetrisIHM.class.getResource("css/menu.css")).toString());
    }

    @Override
    public void afficherScene() {
        this.setScene(scene);
    }
}
