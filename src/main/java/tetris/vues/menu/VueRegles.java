package tetris.vues.menu;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tetris.logique.Preferences;
import tetris.vues.Menu;
import tetris.vues.VueMenuPrincipal;
import tetris.vues.helpers.BarreNavigation;

public class VueRegles extends Stage implements Menu {

    private final BorderPane root;
    private final Scene scene;

    public VueRegles(VueMenuPrincipal vueMenuPrincipal) {
        // Initialisations
        root = new BorderPane();
        scene = new Scene(root, 1280, 720);

        // Styles et bindings
        styliser();

        // Affections
        // ...

        // Affichage
        root.setTop(new BarreNavigation("Règles", vueMenuPrincipal, this));
        this.setScene(scene);
    }

    public void styliser() {
        root.setBackground(Preferences.getInstance().getBackground());
    }


    @Override
    public void afficherScene() {
        this.setScene(scene);
    }
}
